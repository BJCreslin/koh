package ru.cbr.koh.panes_storage.panels.permission_migration.profile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileSelectionStorage {

    private static final String FILE_NAME = "selectedCheckboxes.txt";
    private final Path storagePath;

    public ProfileSelectionStorage() {
        this(Path.of(FILE_NAME));
    }

    ProfileSelectionStorage(Path storagePath) {
        this.storagePath = storagePath;
    }

    public List<String> load() {
        if (!Files.exists(storagePath)) {
            return Collections.emptyList();
        }
        try {
            return Files.readAllLines(storagePath);
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось прочитать " + FILE_NAME, e);
        }
    }

    public void save(List<Profile> selectedProfiles) {
        try {
            List<String> lines = selectedProfiles.stream()
                    .map(Profile::getCode)
                    .collect(Collectors.toList());
            Files.write(storagePath, lines);
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось сохранить " + FILE_NAME, e);
        }
    }
}
