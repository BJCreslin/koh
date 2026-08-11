package ru.cbr.koh.app.service;

import ru.cbr.koh.panes_storage.panels.permission_migration.output.GeneratedFile;

import java.util.List;

public record MigrationPreview(List<GeneratedFile> files) {

    public MigrationPreview {
        files = List.copyOf(files);
    }
}
