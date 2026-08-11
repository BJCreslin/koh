package ru.cbr.koh.logs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "LOG_EVENTS", indexes = {
        @Index(name = "IDX_LOG_EVENTS_TIMESTAMP", columnList = "eventTimestamp"),
        @Index(name = "IDX_LOG_EVENTS_LEVEL", columnList = "level"),
        @Index(name = "IDX_LOG_EVENTS_HASH", columnList = "eventHash", unique = true),
        @Index(name = "IDX_LOG_EVENTS_GROUP", columnList = "errorGroupId")
})
public class LogEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime eventTimestamp;

    @Enumerated(EnumType.STRING)
    private LogLevel level;

    private String loggerName;

    private String executorName;

    @Lob
    private String message;

    @Lob
    private String stackTrace;

    private String sourceFile;

    private Integer lineNumber;

    private LocalDateTime lastSeenAt;

    private Long errorGroupId;

    @Column(nullable = false, unique = true, length = 128)
    private String eventHash;

    private boolean attention;

    private boolean tracked;

    private boolean schedulerEvent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(LocalDateTime eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public LocalDateTime getLastSeenAt() {
        return lastSeenAt;
    }

    public void setLastSeenAt(LocalDateTime lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
    }

    public Long getErrorGroupId() {
        return errorGroupId;
    }

    public void setErrorGroupId(Long errorGroupId) {
        this.errorGroupId = errorGroupId;
    }

    public String getEventHash() {
        return eventHash;
    }

    public void setEventHash(String eventHash) {
        this.eventHash = eventHash;
    }

    public boolean isAttention() {
        return attention;
    }

    public void setAttention(boolean attention) {
        this.attention = attention;
    }

    public boolean isTracked() {
        return tracked;
    }

    public void setTracked(boolean tracked) {
        this.tracked = tracked;
    }

    public boolean isSchedulerEvent() {
        return schedulerEvent;
    }

    public void setSchedulerEvent(boolean schedulerEvent) {
        this.schedulerEvent = schedulerEvent;
    }
}
