package ru.cbr.koh.properties;

import org.apache.commons.math3.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.cbr.koh.exceptions.ConfigurationException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

public class ConfigurationService implements ConfigurationProvider {
    
    private static final Logger logger = LogManager.getLogger(ConfigurationService.class);
    private static final String CONFIG_FILE = "config.properties";
    private static final Properties properties = new Properties();
    private static ConfigurationService instance;

    // Поля для типизированного доступа к свойствам
    private int horizontalSize;
    private int verticalSize;
    private String title;
    private String author;
    private String storyNumber;
    private String storyName;
    private String storyKey;
    private Boolean shouldWriteAbacFile;
    private Boolean fromExcel;
    private String abacFileName;
    private String abacAttributeCodeFilePath;
    private String pathExcel;
    
    // Migration configuration fields
    private String migrationKeyText;
    private String migrationAuthor;
    private String migrationStoryNumber;
    private String migrationTabName;
    
    // Profile parser
    private final ProfileParser profileParser;

    // Singleton pattern
    public static ConfigurationService getInstance() throws ConfigurationException {
        if (instance == null) {
            instance = new ConfigurationService();
        }
        return instance;
    }

    private ConfigurationService() throws ConfigurationException {
        loadProperties();
        setPropertiesFields();
        this.profileParser = new ProfileParser(properties);
    }

    private void loadProperties() throws ConfigurationException {
        try (InputStream inStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inStream == null) {
                throw new ConfigurationException("Файл конфигурации не найден: " + CONFIG_FILE);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, StandardCharsets.UTF_8))) {
                properties.load(reader);
                logger.info("Конфигурация успешно загружена из {}", CONFIG_FILE);
            }
        } catch (IOException e) {
            logger.error("Не удалось загрузить {}", CONFIG_FILE, e);
            throw new ConfigurationException("Ошибка загрузки файла конфигурации: " + CONFIG_FILE, e);
        }
    }

    private void setPropertiesFields() throws ConfigurationException {
        try {
            this.horizontalSize = Integer.parseInt(properties.getProperty("window.size.horizontal", "800"));
            this.verticalSize = Integer.parseInt(properties.getProperty("window.size.vertical", "600"));
            this.title = properties.getProperty("window.title", "Default Title");

            this.author = properties.getProperty("story.author");
            this.storyNumber = properties.getProperty("story.number");
            this.storyName = properties.getProperty("story.name");
            this.storyKey = properties.getProperty("story.key");
            this.shouldWriteAbacFile = Boolean.parseBoolean(properties.getProperty("story.shouldWriteAbacFile", "false"));
            this.fromExcel = Boolean.parseBoolean(properties.getProperty("story.fromExcel", "false"));

            this.abacFileName = properties.getProperty("abac.fileName");
            this.abacAttributeCodeFilePath = properties.getProperty("abac.attributeCodeFilePath");

            this.pathExcel = properties.getProperty("story.pathExcel");
            
            // Migration configuration
            this.migrationKeyText = properties.getProperty("migration.keyText");
            this.migrationAuthor = properties.getProperty("migration.author");
            this.migrationStoryNumber = properties.getProperty("migration.storyNumber");
            this.migrationTabName = properties.getProperty("migration.tabName");
            
            logger.debug("Поля конфигурации успешно инициализированы");
        } catch (NumberFormatException e) {
            logger.error("Ошибка парсинга числовых значений из properties", e);
            throw new ConfigurationException("Некорректные числовые значения в конфигурации", e);
        }
    }

    // Реализация методов интерфейса ConfigurationProvider
    @Override
    public String getString(String key) {
        return properties.getProperty(key);
    }
    
    @Override
    public String getString(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    @Override
    public int getInt(String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.warn("Некорректное числовое значение для ключа {}: {}", key, value);
            return defaultValue;
        }
    }
    
    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }
    
    @Override
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        try {
            saveProperties();
            // Обновляем поля экземпляра если он существует
            if (instance != null) {
                instance.setPropertiesFields();
            }
        } catch (ConfigurationException e) {
            logger.error("Ошибка при сохранении свойства", e);
        }
    }
    
    // Статические методы для совместимости с ConfigManager
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void setPropertyStatic(String key, String value) throws ConfigurationException {
        properties.setProperty(key, value);
        saveProperties();
        // Обновляем поля экземпляра если он существует
        if (instance != null) {
            instance.setPropertiesFields();
        }
    }

    public static void setProperties(List<Pair<String, String>> propers) throws ConfigurationException {
        for (Pair<String, String> property : propers) {
            properties.setProperty(property.getFirst(), property.getSecond());
        }
        saveProperties();
        // Обновляем поля экземпляра если он существует
        if (instance != null) {
            instance.setPropertiesFields();
        }
    }

    private static void saveProperties() throws ConfigurationException {
        try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
            properties.store(output, "Обновлённые свойства");
            logger.info("Конфигурация успешно сохранена в {}", CONFIG_FILE);
        } catch (IOException e) {
            logger.error("Ошибка сохранения файла {}", CONFIG_FILE, e);
            throw new ConfigurationException("Не удалось сохранить конфигурацию в файл: " + CONFIG_FILE, e);
        }
    }

    // Геттеры для типизированного доступа (из PropertiesService)
    public int getHorizontalSize() {
        return horizontalSize;
    }

    public int getVerticalSize() {
        return verticalSize;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getStoryNumber() {
        return storyNumber;
    }

    public String getStoryName() {
        return storyName;
    }

    public String getStoryKey() {
        return storyKey;
    }

    public boolean getSaveAbacPolitics() {
        return shouldWriteAbacFile != null && shouldWriteAbacFile;
    }

    public String getAbacFileName() {
        return abacFileName;
    }

    public String getAbacAttributeCodeFilePath() {
        return abacAttributeCodeFilePath;
    }

    public boolean getFromExcel() {
        return fromExcel != null && fromExcel;
    }

    public String getPathExcel() {
        return pathExcel;
    }

    @Override
    public void setPathExcel(String pathExcel) {
        if (pathExcel != null && !pathExcel.isEmpty()) {
            this.pathExcel = pathExcel;
            setProperty("story.pathExcel", pathExcel);
        }
    }

    // Migration configuration getters
    public String getMigrationKeyText() {
        return migrationKeyText;
    }

    public String getMigrationAuthor() {
        return migrationAuthor;
    }

    public String getMigrationStoryNumber() {
        return migrationStoryNumber;
    }

    public String getMigrationTabName() {
        return migrationTabName;
    }

    // Profile group methods
    @Override
    public List<Profile> getAllProfiles() {
        return profileParser.getAllProfiles();
    }

    @Override
    public List<Profile> getAllWithoutSarAndRegionalCurator() {
        return profileParser.getAllWithoutSarAndRegionalCurator();
    }

    @Override
    public List<Profile> getAllWithoutSar() {
        return profileParser.getAllWithoutSar();
    }

    @Override
    public List<Profile> getAllWithoutSarAndRegionalCuratorAndCoordinatorAnalystMethotologDNSZKO() {
        return profileParser.getAllWithoutSarAndRegionalCuratorAndCoordinatorAnalystMethotologDNSZKO();
    }

    @Override
    public List<Profile> getBaAndOther() {
        return profileParser.getBaAndOther();
    }
}
