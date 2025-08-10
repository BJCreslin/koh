package ru.cbr.koh.exceptions;

/**
 * Исключение для ошибок обработки файлов
 */
public class FileProcessingException extends KohException {
    
    public FileProcessingException(String message) {
        super(message);
    }
    
    public FileProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
