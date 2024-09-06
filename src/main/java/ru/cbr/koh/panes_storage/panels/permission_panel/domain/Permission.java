package ru.cbr.koh.panes_storage.panels.permission_panel.domain;


import ru.cbr.koh.panes_storage.panels.permission_panel.PermissionDialogObject;
import ru.cbr.koh.panes_storage.panels.permission_panel.PermissionType;
import ru.cbr.koh.panes_storage.panels.profile.Profile;

import java.util.List;

public class Permission {

    private static final String PREFIX = "urn:%s:attr:01:subject:";

    private final String parent;

    private final PermissionType type;

    private final String name;

    private final String relKey;

    private final String key;

    private final String abacPermPresAttrCode;

    private final String abacPermPresGroupAction;

    private final String abacPermPresUserAction;

    private final List<Profile> profiles;

    private final boolean koPermission;

    private final String description;

    public Permission(String key,
                      PermissionType type,
                      String abacPermPresGroupAction,
                      String abacPermPresUserAction,
                      String name,
                      List<Profile> profiles,
                      String description) {
        String[] parts = key.split("#");
        this.relKey = parts[parts.length - 1];
        this.key = key;
        this.parent = getParent(key);
        this.abacPermPresAttrCode = PREFIX + key.replace("#", "_");
        this.type = type;
        this.abacPermPresGroupAction = abacPermPresGroupAction;
        this.name = name;
        this.profiles = profiles;
        this.abacPermPresUserAction = abacPermPresUserAction;
        koPermission = abacPermPresUserAction != null;
        this.description = description;
    }

    public Permission(PermissionDialogObject object) {
        this(object.getKey(),
                object.getPermissionType(),
                object.getGroupAction(),
                object.getUserAction(),
                object.getName(),
                null,
                object.getDescription());
    }

    public Permission(PermissionDialogObject object, List<Profile> profiles) {
        this(object.getKey(),
                object.getPermissionType(),
                object.getGroupAction(),
                object.getUserAction(),
                object.getName(),
                profiles,
                object.getDescription());
    }

    private String getParent(String key) {
        String parentLast = key.replace(relKey, "");
        if (parentLast.isEmpty()) {
            return parentLast;
        }
        StringBuilder sb = new StringBuilder(parentLast);
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public String getParent() {
        return parent;
    }

    public PermissionType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getRelKey() {
        return relKey;
    }

    public String getKey() {
        return key;
    }

    public String getAbacPermPresAttrCode() {
        return abacPermPresAttrCode;
    }

    public String getAbacPermPresGroupAction() {
        return abacPermPresGroupAction;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public String getAbacPermPresUserAction() {
        return abacPermPresUserAction;
    }

    public boolean isKoPermission() {
        return koPermission;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public String toString() {
        return
                "parent= " + parent + "\n" +
                "type= " + type + "\n" +
                "name= " + name + "\n" +
                "relKey= " + relKey + "\n" +
                "key= " + key + "\n" +
                "abacPermPresAttrCode= " + abacPermPresAttrCode + "\n" +
                "abacPermPresGroupAction= " + abacPermPresGroupAction + "\n" +
                "abacPermPresUserAction= " + abacPermPresUserAction + "\n" +
                "koPermission= " + koPermission + "\n" +
                "description= " + description + "\n";
    }
}
