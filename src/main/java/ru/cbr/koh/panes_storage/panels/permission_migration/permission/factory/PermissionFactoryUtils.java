package ru.cbr.koh.panes_storage.panels.permission_migration.permission.factory;

import ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser.ParserPermission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.dialog_objects.PermissionDialogObject;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.PermissionType;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.TreeType;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

import java.util.List;

/**
 * Утилитный класс для упрощения создания Permission объектов через фабрики
 */
public final class PermissionFactoryUtils {
    
    private static final PermissionFactoryRegistry registry = new PermissionFactoryRegistry();
    
    private PermissionFactoryUtils() {
        // Utility class
    }
    
    /**
     * Получает реестр фабрик
     */
    public static PermissionFactoryRegistry getRegistry() {
        return registry;
    }
    
    /**
     * Создает Permission из любого поддерживаемого источника
     */
    public static Permission createPermission(Object source) {
        return registry.createPermission(source);
    }
    
    /**
     * Создает Permission с минимальными параметрами
     */
    public static Permission createMinimal(String key, PermissionType type, String name) {
        BasicPermissionFactory factory = registry.getFactory(BasicPermissionFactory.class)
                .orElse(new BasicPermissionFactory());
        return factory.createMinimal(key, type, name);
    }
    
    /**
     * Создает Permission для KO департамента
     */
    public static Permission createForKO(String key, String name, String groupAction, String userAction) {
        BasicPermissionFactory factory = registry.getFactory(BasicPermissionFactory.class)
                .orElse(new BasicPermissionFactory());
        return factory.createForKO(key, name, groupAction, userAction);
    }
    
    /**
     * Создает Permission для GIBR департамента
     */
    public static Permission createForGIBR(String key, String name, String groupAction, String userAction) {
        BasicPermissionFactory factory = registry.getFactory(BasicPermissionFactory.class)
                .orElse(new BasicPermissionFactory());
        return factory.createForGIBR(key, name, groupAction, userAction);
    }
    
    /**
     * Создает Permission из DialogObject с профилями
     */
    public static Permission createFromDialog(PermissionDialogObject dialogObject, List<Profile> profiles) {
        DialogPermissionFactory factory = registry.getFactory(DialogPermissionFactory.class)
                .orElse(new DialogPermissionFactory());
        return factory.createWithProfiles(dialogObject, profiles);
    }
    
    /**
     * Создает Permission из DialogObject для департамента
     */
    public static Permission createFromDialogForDepartment(PermissionDialogObject dialogObject, List<Profile> departmentProfiles) {
        DialogPermissionFactory factory = registry.getFactory(DialogPermissionFactory.class)
                .orElse(new DialogPermissionFactory());
        return factory.createForDepartment(dialogObject, departmentProfiles);
    }
    
    /**
     * Создает Permission из ParserPermission только если требуется сохранение
     */
    public static Permission createFromParserIfNeeded(ParserPermission parserPermission) {
        ParserPermissionFactory factory = registry.getFactory(ParserPermissionFactory.class)
                .orElse(new ParserPermissionFactory());
        return factory.createIfNeedSave(parserPermission);
    }
    
    /**
     * Создает Permission из ParserPermission для банко-зависимых разрешений
     */
    public static Permission createFromParserForBankDependent(ParserPermission parserPermission) {
        ParserPermissionFactory factory = registry.getFactory(ParserPermissionFactory.class)
                .orElse(new ParserPermissionFactory());
        return factory.createForBankDependent(parserPermission);
    }
    
    /**
     * Создает новый Builder
     */
    public static PermissionBuilder builder() {
        return PermissionBuilder.builder();
    }
    
    /**
     * Создает Builder с предустановленными значениями для KO
     */
    public static PermissionBuilder builderForKO(String key, String name) {
        return PermissionBuilder.builder()
                .key(key)
                .name(name)
                .autoType()
                .forKO();
    }
    
    /**
     * Создает Builder с предустановленными значениями для GIBR
     */
    public static PermissionBuilder builderForGIBR(String key, String name) {
        return PermissionBuilder.builder()
                .key(key)
                .name(name)
                .autoType()
                .forGIBR();
    }
    
    /**
     * Создает Builder для универсального разрешения (все типы деревьев)
     */
    public static PermissionBuilder builderForAll(String key, String name) {
        return PermissionBuilder.builder()
                .key(key)
                .name(name)
                .autoType()
                .forAllTreeTypes();
    }
    
    /**
     * Проверяет, может ли система создать Permission из данного источника
     */
    public static boolean canCreateFrom(Object source) {
        return registry.canHandle(source);
    }
    
    /**
     * Регистрирует кастомную фабрику
     */
    public static void registerCustomFactory(PermissionFactory<?> factory) {
        registry.registerFactory(factory);
    }
    
    /**
     * Создает Permission с автоопределением типа на основе ключа
     */
    public static Permission createWithAutoType(String key, String name, String groupAction, String userAction, List<TreeType> treeTypes) {
        return builder()
                .key(key)
                .name(name)
                .autoType()
                .groupAction(groupAction)
                .userAction(userAction)
                .treeTypes(treeTypes)
                .build();
    }
}
