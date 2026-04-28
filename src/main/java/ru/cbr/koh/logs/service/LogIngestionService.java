package ru.cbr.koh.logs.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cbr.koh.logs.domain.ErrorGroupEntity;
import ru.cbr.koh.logs.domain.LogCursorEntity;
import ru.cbr.koh.logs.domain.LogEventEntity;
import ru.cbr.koh.logs.domain.LogLevel;
import ru.cbr.koh.logs.repository.ErrorGroupRepository;
import ru.cbr.koh.logs.repository.LogCursorRepository;
import ru.cbr.koh.logs.repository.LogEventRepository;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HexFormat;
import java.util.List;

@Service
public class LogIngestionService {

    private final LogParserService parserService;
    private final FingerprintService fingerprintService;
    private final SensitiveDataMasker dataMasker;
    private final LogEventRepository logEventRepository;
    private final ErrorGroupRepository errorGroupRepository;
    private final LogCursorRepository cursorRepository;

    public LogIngestionService(LogParserService parserService,
                               FingerprintService fingerprintService,
                               SensitiveDataMasker dataMasker,
                               LogEventRepository logEventRepository,
                               ErrorGroupRepository errorGroupRepository,
                               LogCursorRepository cursorRepository) {
        this.parserService = parserService;
        this.fingerprintService = fingerprintService;
        this.dataMasker = dataMasker;
        this.logEventRepository = logEventRepository;
        this.errorGroupRepository = errorGroupRepository;
        this.cursorRepository = cursorRepository;
    }

    @Transactional
    public LogIngestionResult ingest(Path logFilePath) {
        String sourceKey = logFilePath.toAbsolutePath().normalize().toString();
        long fileSize = fileSize(logFilePath);
        LogCursorEntity cursor = cursorRepository.findBySourceKey(sourceKey).orElseGet(() -> newCursor(sourceKey));
        long offset = cursor.getByteOffset();
        if (offset > fileSize) {
            offset = 0L;
        }

        ReadResult readResult = readFromOffset(logFilePath, offset);
        List<ParsedLogEvent> parsedEvents = parserService.parse(readResult.content());
        int inserted = 0;
        int duplicates = 0;

        for (ParsedLogEvent event : parsedEvents) {
            String eventHash = sha256(fingerprintService.fingerprint(event) + "|" + event.timestamp() + "|" + event.message());
            if (logEventRepository.findByEventHash(eventHash).isPresent()) {
                duplicates++;
                continue;
            }

            ErrorGroupEntity group = upsertGroup(event);
            LogEventEntity entity = toEntity(event, eventHash, group);
            logEventRepository.save(entity);
            inserted++;
        }

        cursor.setByteOffset(readResult.newOffset());
        cursor.setFileSize(fileSize);
        cursor.setModifiedAt(lastModified(logFilePath));
        cursorRepository.save(cursor);
        return new LogIngestionResult(inserted, duplicates, readResult.newOffset());
    }

    private ErrorGroupEntity upsertGroup(ParsedLogEvent event) {
        String fingerprint = fingerprintService.fingerprint(event);
        ErrorGroupEntity group = errorGroupRepository.findByFingerprint(fingerprint).orElseGet(ErrorGroupEntity::new);
        LocalDateTime previousLastSeen = group.getLastSeen();
        if (group.getId() == null) {
            group.setFingerprint(fingerprint);
            group.setCount(0);
            group.setFirstSeen(event.timestamp());
            group.setSampleMessage(dataMasker.mask(event.message()));
            group.setSampleStackTrace(dataMasker.mask(event.stackTrace()));
            group.setExceptionClass(fingerprintService.extractExceptionClass(event));
            FingerprintService.StackFrame stackFrame = fingerprintService.firstStackFrame(event);
            group.setSourceFile(stackFrame.sourceFile());
            group.setLineNumber(stackFrame.lineNumber());
        }
        group.setPreviousSeen(previousLastSeen);
        group.setLastSeen(event.timestamp());
        group.setCount(group.getCount() + 1);
        return errorGroupRepository.save(group);
    }

    private LogEventEntity toEntity(ParsedLogEvent event, String eventHash, ErrorGroupEntity group) {
        LogEventEntity entity = new LogEventEntity();
        entity.setEventTimestamp(event.timestamp());
        entity.setLevel(event.level());
        entity.setLoggerName(event.loggerName());
        entity.setExecutorName(event.executorName());
        entity.setMessage(dataMasker.mask(event.message()));
        entity.setStackTrace(dataMasker.mask(event.stackTrace()));
        entity.setSourceFile(group.getSourceFile());
        entity.setLineNumber(group.getLineNumber());
        entity.setLastSeenAt(event.timestamp());
        entity.setErrorGroupId(group.getId());
        entity.setEventHash(eventHash);
        entity.setTracked(group.isAttention());
        entity.setAttention(false);
        entity.setSchedulerEvent(event.schedulerEvent() || event.level() == LogLevel.INFO);
        return entity;
    }

    private ReadResult readFromOffset(Path path, long offset) {
        try (RandomAccessFile file = new RandomAccessFile(path.toFile(), "r")) {
            file.seek(offset);
            byte[] bytes = new byte[(int) (file.length() - offset)];
            file.readFully(bytes);
            return new ReadResult(new String(bytes, StandardCharsets.UTF_8), file.length());
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось прочитать лог с offset: " + path.toAbsolutePath(), e);
        }
    }

    private long fileSize(Path path) {
        try {
            return Files.size(path);
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось определить размер файла: " + path.toAbsolutePath(), e);
        }
    }

    private LocalDateTime lastModified(Path path) {
        try {
            return LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(), ZoneId.systemDefault());
        } catch (IOException e) {
            return LocalDateTime.now();
        }
    }

    private LogCursorEntity newCursor(String sourceKey) {
        LogCursorEntity cursor = new LogCursorEntity();
        cursor.setSourceKey(sourceKey);
        cursor.setByteOffset(0L);
        return cursor;
    }

    private String sha256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 недоступен", e);
        }
    }

    private record ReadResult(String content, long newOffset) {
    }
}
