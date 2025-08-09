package ru.cbr.koh.exceptions;

/**
 * Исключение для ошибок парсинга Excel файлов
 */
public class ExcelParsingException extends FileProcessingException {
    
    public ExcelParsingException(String message) {
        super(message);
    }
    
    public ExcelParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
