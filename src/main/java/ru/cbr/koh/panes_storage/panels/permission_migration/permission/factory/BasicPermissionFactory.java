package ru.cbr.koh.panes_storage.panels.permission_migration.permission.factory;

import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.PermissionType;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.TreeType;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

import java.util.List;

/**
 * Фабрика для создания Permission объектов из базовых параметров
 */
public class BasicPermissionFactory implements PermissionFactory<BasicPermissionFactory.PermissionData> {

    @Override
    public Permission create(PermissionData source) {
        if (source == null) {
            throw new IllegalArgumentException("Source data cannot be null");
        }
        
        return new Permission(
            source.key(),
            source.type(),
            source.groupAction(),
            source.userAction(),
            source.name(),
            source.profiles(),
            source.description(),
            source.treeTypes()
        );
    }

    @Override
    public boolean canHandle(Object source) {
        return source instanceof PermissionData;
    }

    /**
     * Создает Permission с минимальными обязательными параметрами
     */
    public Permission createMinimal(String key, PermissionType type, String name) {
        return create(new PermissionData(
            key,
            type,
            null,
            null,
            name,
            null,
            null,
            null
        ));
    }

    /**
     * Создает Permission для KO типа с базовыми настройками
     */
    public Permission createForKO(String key, String name, String groupAction, String userAction) {
        PermissionType type = PermissionType.getPermissionType(extractRelKey(key));
        return create(new PermissionData(
            key,
            type,
            groupAction,
            userAction,
            name,
            null,
            null,
            List.of(TreeType.KO)
        ));
    }

    /**
     * Создает Permission для GIBR типа с базовыми настройками
     */
    public Permission createForGIBR(String key, String name, String groupAction, String userAction) {
        PermissionType type = PermissionType.getPermissionType(extractRelKey(key));
        return create(new PermissionData(
            key,
            type,
            groupAction,
            userAction,
            name,
            null,
            null,
            List.of(TreeType.GIBR)
        ));
    }

    private String extractRelKey(String key) {
        if (key == null || key.isEmpty()) {
            return "";
        }
        String[] parts = key.split("#");
        return parts[parts.length - 1];
    }

    /**
     * Запись для хранения данных Permission
     */
    public record PermissionData(
        String key,
        PermissionType type,
        String groupAction,
        String userAction,
        String name,
        List<Profile> profiles,
        String description,
        List<TreeType> treeTypes
    ) {}
}
