package ru.cbr.koh.panes_storage.panels.permission_migration.permission.factory;

import ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser.ParserPermission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.PermissionType;

/**
 * Фабрика для создания Permission объектов из ParserPermission
 */
public class ParserPermissionFactory implements PermissionFactory<ParserPermission> {

    @Override
    public Permission create(ParserPermission source) {
        if (source == null) {
            throw new IllegalArgumentException("ParserPermission cannot be null");
        }

        PermissionType type = PermissionType.getPermissionType(source.relKey());
        
        return new Permission(
            source.key(),
            type,
            determineGroupAction(source),
            determineUserAction(source),
            source.name(),
            source.profiles(),
            source.description(),
            source.types()
        );
    }

    @Override
    public boolean canHandle(Object source) {
        return source instanceof ParserPermission;
    }

    /**
     * Создает Permission из ParserPermission только если требуется сохранение
     */
    public Permission createIfNeedSave(ParserPermission source) {
        if (source == null) {
            throw new IllegalArgumentException("ParserPermission cannot be null");
        }
        
        if (!source.needSave()) {
            return null;
        }
        
        return create(source);
    }

    /**
     * Создает Permission из ParserPermission для банко-зависимых разрешений
     */
    public Permission createForBankDependent(ParserPermission source) {
        if (source == null) {
            throw new IllegalArgumentException("ParserPermission cannot be null");
        }
        
        if (!source.isBankDependent()) {
            throw new IllegalArgumentException("ParserPermission is not bank dependent");
        }
        
        return create(source);
    }

    private String determineGroupAction(ParserPermission source) {
        // Логика определения group action на основе политики и других параметров
        if (source.politic() != null && !source.politic().isEmpty()) {
            return source.politic() + "_GROUP";
        }
        return null;
    }

    private String determineUserAction(ParserPermission source) {
        // Логика определения user action на основе политики и других параметров
        if (source.politic() != null && !source.politic().isEmpty()) {
            return source.politic() + "_USER";
        }
        return null;
    }
}
