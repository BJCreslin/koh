package ru.cbr.koh.properties;

import org.apache.commons.math3.util.Pair;

import java.io.*;
import java.util.List;
import java.util.Properties;

public final class ConfigManager {
    private static final Properties properties = new Properties();
    private static final String CONFIG_FILE = "config.properties";

    static {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Не удалось загрузить " + CONFIG_FILE);
            e.printStackTrace();
        }
    }

    // Метод для получения значения свойства по ключу
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Метод для обновления значения свойства и сохранения изменений
    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
        saveProperties();
    }

    // Метод для записи изменений в файл
    private static void saveProperties() {
        try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
            properties.store(output, "Обновлённые свойства");
        } catch (IOException e) {
            System.err.println("Ошибка сохранения файла " + CONFIG_FILE);
            e.printStackTrace();
        }
    }

    public static void setProperties(List<Pair<String, String>> propers) {
        for (Pair<String, String> property : propers) {
            properties.setProperty(property.getFirst(), property.getSecond());
        }
        saveProperties();
    }
}
