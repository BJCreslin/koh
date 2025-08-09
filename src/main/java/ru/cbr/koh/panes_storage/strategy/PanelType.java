package ru.cbr.koh.panes_storage.strategy;

/**
 * Перечисление типов панелей
 */
public enum PanelType {
    LOGGER_PROXY("Logger Proxy", "Панель для настройки логгирования через прокси"),
    PERMISSION_MIGRATION("Permission Migration", "Панель для миграции разрешений");
    
    private final String displayName;
    private final String description;
    
    PanelType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
}
