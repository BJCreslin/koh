package ru.cbr.koh.panes_storage.panels.permission_panel.domain.profile_secure_elem;


import ru.cbr.koh.panes_storage.panels.permission_panel.domain.Permission;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ProfileSecureElemMigrationContent {

    private final Permission[] permissions;

    public ProfileSecureElemMigrationContent(Permission[] permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return Arrays.stream(permissions).toList().stream()
                .map(ProfileSecureElemMigration::new)
                .map(ProfileSecureElemMigration::toString)
                .collect(Collectors.joining());
    }
}
