package ru.cbr.koh.logs.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "SCHEDULER_PRESETS", indexes = @Index(name = "IDX_SCHEDULER_PRESETS_NAME", columnList = "name", unique = true))
public class SchedulerPresetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String loggerPattern;

    private String executorPattern;

    private String messagePattern;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoggerPattern() {
        return loggerPattern;
    }

    public void setLoggerPattern(String loggerPattern) {
        this.loggerPattern = loggerPattern;
    }

    public String getExecutorPattern() {
        return executorPattern;
    }

    public void setExecutorPattern(String executorPattern) {
        this.executorPattern = executorPattern;
    }

    public String getMessagePattern() {
        return messagePattern;
    }

    public void setMessagePattern(String messagePattern) {
        this.messagePattern = messagePattern;
    }
}
