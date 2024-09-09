package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.base_clases;


import ru.cbr.koh.panes_storage.panels.permission_migration.information.domain.Information;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.profile_secure_elem.ProfileSecureElemMigrationContent;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.profile_secure_elem.ProfileSecureElemMigrationContentRollback;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.profile_secure_elem.ProfileSecureElemPermissionId;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.secure_elem.SecureElemMigrationContent;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.secure_elem.SecureElemMigrationContentRollback;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.secure_elem.SecureElemPermissionId;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ChangeLog {

    private final String keyText;

    private final String author;

    private final String storyNumber;

    private final String tabName;

    private final Permission[] permissions;

    public ChangeLog(Information information, List<Permission> permissions) {
        this.keyText = information.keyText();
        this.author = information.author();
        this.storyNumber = information.storyNumber();
        this.tabName = information.storyText();
        this.permissions = permissions.toArray(new Permission[0]);
    }

    public void create() {
        SecureElemPermissionId permissionId = new SecureElemPermissionId(keyText);
        String fileContent = getFileContent(permissions, permissionId);
        saveFile(permissionId, fileContent);
    }

    private String getFileContent(Permission[] permissions, SecureElemPermissionId permissionId) {

        return String.format(
                getTempleFileContent(),
                permissionId.toString(),
                author, storyNumber, tabName,
                new SecureElemMigrationContent(permissions),
                new SecureElemMigrationContentRollback(permissions),
                new ProfileSecureElemPermissionId(keyText),
                author, storyNumber, tabName,
                new ProfileSecureElemMigrationContent(permissions),
                new ProfileSecureElemMigrationContentRollback(permissions)
        );
    }

    private static void saveFile(SecureElemPermissionId idText, String newFileContent) {
        Path filePath = Path.of(idText.toString() + ".xml");

        try {
            Files.writeString(filePath, newFileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getTempleFileContent() {
        URL resource = ChangeLog.class.getClassLoader().getResource("template.xml");
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
