package ru.cbr.koh.panes_storage.panels.permission_migration.permission.factory;

import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;

/**
 * Интерфейс фабрики для создания объектов Permission
 */
public interface PermissionFactory<T> {
    
    /**
     * Создает объект Permission на основе входных данных
     * 
     * @param source исходные данные для создания Permission
     * @return созданный объект Permission
     */
    Permission create(T source);
    
    /**
     * Проверяет, может ли фабрика обработать данный тип источника
     * 
     * @param source исходные данные
     * @return true, если фабрика может обработать данный источник
     */
    boolean canHandle(Object source);
}
