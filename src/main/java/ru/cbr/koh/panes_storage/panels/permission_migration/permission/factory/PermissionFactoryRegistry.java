package ru.cbr.koh.panes_storage.panels.permission_migration.permission.factory;

import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Реестр фабрик для управления различными типами фабрик Permission
 */
public class PermissionFactoryRegistry {
    
    private final List<PermissionFactory<?>> factories;
    
    public PermissionFactoryRegistry() {
        this.factories = new ArrayList<>();
        initializeDefaultFactories();
    }
    
    /**
     * Инициализирует стандартные фабрики
     */
    private void initializeDefaultFactories() {
        registerFactory(new BasicPermissionFactory());
        registerFactory(new DialogPermissionFactory());
        registerFactory(new ParserPermissionFactory());
    }
    
    /**
     * Регистрирует новую фабрику
     */
    public void registerFactory(PermissionFactory<?> factory) {
        if (factory == null) {
            throw new IllegalArgumentException("Factory cannot be null");
        }
        factories.add(factory);
    }
    
    /**
     * Удаляет фабрику из реестра
     */
    public boolean removeFactory(PermissionFactory<?> factory) {
        return factories.remove(factory);
    }
    
    /**
     * Создает Permission используя подходящую фабрику
     */
    @SuppressWarnings("unchecked")
    public Permission createPermission(Object source) {
        if (source == null) {
            throw new IllegalArgumentException("Source cannot be null");
        }
        
        Optional<PermissionFactory<?>> factory = findSuitableFactory(source);
        
        if (factory.isEmpty()) {
            throw new IllegalArgumentException("No suitable factory found for source type: " + source.getClass().getSimpleName());
        }
        
        return ((PermissionFactory<Object>) factory.get()).create(source);
    }
    
    /**
     * Находит подходящую фабрику для данного источника
     */
    public Optional<PermissionFactory<?>> findSuitableFactory(Object source) {
        return factories.stream()
                .filter(factory -> factory.canHandle(source))
                .findFirst();
    }
    
    /**
     * Проверяет, может ли реестр обработать данный тип источника
     */
    public boolean canHandle(Object source) {
        return findSuitableFactory(source).isPresent();
    }
    
    /**
     * Возвращает список всех зарегистрированных фабрик
     */
    public List<PermissionFactory<?>> getRegisteredFactories() {
        return new ArrayList<>(factories);
    }
    
    /**
     * Возвращает количество зарегистрированных фабрик
     */
    public int getFactoryCount() {
        return factories.size();
    }
    
    /**
     * Очищает все зарегистрированные фабрики
     */
    public void clearFactories() {
        factories.clear();
    }
    
    /**
     * Получает фабрику определенного типа
     */
    @SuppressWarnings("unchecked")
    public <T extends PermissionFactory<?>> Optional<T> getFactory(Class<T> factoryClass) {
        return factories.stream()
                .filter(factoryClass::isInstance)
                .map(factory -> (T) factory)
                .findFirst();
    }
}
