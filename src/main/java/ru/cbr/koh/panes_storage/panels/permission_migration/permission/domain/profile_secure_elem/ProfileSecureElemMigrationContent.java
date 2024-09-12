package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.profile_secure_elem;


import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;

import java.util.List;
import java.util.stream.Collectors;

public class ProfileSecureElemMigrationContent {

    private final List<? extends Permission> permissions;

    public ProfileSecureElemMigrationContent(List<? extends Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return permissions.stream()
                .map(ProfileSecureElemMigration::new)
                .map(ProfileSecureElemMigration::toString)
                .collect(Collectors.joining());
    }
}
