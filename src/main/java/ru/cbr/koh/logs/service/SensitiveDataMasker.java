package ru.cbr.koh.logs.service;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class SensitiveDataMasker {

    private static final Pattern EMAIL = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}");
    private static final Pattern IPV4 = Pattern.compile("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b");
    private static final Pattern API_KEY = Pattern.compile("(?i)(api[_-]?key|token|secret|password)\\s*[:=]\\s*[^\\s,;]+");

    public String mask(String value) {
        if (value == null || value.isBlank()) {
            return value;
        }
        String masked = EMAIL.matcher(value).replaceAll("[EMAIL]");
        masked = IPV4.matcher(masked).replaceAll("[IP]");
        return API_KEY.matcher(masked).replaceAll("$1=[SECRET]");
    }
}
