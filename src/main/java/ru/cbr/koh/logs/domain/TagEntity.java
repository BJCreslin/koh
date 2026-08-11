package ru.cbr.koh.logs.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "TAGS", indexes = @Index(name = "IDX_TAGS_LOG_EVENT", columnList = "logEventId"))
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long logEventId;

    private Long errorGroupId;

    private String tagType;

    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLogEventId() {
        return logEventId;
    }

    public void setLogEventId(Long logEventId) {
        this.logEventId = logEventId;
    }

    public Long getErrorGroupId() {
        return errorGroupId;
    }

    public void setErrorGroupId(Long errorGroupId) {
        this.errorGroupId = errorGroupId;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
