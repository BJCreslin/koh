package ru.cbr.koh.logs.service;

import ru.cbr.koh.logs.domain.LogLevel;

import java.time.LocalDateTime;

public record ParsedLogEvent(
        LocalDateTime timestamp,
        LogLevel level,
        String loggerName,
        String executorName,
        String message,
        String stackTrace,
        boolean schedulerEvent
) {
}
