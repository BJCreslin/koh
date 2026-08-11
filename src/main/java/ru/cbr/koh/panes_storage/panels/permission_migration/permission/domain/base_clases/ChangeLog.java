package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.base_clases;


import ru.cbr.koh.panes_storage.panels.permission_migration.information.domain.Information;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.secure_elem.SecureElemMigrationContent;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.secure_elem.SecureElemMigrationContentRollback;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.secure_elem.SecureElemPermissionId;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.tree_elem.TreeMigrationContent;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.tree_elem.TreeMigrationContentRollback;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;
import ru.cbr.koh.panes_storage.panels.permission_migration.output.GeneratedFile;
import ru.cbr.koh.panes_storage.panels.permission_migration.save_abac_attribute_code.AbacAttributeCodeSaver;
import ru.cbr.koh.panes_storage.panels.permission_migration.save_abac_profile_file.AbacProfileFileSaver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ChangeLog {

    private final List<Permission> permissions;

    private final Information information;
    private final AbacProfileFileSaver abacProfileFileSaver;
    private final AbacAttributeCodeSaver abacAttributeCodeSaver;

    public ChangeLog(Information information, List<Permission> permissions) {
        this(information, permissions, new AbacProfileFileSaver(), new AbacAttributeCodeSaver());
    }

    public ChangeLog(Information information,
                     List<Permission> permissions,
                     AbacProfileFileSaver abacProfileFileSaver,
                     AbacAttributeCodeSaver abacAttributeCodeSaver) {
        this.information = information;
        this.permissions = permissions;
        this.abacProfileFileSaver = abacProfileFileSaver;
        this.abacAttributeCodeSaver = abacAttributeCodeSaver;
    }

    public void create() {
        saveFiles(buildFiles());
    }

    public List<GeneratedFile> buildFiles() {
        SecureElemPermissionId permissionId = new SecureElemPermissionId(information.keyText());
        List<GeneratedFile> files = new ArrayList<>();
        files.add(new GeneratedFile(Path.of(permissionId + ".xml"), getFileContent(permissions, permissionId)));
        if (information.shouldWriteAbakFile()) {
            files.add(abacProfileFileSaver.buildFile(permissions));
        }
        if (information.shouldWriteAbacAttributeCode()) {
            files.add(abacAttributeCodeSaver.buildFile(permissions));
        }
        return files;
    }

    public void saveFiles(List<GeneratedFile> files) {
        files.forEach(this::saveFile);
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
                new TreeMigrationContentRollback(permissions),
                new SecureElemMigrationContentRollback(permissions),
                getChangeLogCreatedContent(permissions, permissionId));
    }

    private String getChangeLogCreatedContent(List<Permission> permissions, SecureElemPermissionId permissionId) {
        Set<Profile> profiles =
                permissions.stream()
                        .<Profile>mapMulti((permission, consumer) ->
                                permission.getProfiles().forEach(consumer))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
        var accumulator = new StringBuilder();
        profiles.forEach(profile -> {
            var changeSet = new ChangeSet(profile, permissions, permissionId, information);
            accumulator.append(changeSet.toString()).append("\n");
        });
        return accumulator.toString();
    }

    private void saveFile(GeneratedFile file) {
        try {
            Files.writeString(file.path(), file.content());
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось сохранить файл: " + file.path().toAbsolutePath(), e);
        }
    }

    private static String getChangeLogTempleFileContent() {
        try (InputStream input = ChangeLog.class.getClassLoader().getResourceAsStream("changeLogTemplate.xml")) {
            if (input == null) {
                throw new IllegalStateException("File not found: changeLogTemplate.xml");
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load changeLogTemplate.xml", e);
        }
    }
}
