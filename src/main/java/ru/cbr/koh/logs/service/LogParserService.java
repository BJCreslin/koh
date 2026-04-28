package ru.cbr.koh.logs.service;

import org.springframework.stereotype.Service;
import ru.cbr.koh.logs.domain.LogLevel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LogParserService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss,SSS", Locale.ROOT);
    private static final Pattern LOG_LINE = Pattern.compile(
            "^(\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2}:\\d{2},\\d{3})\\s+(ERROR|WARN|INFO)\\s+\\[([^]]+)]\\s?(.*)$");

    public List<ParsedLogEvent> parse(String content) {
        List<ParsedLogEvent> result = new ArrayList<>();
        CurrentEvent currentEvent = null;

        for (String line : content.split("\\R", -1)) {
            Matcher matcher = LOG_LINE.matcher(line);
            if (matcher.matches()) {
                if (currentEvent != null) {
                    addIfRelevant(result, currentEvent.toParsedEvent());
                }
                currentEvent = createCurrentEvent(matcher);
                continue;
            }

            if (currentEvent != null && !line.isBlank()) {
                currentEvent.appendStackLine(line);
            }
        }

        if (currentEvent != null) {
            addIfRelevant(result, currentEvent.toParsedEvent());
        }
        return result;
    }

    private CurrentEvent createCurrentEvent(Matcher matcher) {
        String context = matcher.group(3).trim();
        String logger = context;
        String executor = "";
        int splitIndex = context.indexOf(' ');
        if (splitIndex > 0) {
            logger = context.substring(0, splitIndex).trim();
            executor = context.substring(splitIndex + 1).trim();
        }

        LogLevel level = LogLevel.valueOf(matcher.group(2));
        return new CurrentEvent(
                LocalDateTime.parse(matcher.group(1), FORMATTER),
                level,
                logger,
                executor,
                matcher.group(4),
                isSchedulerEvent(level, logger, executor)
        );
    }

    private void addIfRelevant(List<ParsedLogEvent> result, ParsedLogEvent event) {
        if (event.level() == LogLevel.ERROR || event.level() == LogLevel.WARN || event.schedulerEvent()) {
            result.add(event);
        }
    }

    private boolean isSchedulerEvent(LogLevel level, String logger, String executor) {
        if (level != LogLevel.INFO) {
            return false;
        }
        String combined = (logger + " " + executor).toLowerCase(Locale.ROOT);
        return combined.contains("scheduler");
    }

    private static class CurrentEvent {
        private final LocalDateTime timestamp;
        private final LogLevel level;
        private final String logger;
        private final String executor;
        private final String message;
        private final boolean schedulerEvent;
        private final StringBuilder stackTrace = new StringBuilder();

        private CurrentEvent(LocalDateTime timestamp,
                             LogLevel level,
                             String logger,
                             String executor,
                             String message,
                             boolean schedulerEvent) {
            this.timestamp = timestamp;
            this.level = level;
            this.logger = logger;
            this.executor = executor;
            this.message = message;
            this.schedulerEvent = schedulerEvent;
        }

        private void appendStackLine(String line) {
            if (stackTrace.length() > 0) {
                stackTrace.append(System.lineSeparator());
            }
            stackTrace.append(line);
        }

        private ParsedLogEvent toParsedEvent() {
            return new ParsedLogEvent(
                    timestamp,
                    level,
                    logger,
                    executor,
                    message,
                    stackTrace.toString(),
                    schedulerEvent
            );
        }
    }
}
