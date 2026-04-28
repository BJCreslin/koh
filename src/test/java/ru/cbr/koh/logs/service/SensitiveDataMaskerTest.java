package ru.cbr.koh.logs.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SensitiveDataMaskerTest {

    private final SensitiveDataMasker masker = new SensitiveDataMasker();

    @Test
    void shouldMaskSensitiveValues() {
        String result = masker.mask("email test@example.com ip 10.10.10.1 apiKey=abcd1234 token:secret");

        assertTrue(result.contains("[EMAIL]"));
        assertTrue(result.contains("[IP]"));
        assertTrue(result.contains("[SECRET]"));
        assertFalse(result.contains("test@example.com"));
        assertFalse(result.contains("10.10.10.1"));
        assertFalse(result.contains("abcd1234"));
    }
}
