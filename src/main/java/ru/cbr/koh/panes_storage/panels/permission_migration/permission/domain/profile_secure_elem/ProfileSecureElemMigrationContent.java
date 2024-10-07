package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.profile_secure_elem;


import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

import java.util.List;
import java.util.stream.Collectors;

public class ProfileSecureElemMigrationContent {

    private final List<? extends Permission> permissions;

    private final Profile profile;

    public ProfileSecureElemMigrationContent(List<? extends Permission> permissions, Profile profile) {
        this.permissions = permissions;
        this.profile = profile;
    }

    @Override
    public String toString() {
        return permissions.stream()
                .filter(it -> it.getProfiles().contains(profile))
                .map(it -> new ProfileSecureElemMigration(it, profile))
                .map(ProfileSecureElemMigration::toString)
                .collect(Collectors.joining());
    }
}
