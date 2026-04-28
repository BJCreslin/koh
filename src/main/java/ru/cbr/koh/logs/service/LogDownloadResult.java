package ru.cbr.koh.logs.service;

import java.nio.file.Path;

public record LogDownloadResult(Path archivePath, Path logFilePath) {
}
