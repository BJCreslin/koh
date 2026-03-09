package ru.cbr.koh.properties;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class ConfigManager {

    private static final String CONFIG_FILE = "config.properties";
    private static final Path CONFIG_PATH = Path.of(CONFIG_FILE);
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private ConfigManager() {
    }

    public static synchronized String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static synchronized void setProperty(String key, String value) {
        PROPERTIES.setProperty(key, value);
        saveProperties();
    }

    private static void loadProperties() {
        if (!Files.exists(CONFIG_PATH)) {
            throw new IllegalStateException("Файл конфигурации не найден: " + CONFIG_PATH.toAbsolutePath());
        }
        try (InputStream input = Files.newInputStream(CONFIG_PATH)) {
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось загрузить " + CONFIG_PATH.toAbsolutePath(), e);
        }
    }

    private static void saveProperties() {
        try (OutputStream output = Files.newOutputStream(CONFIG_PATH)) {
            PROPERTIES.store(output, "Updated properties");
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка сохранения файла " + CONFIG_PATH.toAbsolutePath(), e);
        }
    }
}
