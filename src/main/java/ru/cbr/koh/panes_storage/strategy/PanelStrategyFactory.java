package ru.cbr.koh.panes_storage.strategy;

import ru.cbr.koh.panes_storage.strategy.impl.LoggerProxyPanelStrategy;
import ru.cbr.koh.panes_storage.strategy.impl.PermissionMigrationPanelStrategy;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Фабрика для создания стратегий панелей
 */
public class PanelStrategyFactory {
    
    private static final Map<PanelType, Supplier<PanelStrategy>> strategySuppliers;
    
    static {
        strategySuppliers = new EnumMap<>(PanelType.class);
        strategySuppliers.put(PanelType.LOGGER_PROXY, LoggerProxyPanelStrategy::new);
        strategySuppliers.put(PanelType.PERMISSION_MIGRATION, PermissionMigrationPanelStrategy::new);
    }
    
    /**
     * Создать стратегию по типу панели
     * @param panelType тип панели
     * @return стратегия панели
     * @throws IllegalArgumentException если тип панели не поддерживается
     */
    public static PanelStrategy createStrategy(PanelType panelType) {
        Supplier<PanelStrategy> supplier = strategySuppliers.get(panelType);
        if (supplier == null) {
            throw new IllegalArgumentException("Неподдерживаемый тип панели: " + panelType);
        }
        return supplier.get();
    }
    
    /**
     * Проверить, поддерживается ли тип панели
     * @param panelType тип панели
     * @return true, если тип поддерживается
     */
    public static boolean isSupported(PanelType panelType) {
        return strategySuppliers.containsKey(panelType);
    }
}
