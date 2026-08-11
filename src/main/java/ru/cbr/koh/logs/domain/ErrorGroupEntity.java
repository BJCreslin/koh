package ru.cbr.koh.logs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "ERROR_GROUPS", indexes = {
        @Index(name = "IDX_ERROR_GROUPS_FINGERPRINT", columnList = "fingerprint", unique = true),
        @Index(name = "IDX_ERROR_GROUPS_LAST_SEEN", columnList = "lastSeen")
})
public class ErrorGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 128)
    private String fingerprint;

    @Column(name = "event_count")
    private long count;

    private LocalDateTime firstSeen;

    private LocalDateTime lastSeen;

    private LocalDateTime previousSeen;

    private String exceptionClass;

    private String sourceFile;

    private Integer lineNumber;

    private boolean attention;

    @Lob
    private String sampleMessage;

    @Lob
    private String sampleStackTrace;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public LocalDateTime getFirstSeen() {
        return firstSeen;
    }

    public void setFirstSeen(LocalDateTime firstSeen) {
        this.firstSeen = firstSeen;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public LocalDateTime getPreviousSeen() {
        return previousSeen;
    }

    public void setPreviousSeen(LocalDateTime previousSeen) {
        this.previousSeen = previousSeen;
    }

    public String getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(String exceptionClass) {
        this.exceptionClass = exceptionClass;
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

    public boolean isAttention() {
        return attention;
    }

    public void setAttention(boolean attention) {
        this.attention = attention;
    }

    public String getSampleMessage() {
        return sampleMessage;
    }

    public void setSampleMessage(String sampleMessage) {
        this.sampleMessage = sampleMessage;
    }

    public String getSampleStackTrace() {
        return sampleStackTrace;
    }

    public void setSampleStackTrace(String sampleStackTrace) {
        this.sampleStackTrace = sampleStackTrace;
    }
}
