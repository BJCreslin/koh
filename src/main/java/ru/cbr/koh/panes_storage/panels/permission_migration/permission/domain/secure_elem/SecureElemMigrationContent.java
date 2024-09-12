package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.secure_elem;


import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;

import java.util.List;
import java.util.stream.Collectors;

public class SecureElemMigrationContent {

    private final List<? extends Permission> permissions;

    public SecureElemMigrationContent(List<? extends Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return permissions.stream()
                .map(SecureElemMigration::new)
                .map(SecureElemMigration::toString)
                .collect(Collectors.joining());
    }
}
