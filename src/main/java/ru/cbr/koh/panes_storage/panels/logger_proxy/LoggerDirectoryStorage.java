package ru.cbr.koh.panes_storage.panels.logger_proxy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class LoggerDirectoryStorage {

    private static final String FILE_NAME = "logger.txt";
    private final Path storagePath;

    public LoggerDirectoryStorage() {
        this(Path.of(FILE_NAME));
    }

    LoggerDirectoryStorage(Path storagePath) {
        this.storagePath = storagePath;
    }

    public String load() {
        if (!Files.exists(storagePath)) {
            return "";
        }

        try {
            List<String> lines = Files.readAllLines(storagePath);
            if (lines.isEmpty()) {
                return "";
            }
            String firstLine = lines.get(0);
            return firstLine == null ? "" : firstLine.trim();
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось прочитать " + FILE_NAME, e);
        }
    }

    public void save(String directory) {
        if (directory == null || directory.isBlank()) {
            try {
                Files.deleteIfExists(storagePath);
            } catch (IOException e) {
                throw new IllegalStateException("Не удалось очистить " + FILE_NAME, e);
            }
            return;
        }

        try {
            Files.writeString(storagePath, directory.trim());
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось сохранить " + FILE_NAME, e);
        }
    }
}
