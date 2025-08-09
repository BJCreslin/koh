package ru.cbr.koh.properties;

import org.apache.commons.math3.util.Pair;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

public class ConfigurationService {
    
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

    // Singleton pattern
    public static ConfigurationService getInstance() {
        if (instance == null) {
            instance = new ConfigurationService();
        }
        return instance;
    }

    private ConfigurationService() {
        loadProperties();
        setPropertiesFields();
    }

    private void loadProperties() {
        try (InputStream inStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inStream != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, StandardCharsets.UTF_8))) {
                    properties.load(reader);
                }
            }
        } catch (IOException e) {
            System.err.println("Не удалось загрузить " + CONFIG_FILE);
            e.printStackTrace();
        }
    }

    private void setPropertiesFields() {
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
        } catch (NumberFormatException e) {
            System.err.println("Ошибка парсинга числовых значений из properties");
            e.printStackTrace();
        }
    }

    // Статические методы для совместимости с ConfigManager
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
        saveProperties();
        // Обновляем поля экземпляра если он существует
        if (instance != null) {
            instance.setPropertiesFields();
        }
    }

    public static void setProperties(List<Pair<String, String>> propers) {
        for (Pair<String, String> property : propers) {
            properties.setProperty(property.getFirst(), property.getSecond());
        }
        saveProperties();
        // Обновляем поля экземпляра если он существует
        if (instance != null) {
            instance.setPropertiesFields();
        }
    }

    private static void saveProperties() {
        try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
            properties.store(output, "Обновлённые свойства");
        } catch (IOException e) {
            System.err.println("Ошибка сохранения файла " + CONFIG_FILE);
            e.printStackTrace();
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

    public void setPathExcel(String pathExcel) {
        if (pathExcel != null && !pathExcel.isEmpty()) {
            this.pathExcel = pathExcel;
            setProperty("story.pathExcel", pathExcel);
        }
    }
}
