package ru.cbr.koh.properties;

import java.util.List;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

/**
 * Интерфейс для работы с конфигурацией приложения
 */
public interface ConfigurationProvider {
    
    /**
     * Получить строковое значение по ключу
     */
    String getString(String key);
    
    /**
     * Получить строковое значение по ключу с значением по умолчанию
     */
    String getString(String key, String defaultValue);
    
    /**
     * Получить целочисленное значение по ключу
     */
    int getInt(String key, int defaultValue);
    
    /**
     * Получить булево значение по ключу
     */
    boolean getBoolean(String key, boolean defaultValue);
    
    /**
     * Установить значение по ключу
     */
    void setProperty(String key, String value);
    
    /**
     * Получить размер окна по горизонтали
     */
    int getHorizontalSize();
    
    /**
     * Получить размер окна по вертикали
     */
    int getVerticalSize();
    
    /**
     * Получить заголовок окна
     */
    String getTitle();
    
    /**
     * Получить автора истории
     */
    String getAuthor();
    
    /**
     * Получить номер истории
     */
    String getStoryNumber();
    
    /**
     * Получить имя истории
     */
    String getStoryName();
    
    /**
     * Получить ключ истории
     */
    String getStoryKey();
    
    /**
     * Проверить, нужно ли сохранять ABAC файл
     */
    boolean getSaveAbacPolitics();
    
    /**
     * Получить имя ABAC файла
     */
    String getAbacFileName();
    
    /**
     * Получить путь к файлу атрибутов ABAC
     */
    String getAbacAttributeCodeFilePath();
    
    /**
     * Проверить, используется ли Excel
     */
    boolean getFromExcel();
    
    /**
     * Получить путь к Excel файлу
     */
    String getPathExcel();
    
    /**
     * Установить путь к Excel файлу
     */
    void setPathExcel(String pathExcel);
    
    /**
     * Получить ключевой текст миграции
     */
    String getMigrationKeyText();
    
    /**
     * Получить автора миграции
     */
    String getMigrationAuthor();
    
    /**
     * Получить номер истории миграции
     */
    String getMigrationStoryNumber();
    
    /**
     * Получить имя вкладки миграции
     */
    String getMigrationTabName();
    
    /**
     * Получить все профили
     */
    List<Profile> getAllProfiles();
    
    /**
     * Получить профили без SAR и регионального куратора
     */
    List<Profile> getAllWithoutSarAndRegionalCurator();
    
    /**
     * Получить профили без SAR
     */
    List<Profile> getAllWithoutSar();
    
    /**
     * Получить профили без SAR, регионального куратора и координатора-аналитика-методолога ДНСЗКО
     */
    List<Profile> getAllWithoutSarAndRegionalCuratorAndCoordinatorAnalystMethotologDNSZKO();
    
    /**
     * Получить профили БА и другие
     */
    List<Profile> getBaAndOther();
}
