package ru.cbr.koh.panes_storage.panels.application_logs;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogTableFormatTest {

    @Test
    void shouldFormatTimestampInRussianDateTimeFormat() {
        LocalDateTime timestamp = LocalDateTime.of(2026, 4, 28, 9, 5, 7);

        assertEquals("28.04.2026 09:05:07", LogTableFormat.timestamp(timestamp));
    }

    @Test
    void shouldFormatEmptyTimestampAsBlank() {
        assertEquals("", LogTableFormat.timestamp(null));
    }
}
