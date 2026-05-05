package ru.cbr.koh.logs.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cbr.koh.logs.domain.ErrorGroupEntity;
import ru.cbr.koh.logs.domain.LogEventEntity;
import ru.cbr.koh.logs.domain.LogLevel;
import ru.cbr.koh.logs.domain.SchedulerPresetEntity;
import ru.cbr.koh.logs.domain.TagEntity;
import ru.cbr.koh.logs.repository.ErrorGroupRepository;
import ru.cbr.koh.logs.repository.LogEventRepository;
import ru.cbr.koh.logs.repository.SchedulerPresetRepository;
import ru.cbr.koh.logs.repository.TagRepository;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationLogFacade {

    private final RemoteLogArchiveService remoteLogArchiveService;
    private final LogArchiveService logArchiveService;
    private final LogIngestionService ingestionService;
    private final LogEventRepository logEventRepository;
    private final ErrorGroupRepository errorGroupRepository;
    private final TagRepository tagRepository;
    private final SchedulerPresetRepository schedulerPresetRepository;

    public ApplicationLogFacade(RemoteLogArchiveService remoteLogArchiveService,
                                LogArchiveService logArchiveService,
                                LogIngestionService ingestionService,
                                LogEventRepository logEventRepository,
                                ErrorGroupRepository errorGroupRepository,
                                TagRepository tagRepository,
                                SchedulerPresetRepository schedulerPresetRepository) {
        this.remoteLogArchiveService = remoteLogArchiveService;
        this.logArchiveService = logArchiveService;
        this.ingestionService = ingestionService;
        this.logEventRepository = logEventRepository;
        this.errorGroupRepository = errorGroupRepository;
        this.tagRepository = tagRepository;
        this.schedulerPresetRepository = schedulerPresetRepository;
    }

    public LogIngestionResult downloadAndIngestLatest(String password) {
        Path archivePath = remoteLogArchiveService.downloadLatestZpeArchive(password);
        Path logFilePath = logArchiveService.unpackAndFindDossierLog(archivePath);
        return ingestionService.ingest(logFilePath);
    }

    public LogIngestionResult ingestLocalLog(Path logFilePath) {
        return ingestionService.ingest(logFilePath);
    }

    public List<LogEventEntity> searchEvents(LogLevel level,
                                             boolean attentionOnly,
                                             LocalDateTime fromDate,
                                             LocalDateTime toDate) {
        return searchEvents(level, attentionOnly, fromDate, toDate, 0, 500).getContent();
    }

    public Page<LogEventEntity> searchEvents(LogLevel level,
                                             boolean attentionOnly,
                                             LocalDateTime fromDate,
                                             LocalDateTime toDate,
                                             int page,
                                             int size) {
        return logEventRepository.search(level, attentionOnly, fromDate, toDate, PageRequest.of(page, size));
    }

    public LogEventEntity eventById(Long id) {
        return logEventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Событие не найдено: " + id));
    }

    public List<ErrorGroupEntity> errorGroups() {
        return errorGroupRepository.findTop500ByOrderByLastSeenDesc();
    }

    public ErrorGroupEntity errorGroupById(Long id) {
        return errorGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Группа не найдена: " + id));
    }

    public List<LogEventEntity> schedulerEvents(String logger, String executor) {
        return schedulerEvents(logger, executor, 0, 500).getContent();
    }

    public Page<LogEventEntity> schedulerEvents(String logger, String executor, int page, int size) {
        return logEventRepository.searchSchedulerEvents(blankToNull(logger), blankToNull(executor), PageRequest.of(page, size));
    }

    public List<SchedulerPresetEntity> schedulerPresets() {
        ensureDefaultPreset();
        return schedulerPresetRepository.findAll();
    }

    @Transactional
    public SchedulerPresetEntity savePreset(Long id, String name, String loggerPattern, String executorPattern, String messagePattern) {
        SchedulerPresetEntity preset = id == null
                ? schedulerPresetRepository.findByName(name).orElseGet(SchedulerPresetEntity::new)
                : schedulerPresetRepository.findById(id).orElseGet(SchedulerPresetEntity::new);
        preset.setName(name);
        preset.setLoggerPattern(loggerPattern);
        preset.setExecutorPattern(executorPattern);
        preset.setMessagePattern(messagePattern);
        return schedulerPresetRepository.save(preset);
    }

    public void deletePreset(Long id) {
        schedulerPresetRepository.deleteById(id);
    }

    @Transactional
    public void toggleGroupAttention(Long groupId) {
        ErrorGroupEntity group = errorGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Группа не найдена: " + groupId));
        group.setAttention(!group.isAttention());
        errorGroupRepository.save(group);

        TagEntity tag = new TagEntity();
        tag.setErrorGroupId(groupId);
        tag.setTagType("ATTENTION");
        tag.setCreatedAt(LocalDateTime.now());
        tagRepository.save(tag);
    }

    @Transactional
    public void deleteEventsBefore(LocalDateTime before) {
        logEventRepository.deleteByEventTimestampBefore(before);
        errorGroupRepository.deleteByLastSeenBefore(before);
    }

    public void deleteEvent(Long id) {
        logEventRepository.deleteById(id);
    }

    public String llmStub(Long groupId) {
        return "Интеграция LLM пока не подключена. Группа: " + groupId;
    }

    private void ensureDefaultPreset() {
        schedulerPresetRepository.findByName("Form303").orElseGet(() ->
                savePreset(null, "Form303", "Form303Scheduler", "asyncForm303SchedulerExecutor", "")
        );
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
