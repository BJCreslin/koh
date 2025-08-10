package ru.cbr.koh.panes_storage.panels.permission_migration.permission.factory;

import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.PermissionType;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.TreeType;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder для гибкого создания Permission объектов
 */
public class PermissionBuilder {
    
    private String key;
    private PermissionType type;
    private String groupAction;
    private String userAction;
    private String name;
    private List<Profile> profiles;
    private String description;
    private List<TreeType> treeTypes;
    
    public PermissionBuilder() {
        this.profiles = new ArrayList<>();
        this.treeTypes = new ArrayList<>();
    }
    
    /**
     * Устанавливает ключ разрешения
     */
    public PermissionBuilder key(String key) {
        this.key = key;
        return this;
    }
    
    /**
     * Устанавливает тип разрешения
     */
    public PermissionBuilder type(PermissionType type) {
        this.type = type;
        return this;
    }
    
    /**
     * Автоматически определяет тип на основе ключа
     */
    public PermissionBuilder autoType() {
        if (this.key != null) {
            String relKey = extractRelKey(this.key);
            this.type = PermissionType.getPermissionType(relKey);
        }
        return this;
    }
    
    /**
     * Устанавливает групповое действие
     */
    public PermissionBuilder groupAction(String groupAction) {
        this.groupAction = groupAction;
        return this;
    }
    
    /**
     * Устанавливает пользовательское действие
     */
    public PermissionBuilder userAction(String userAction) {
        this.userAction = userAction;
        return this;
    }
    
    /**
     * Устанавливает имя разрешения
     */
    public PermissionBuilder name(String name) {
        this.name = name;
        return this;
    }
    
    /**
     * Устанавливает описание
     */
    public PermissionBuilder description(String description) {
        this.description = description;
        return this;
    }
    
    /**
     * Добавляет профиль
     */
    public PermissionBuilder addProfile(Profile profile) {
        if (profile != null) {
            this.profiles.add(profile);
        }
        return this;
    }
    
    /**
     * Добавляет список профилей
     */
    public PermissionBuilder addProfiles(List<Profile> profiles) {
        if (profiles != null) {
            this.profiles.addAll(profiles);
        }
        return this;
    }
    
    /**
     * Устанавливает профили (заменяет существующие)
     */
    public PermissionBuilder profiles(List<Profile> profiles) {
        this.profiles = new ArrayList<>(profiles != null ? profiles : new ArrayList<>());
        return this;
    }
    
    /**
     * Добавляет тип дерева
     */
    public PermissionBuilder addTreeType(TreeType treeType) {
        if (treeType != null) {
            this.treeTypes.add(treeType);
        }
        return this;
    }
    
    /**
     * Добавляет список типов деревьев
     */
    public PermissionBuilder addTreeTypes(List<TreeType> treeTypes) {
        if (treeTypes != null) {
            this.treeTypes.addAll(treeTypes);
        }
        return this;
    }
    
    /**
     * Устанавливает типы деревьев (заменяет существующие)
     */
    public PermissionBuilder treeTypes(List<TreeType> treeTypes) {
        this.treeTypes = new ArrayList<>(treeTypes != null ? treeTypes : new ArrayList<>());
        return this;
    }
    
    /**
     * Настраивает разрешение для KO
     */
    public PermissionBuilder forKO() {
        return addTreeType(TreeType.KO);
    }
    
    /**
     * Настраивает разрешение для GIBR
     */
    public PermissionBuilder forGIBR() {
        return addTreeType(TreeType.GIBR);
    }
    
    /**
     * Настраивает разрешение для всех типов деревьев
     */
    public PermissionBuilder forAllTreeTypes() {
        return treeTypes(List.of(TreeType.KO, TreeType.GIBR));
    }
    
    /**
     * Создает Permission объект
     */
    public Permission build() {
        validateRequiredFields();
        
        return new Permission(
            key,
            type,
            groupAction,
            userAction,
            name,
            profiles.isEmpty() ? null : new ArrayList<>(profiles),
            description,
            treeTypes.isEmpty() ? null : new ArrayList<>(treeTypes)
        );
    }
    
    /**
     * Сбрасывает builder к начальному состоянию
     */
    public PermissionBuilder reset() {
        this.key = null;
        this.type = null;
        this.groupAction = null;
        this.userAction = null;
        this.name = null;
        this.profiles = new ArrayList<>();
        this.description = null;
        this.treeTypes = new ArrayList<>();
        return this;
    }
    
    /**
     * Создает копию текущего состояния builder'а
     */
    public PermissionBuilder copy() {
        return new PermissionBuilder()
                .key(this.key)
                .type(this.type)
                .groupAction(this.groupAction)
                .userAction(this.userAction)
                .name(this.name)
                .profiles(this.profiles)
                .description(this.description)
                .treeTypes(this.treeTypes);
    }
    
    private void validateRequiredFields() {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalStateException("Key is required");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalStateException("Name is required");
        }
        if (type == null) {
            throw new IllegalStateException("Type is required");
        }
    }
    
    private String extractRelKey(String key) {
        if (key == null || key.isEmpty()) {
            return "";
        }
        String[] parts = key.split("#");
        return parts[parts.length - 1];
    }
    
    /**
     * Статический метод для создания нового builder'а
     */
    public static PermissionBuilder builder() {
        return new PermissionBuilder();
    }
}
