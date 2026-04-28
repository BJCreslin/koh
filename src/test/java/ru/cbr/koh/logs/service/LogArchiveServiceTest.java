package ru.cbr.koh.logs.service;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogArchiveServiceTest {

    private final LogArchiveService service = new LogArchiveService();

    @Test
    void shouldUnpackAbsoluteZipEntriesInsideTargetDirectory() throws Exception {
        Path archive = Files.createTempFile("logs", ".zip");
        try (ZipOutputStream outputStream = new ZipOutputStream(Files.newOutputStream(archive))) {
            outputStream.putNextEntry(new ZipEntry("/opt2/hash/log/dossier-ko.log"));
            outputStream.write("content".getBytes(java.nio.charset.StandardCharsets.UTF_8));
            outputStream.closeEntry();
        }

        Path logFile = service.unpackAndFindDossierLog(archive);

        assertEquals("dossier-ko.log", logFile.getFileName().toString());
        assertEquals("content", Files.readString(logFile));
    }
}
