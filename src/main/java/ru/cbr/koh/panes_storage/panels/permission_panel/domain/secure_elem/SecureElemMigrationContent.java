package ru.cbr.koh.panes_storage.panels.permission_panel.domain.secure_elem;


import ru.cbr.koh.panes_storage.panels.permission_panel.domain.Permission;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SecureElemMigrationContent {

    private final Permission[] permissions;

    public SecureElemMigrationContent(Permission[] permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return Arrays.stream(permissions).toList().stream()
                .map(SecureElemMigration::new)
                .map(SecureElemMigration::toString)
                .collect(Collectors.joining());
    }
}
