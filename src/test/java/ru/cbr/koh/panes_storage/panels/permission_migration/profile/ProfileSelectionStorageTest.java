package ru.cbr.koh.panes_storage.panels.permission_migration.profile;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfileSelectionStorageTest {

    @Test
    void shouldSaveProfileCodes() throws Exception {
        Path tempFile = Files.createTempFile("profiles", ".txt");
        ProfileSelectionStorage storage = new ProfileSelectionStorage(tempFile);

        storage.save(List.of(Profile.AUDITOR, Profile.CURATOR_DFS));

        List<String> saved = Files.readAllLines(tempFile);
        assertEquals(List.of("AUDITOR", "CURATOR_DFS"), saved);

        Files.deleteIfExists(tempFile);
    }
}
