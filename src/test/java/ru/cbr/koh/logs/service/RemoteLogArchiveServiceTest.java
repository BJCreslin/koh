package ru.cbr.koh.logs.service;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RemoteLogArchiveServiceTest {

    private final RemoteLogArchiveService service = new RemoteLogArchiveService();

    @Test
    void shouldTreatExistingNonEmptyArchiveAsValidCache() throws Exception {
        Path archive = Files.createTempFile("zpe-all-logs", ".zip");
        Files.writeString(archive, "zip-content");

        assertTrue(service.isValidCachedArchive(archive));

        Files.deleteIfExists(archive);
    }

    @Test
    void shouldRejectEmptyArchiveCache() throws Exception {
        Path archive = Files.createTempFile("zpe-all-logs", ".zip");

        assertFalse(service.isValidCachedArchive(archive));

        Files.deleteIfExists(archive);
    }

    @Test
    void shouldUseCachedLatestArchiveWhenItAlreadyExists() throws Exception {
        Path archive = Files.createTempFile("zpe-all-logs", ".zip");
        Files.writeString(archive, "zip-content");
        TestRemoteLogArchiveService testService = new TestRemoteLogArchiveService(archive);

        Path result = testService.downloadLatestZpeArchive();

        assertEquals(archive, result);
        assertFalse(testService.downloadCalled);

        Files.deleteIfExists(archive);
    }

    @Test
    void shouldDownloadLatestArchiveWhenCacheIsEmpty() throws Exception {
        Path archive = Files.createTempFile("zpe-all-logs", ".zip");
        TestRemoteLogArchiveService testService = new TestRemoteLogArchiveService(archive);

        Path result = testService.downloadLatestZpeArchive();

        assertEquals(archive, result);
        assertTrue(testService.downloadCalled);

        Files.deleteIfExists(archive);
    }

    private static final class TestRemoteLogArchiveService extends RemoteLogArchiveService {

        private final Path archive;
        private boolean downloadCalled;

        private TestRemoteLogArchiveService(Path archive) {
            this.archive = archive;
        }

        @Override
        String findLatestZpeArchiveName() {
            return "20260428094428-zpe-all-logs.zip";
        }

        @Override
        Path resolveDownloadPath(String fileName) {
            return archive;
        }

        @Override
        Path downloadArchive(String fileName, Path targetPath) {
            downloadCalled = true;
            return targetPath;
        }
    }
}
