package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.dialog_objects;

import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.PermissionType;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.TreeType;

import java.util.List;

/**
 * Класс хранения данных о диалоге выбора данных пермишнов
 */
public class PermissionDialogObject {

    private String key;

    private PermissionType permissionType;

    private String groupAction;

    private String userAction;

    private String name;

    private String description;

    private List<TreeType> treeType;

    public PermissionDialogObject(String key, PermissionType permissionType, String groupAction, String userAction, String name, String description, List<TreeType> treeType) {
        this.key = key;
        this.permissionType = permissionType;
        this.groupAction = groupAction;
        this.userAction = userAction;
        this.name = name;
        this.description = description;
        this.treeType = treeType;
    }

    public String getKey() {
        return key;
    }

    public PermissionType getPermissionType() {
        return permissionType;
    }

    public String getGroupAction() {
        return groupAction;
    }

    public String getUserAction() {
        return userAction;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<TreeType> getTreeType() {
        return treeType;
    }

    @Override
    public String toString() {
        return """
                "key='" + key + '\'' +
                ", permissionType=" + permissionType +
                ", groupAction='" + groupAction + '\'' +
                ", userAction='" + userAction + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", treeType=" + treeType +
                """;
    }
}
