package ru.cbr.koh.exceptions;

/**
 * Базовое исключение для приложения KOH
 */
public class KohException extends Exception {
    
    public KohException(String message) {
        super(message);
    }
    
    public KohException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public KohException(Throwable cause) {
        super(cause);
    }
}
