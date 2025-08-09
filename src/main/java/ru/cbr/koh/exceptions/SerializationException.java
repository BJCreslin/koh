package ru.cbr.koh.exceptions;

/**
 * Исключение для ошибок сериализации/десериализации
 */
public class SerializationException extends KohException {
    
    public SerializationException(String message) {
        super(message);
    }
    
    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
