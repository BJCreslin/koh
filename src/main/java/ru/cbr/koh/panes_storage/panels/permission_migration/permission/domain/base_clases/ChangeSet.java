package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.base_clases;

import ru.cbr.koh.panes_storage.panels.permission_migration.information.domain.Information;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.profile_secure_elem.ProfileSecureElemMigrationContent;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.profile_secure_elem.ProfileSecureElemMigrationContentRollback;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.secure_elem.SecureElemMigrationContent;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.secure_elem.SecureElemMigrationContentRollback;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.secure_elem.SecureElemPermissionId;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.tree_elem.TreeMigrationContent;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ChangeSet {
    private Profile profile;
    private List<Permission> permissions;
    private SecureElemPermissionId permissionId;
    private Information information;

    private String content;

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
                new ProfileSecureElemMigrationContent(permissions, profile),
                new ProfileSecureElemMigrationContentRollback(permissions, profile)
        );
    }

    private static String getChangeSetTempleFileContent() {
        URL resource = ChangeLog.class.getClassLoader().getResource("changeSetTemplate.xml");
        if (resource == null) {
            throw new RuntimeException("File not found!");
        }
        try {
            Path path = Paths.get(resource.toURI());
            List<String> fileLines = Files.readAllLines(path);
            return String.join("\n", fileLines);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getPermissionIdentifier(SecureElemPermissionId permissionId, Profile profile) {
        return String.format("%s_%s_profile", permissionId, profile.getId());
    }
}
