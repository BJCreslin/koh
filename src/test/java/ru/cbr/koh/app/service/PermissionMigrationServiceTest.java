package ru.cbr.koh.app.service;

import org.junit.jupiter.api.Test;
import ru.cbr.koh.panes_storage.panels.permission_migration.output.GeneratedFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PermissionMigrationServiceTest {

    private final PermissionMigrationService service = new PermissionMigrationService();

    @Test
    void shouldSavePreviewFiles() throws Exception {
        Path tempFile = Files.createTempFile("preview", ".txt");
        MigrationPreview preview = new MigrationPreview(List.of(new GeneratedFile(tempFile, "content")));

        service.savePreview(preview);

        String saved = Files.readString(tempFile);
        assertEquals("content", saved);
        Files.deleteIfExists(tempFile);
    }

    @Test
    void shouldRejectEmptyPreview() {
        MigrationPreview preview = new MigrationPreview(List.of());
        assertThrows(IllegalArgumentException.class, () -> service.savePreview(preview));
    }

    @Test
    void shouldRollbackAlreadySavedFilesWhenLaterSaveFails() throws Exception {
        Path tempDir = Files.createTempDirectory("preview-save");
        Path firstFile = tempDir.resolve("first.txt");
        Files.writeString(firstFile, "original");

        Path secondFile = tempDir.resolve("second.txt");
        Files.writeString(secondFile, "existing");

        PermissionMigrationService flakyService = new PermissionMigrationService() {
            private int moveCalls;

            @Override
            void moveWithReplace(Path source, Path target) throws java.io.IOException {
                moveCalls++;
                if (moveCalls == 2) {
                    throw new java.io.IOException("Simulated move failure");
                }
                super.moveWithReplace(source, target);
            }
        };

        MigrationPreview preview = new MigrationPreview(List.of(
                new GeneratedFile(firstFile, "updated"),
                new GeneratedFile(secondFile, "content")
        ));

        assertThrows(IllegalStateException.class, () -> flakyService.savePreview(preview));
        assertEquals("original", Files.readString(firstFile));
        assertEquals("existing", Files.readString(secondFile));

        Files.deleteIfExists(firstFile);
        Files.deleteIfExists(secondFile);
        Files.deleteIfExists(tempDir);
    }
}
