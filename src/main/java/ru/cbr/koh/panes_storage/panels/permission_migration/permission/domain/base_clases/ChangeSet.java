package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.base_clases;

import ru.cbr.koh.panes_storage.panels.permission_migration.information.domain.Information;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.profile_secure_elem.ProfileSecureElemMigrationContent;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.profile_secure_elem.ProfileSecureElemMigrationContentRollback;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.secure_elem.SecureElemPermissionId;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ChangeSet {
    private final Profile profile;
    private final List<Permission> permissions;
    private final SecureElemPermissionId permissionId;
    private final Information information;

    private final String content;

    public ChangeSet(Profile profile, List<Permission> permissions, SecureElemPermissionId permissionId, Information information) {
        this.profile = profile;
        this.permissions = permissions;
        this.permissionId = permissionId;
        this.information = information;
        content = getChangeSetTempleFileContent();
    }

    @Override
    public String toString() {
        return String.format(content,
                getPermissionIdentifier(permissionId, profile),
                information.author(),
                profile.getName(),
                information.storyNumber(),
                information.storyText(),
                profile.getDisplayName(),
                profile.getId(),
                new ProfileSecureElemMigrationContent(permissions, profile),
                new ProfileSecureElemMigrationContentRollback(permissions, profile)
        );
    }

    private static String getChangeSetTempleFileContent() {
        try (InputStream input = ChangeLog.class.getClassLoader().getResourceAsStream("changeSetTemplate.xml")) {
            if (input == null) {
                throw new IllegalStateException("File not found: changeSetTemplate.xml");
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load changeSetTemplate.xml", e);
        }
    }

    private static String getPermissionIdentifier(SecureElemPermissionId permissionId, Profile profile) {
        return String.format("%s_%s_profile", permissionId, profile.getId());
    }
}
