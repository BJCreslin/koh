package ru.cbr.koh.panes_storage.panels.application_logs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

final class LogTableFormat {

    private static final DateTimeFormatter RUSSIAN_DATE_TIME = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private LogTableFormat() {
    }

    static String timestamp(LocalDateTime value) {
        return value == null ? "" : RUSSIAN_DATE_TIME.format(value);
    }
}
