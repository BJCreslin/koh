package ru.cbr.koh.exceptions;

/**
 * Исключение для ошибок конфигурации
 */
public class ConfigurationException extends KohException {
    
    public ConfigurationException(String message) {
        super(message);
    }
    
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
