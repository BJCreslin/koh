package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain;


import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.dialog_objects.PermissionDialogObject;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.PermissionType;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.TreeType;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

import java.util.List;

public class Permission {

    public static final String PREFIX = "urn:%s:attr:01:subject:";

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

    private final List<TreeType> treeType;

    private final Integer orderNoInNode;

    private KeyCandidate keyCandidate;

    public Permission(String key,
                      PermissionType type,
                      String abacPermPresGroupAction,
                      String abacPermPresUserAction,
                      String name,
                      List<Profile> profiles,
                      String description,
                      List<TreeType> treeType,
                      boolean bankDependent,
                      Integer orderNoInNode) {
        this.treeType = treeType;
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
        koPermission = bankDependent;
        this.description = description;
        this.orderNoInNode = orderNoInNode;
    }

    public List<TreeType> getTreeType() {
        return treeType;
    }

    public Permission(String key,
                      PermissionType type,
                      String abacPermPresGroupAction,
                      String abacPermPresUserAction,
                      String name,
                      List<Profile> profiles,
                      String description,
                      List<TreeType> treeType,
                      Integer orderNoInNode) {
        this.treeType = treeType;
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
        this.orderNoInNode = orderNoInNode;
    }

    public Permission(PermissionDialogObject object) {
        this(object.getKey(),
                object.getPermissionType(),
                object.getGroupAction(),
                object.getUserAction(),
                object.getName(),
                null,
                object.getDescription(),
                object.getTreeType(),
                null);
    }

    public Permission(PermissionDialogObject object, List<Profile> profiles) {
        this(object.getKey(),
                object.getPermissionType(),
                object.getGroupAction(),
                object.getUserAction(),
                object.getName(),
                profiles,
                object.getDescription(),
                object.getTreeType(),
                null);
    }

    private String getParent(String key) {
        String[] parts = key.split("#");
        if (parts.length <= 1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length - 1; i++) {
            if (i > 0) {
                sb.append("#");
            }
            sb.append(parts[i]);
        }
        return sb.toString();
    }

    public String getParent() {
        return getParent(getKey());
    }

    public PermissionType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getRelKey() {
        String[] parts = getKey().split("#");
        return parts[parts.length - 1];
    }

    public String getKey() {
        if (keyCandidate != null && keyCandidate.getSelectedKey() != null) {
            return keyCandidate.getSelectedKey();
        }
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

    public Integer getOrderNoInNode() {
        return orderNoInNode;
    }

    public Permission withKeyCandidate(KeyCandidate candidate) {
        this.keyCandidate = candidate;
        return this;
    }

    public boolean hasKeyCandidate() {
        return keyCandidate != null;
    }

    public KeyCandidate getKeyCandidate() {
        return keyCandidate;
    }

    public boolean hasKeyConflict() {
        return keyCandidate != null && keyCandidate.hasConflict();
    }

    public void resolveKey(String key) {
        if (keyCandidate == null || key == null) {
            return;
        }
        if (key.equals(keyCandidate.getComputedKey())) {
            keyCandidate.selectComputed();
        } else {
            keyCandidate.resolve();
        }
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
                        "description= " + description + "\n" +
                        "treeType= " + treeType + "\n" +
                        "orderNoInNode= " + orderNoInNode;
    }
}
