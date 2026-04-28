package ru.cbr.koh.logs.service;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FingerprintService {

    private static final Pattern EXCEPTION_CLASS = Pattern.compile("([\\w.$]+(?:Exception|Error))(?::|$)");
    private static final Pattern STACK_FRAME = Pattern.compile("\\bat\\s+[^\\(]+\\(([^:()]+\\.java):(\\d+)\\)");

    public String fingerprint(ParsedLogEvent event) {
        String base = extractExceptionClass(event) + "|" + extractFirstStackFrame(event);
        if ("|".equals(base)) {
            base = event.level() + "|" + event.loggerName() + "|" + normalizeMessage(event.message());
        }
        return sha256(base);
    }

    public String extractExceptionClass(ParsedLogEvent event) {
        String stackTrace = event.stackTrace() == null ? "" : event.stackTrace();
        Matcher matcher = EXCEPTION_CLASS.matcher(stackTrace);
        if (matcher.find()) {
            return matcher.group(1);
        }

        String message = event.message() == null ? "" : event.message();
        matcher = EXCEPTION_CLASS.matcher(message);
        return matcher.find() ? matcher.group(1) : "";
    }

    public StackFrame firstStackFrame(ParsedLogEvent event) {
        Matcher matcher = STACK_FRAME.matcher(event.stackTrace() == null ? "" : event.stackTrace());
        if (!matcher.find()) {
            return new StackFrame(null, null);
        }
        return new StackFrame(matcher.group(1), Integer.parseInt(matcher.group(2)));
    }

    private String extractFirstStackFrame(ParsedLogEvent event) {
        StackFrame stackFrame = firstStackFrame(event);
        if (stackFrame.sourceFile() == null) {
            return "";
        }
        return stackFrame.sourceFile() + ":" + stackFrame.lineNumber();
    }

    private String normalizeMessage(String message) {
        if (message == null) {
            return "";
        }
        return message.replaceAll("\\d+", "#").replaceAll("\\s+", " ").trim();
    }

    private String sha256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 недоступен", e);
        }
    }

    public record StackFrame(String sourceFile, Integer lineNumber) {
    }
}
