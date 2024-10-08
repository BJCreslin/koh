package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.base_clases;


import ru.cbr.koh.panes_storage.panels.permission_migration.information.domain.Information;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.secure_elem.SecureElemMigrationContent;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.secure_elem.SecureElemMigrationContentRollback;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.secure_elem.SecureElemPermissionId;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.tree_elem.TreeMigrationContent;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.tree_elem.TreeMigrationContentRollback;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;
import ru.cbr.koh.panes_storage.panels.permission_migration.save_abac_profile_file.AbacProfileFileSaver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ChangeLog {

    private final List<Permission> permissions;

    private final Information information;

    public ChangeLog(Information information, List<Permission> permissions) {
        this.information = information;
        this.permissions = permissions;
    }

    public void create() {
        SecureElemPermissionId permissionId = new SecureElemPermissionId(information.keyText());
        String fileContent = getFileContent(permissions, permissionId);
        saveFile(permissionId, fileContent);
        if (information.shouldWriteAbakFile()) {
            var saver = new AbacProfileFileSaver();
            saver.save(permissions);
        }
    }

    private String getFileContent(List<Permission> permissions, SecureElemPermissionId permissionId) {
        return String.format(
                getChangeLogTempleFileContent(),
                permissionId,
                information.author(),
                information.storyNumber(),
                information.storyText(),
                new SecureElemMigrationContent(permissions),
                new TreeMigrationContent(permissions),
                new SecureElemMigrationContentRollback(permissions),
                new TreeMigrationContentRollback(permissions),
                getChangeLogCreatedContent(permissions, permissionId));

//        return String.format(
//                getChangeLogTempleFileContent(),
//                permissionId.toString(),
//                author, storyNumber, tabName,
//                new SecureElemMigrationContent(permissions),
//                new SecureElemMigrationContentRollback(permissions),
//
//                new ProfileSecureElemPermissionId(keyText),
//                author, storyNumber, tabName,
//                new ProfileSecureElemMigrationContent(permissions),
//                new ProfileSecureElemMigrationContentRollback(permissions),
//
//                new TreeMigrationId(keyText),
//                author, storyNumber, tabName,
//                new TreeMigrationContent(permissions),
//                new TreeMigrationContentRollback(permissions)
//        );
    }

    private String getChangeLogCreatedContent(List<Permission> permissions, SecureElemPermissionId permissionId) {
        Set<Profile> profiles =
                permissions.stream()
                        .<Profile>mapMulti((permission, consumer) ->
                                permission.getProfiles().forEach(consumer::accept))
                        .collect(Collectors.toSet());
        var accumulator = new StringBuilder();
        profiles.forEach(profile -> {
            var changeSet = new ChangeSet(profile, permissions, permissionId, information);
            accumulator.append(changeSet.toString()).append("\n");
        });
        return accumulator.toString();
    }

    private static void saveFile(SecureElemPermissionId idText, String newFileContent) {
        Path filePath = Path.of(idText.toString() + ".xml");

        try {
            Files.writeString(filePath, newFileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getChangeLogTempleFileContent() {
        URL resource = ChangeLog.class.getClassLoader().getResource("changeLogTemplate.xml");
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
}
