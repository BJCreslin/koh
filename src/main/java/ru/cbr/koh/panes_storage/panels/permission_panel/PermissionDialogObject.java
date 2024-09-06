package ru.cbr.koh.panes_storage.panels.permission_panel;

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

    public PermissionDialogObject(String key, PermissionType permissionType, String groupAction, String userAction, String name, String description) {
        this.key = key;
        this.permissionType = permissionType;
        this.groupAction = groupAction;
        this.userAction = userAction;
        this.name = name;
        this.description = description;
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

    @Override
    public String toString() {
        return """
                "key='" + key + '\'' +
                ", permissionType=" + permissionType +
                ", groupAction='" + groupAction + '\'' +
                ", userAction='" + userAction + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                """;
    }
}
