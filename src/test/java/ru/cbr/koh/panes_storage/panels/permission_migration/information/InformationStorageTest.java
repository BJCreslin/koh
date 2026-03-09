package ru.cbr.koh.panes_storage.panels.permission_migration.information;

import org.junit.jupiter.api.Test;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.domain.Information;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InformationStorageTest {

    @Test
    void shouldSaveAndLoadInformation() throws Exception {
        File tempFile = Files.createTempFile("information", ".txt").toFile();
        InformationStorage storage = new InformationStorage(tempFile);

        Information source = new Information("key", "author", "story", "name", true, false, true);
        storage.save(source);

        Information loaded = storage.load().orElseThrow();
        assertEquals(source, loaded);

        assertTrue(tempFile.delete());
    }

    @Test
    void shouldLoadLegacySerializedInformationAndRewriteAsText() throws Exception {
        File tempFile = Files.createTempFile("information-legacy", ".txt").toFile();
        Information source = new Information("key", "author", "story", "name", true, false, true);

        try (FileOutputStream fileOut = new FileOutputStream(tempFile);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(source);
        }

        InformationStorage storage = new InformationStorage(tempFile);
        Information loaded = storage.load().orElseThrow();

        assertEquals(source, loaded);
        String persistedContent = Files.readString(tempFile.toPath());
        assertTrue(persistedContent.contains("keyText=key"));
        assertTrue(tempFile.delete());
    }
}
