package ru.cbr.koh.app.service;

import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;

import java.util.List;
import java.util.Map;

public record ExcelReadResult(MigrationPreview preview,
                              List<Permission> permissions,
                              Map<String, Map<String, List<Permission>>> conflicts) {
}
