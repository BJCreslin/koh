package ru.cbr.koh.logs.service;

import org.junit.jupiter.api.Test;
import ru.cbr.koh.logs.domain.LogLevel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LogParserServiceTest {

    private final LogParserService parserService = new LogParserService();

    @Test
    void shouldMergeStackTraceLinesIntoPreviousWarnEvent() {
        String content = """
                28.04.2026 00:00:44,786 WARN  [FileTextContentParserImpl asyncFilenetPathElasticSchedulerExecutor-1] Ошибка парсинга файла.
                org.example.SomeException: failed
                \tat org.example.Service.handle(Service.java:45)
                28.04.2026 00:00:45,358 WARN  [FileTextContentParserImpl asyncFilenetPathElasticSchedulerExecutor-1] Следующее предупреждение.
                """;

        List<ParsedLogEvent> events = parserService.parse(content);

        assertEquals(2, events.size());
        assertEquals(LogLevel.WARN, events.get(0).level());
        assertTrue(events.get(0).stackTrace().contains("SomeException"));
        assertTrue(events.get(0).stackTrace().contains("Service.java:45"));
    }

    @Test
    void shouldKeepSchedulerInfoEvents() {
        String content = """
                28.04.2026 04:01:11,980 INFO  [Form303Scheduler asyncForm303SchedulerExecutor-1] Сумма в витрине.
                28.04.2026 04:01:12,000 INFO  [SomeService main] Ordinary info.
                """;

        List<ParsedLogEvent> events = parserService.parse(content);

        assertEquals(1, events.size());
        assertEquals("Form303Scheduler", events.get(0).loggerName());
        assertEquals("asyncForm303SchedulerExecutor-1", events.get(0).executorName());
    }
}
