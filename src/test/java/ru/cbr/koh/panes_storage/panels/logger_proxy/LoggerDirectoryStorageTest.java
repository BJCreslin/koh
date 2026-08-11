package ru.cbr.koh.panes_storage.panels.logger_proxy;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class LoggerDirectoryStorageTest {

    @Test
    void shouldSaveAndLoadDirectory() throws Exception {
        Path tempFile = Files.createTempFile("logger", ".txt");
        LoggerDirectoryStorage storage = new LoggerDirectoryStorage(tempFile);

        storage.save("/tmp/project");

        assertEquals("/tmp/project", storage.load());
        Files.deleteIfExists(tempFile);
    }

    @Test
    void shouldDeleteStorageFileOnBlankSave() throws Exception {
        Path tempFile = Files.createTempFile("logger", ".txt");
        LoggerDirectoryStorage storage = new LoggerDirectoryStorage(tempFile);

        storage.save("/tmp/project");
        storage.save("   ");

        assertFalse(Files.exists(tempFile));
    }
}
