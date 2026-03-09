package ru.cbr.koh.panes_storage.panels.permission_migration.output;

import java.nio.file.Path;

public record GeneratedFile(Path path, String content) {

    public String displayName() {
        return path.toString();
    }
}
