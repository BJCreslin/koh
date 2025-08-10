package ru.cbr.koh.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Централизованный обработчик исключений для приложения KOH
 */
public class ExceptionHandler {
    
    private static final Logger logger = LogManager.getLogger(ExceptionHandler.class);
    
    /**
     * Обработка исключения с показом пользователю диалога ошибки
     */
    public static void handleException(String context, Exception e, Component parentComponent) {
        logger.error("Ошибка в контексте '{}': {}", context, e.getMessage(), e);
        
        String userMessage = getUserFriendlyMessage(e);
        String title = getTitle(e);
        
        JOptionPane.showMessageDialog(
            parentComponent,
            context + "\n\n" + userMessage,
            title,
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Обработка исключения только с логированием (без показа пользователю)
     */
    public static void logException(String context, Exception e) {
        logger.error("Ошибка в контексте '{}': {}", context, e.getMessage(), e);
    }
    
    /**
     * Обработка критической ошибки с завершением приложения
     */
    public static void handleCriticalException(String context, Exception e, Component parentComponent) {
        logger.fatal("Критическая ошибка в контексте '{}': {}", context, e.getMessage(), e);
        
        String userMessage = getUserFriendlyMessage(e);
        
        int result = JOptionPane.showConfirmDialog(
            parentComponent,
            context + "\n\n" + userMessage + "\n\nПриложение будет закрыто. Продолжить?",
            "Критическая ошибка",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.ERROR_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
            System.exit(1);
        }
    }
    
    private static String getUserFriendlyMessage(Exception e) {
        if (e instanceof ConfigurationException) {
            return "Ошибка конфигурации: " + e.getMessage();
        } else if (e instanceof ExcelParsingException) {
            return "Ошибка обработки Excel файла: " + e.getMessage();
        } else if (e instanceof FileProcessingException) {
            return "Ошибка обработки файла: " + e.getMessage();
        } else if (e instanceof SerializationException) {
            return "Ошибка сохранения/загрузки данных: " + e.getMessage();
        } else {
            return "Неожиданная ошибка: " + e.getMessage();
        }
    }
    
    private static String getTitle(Exception e) {
        if (e instanceof ConfigurationException) {
            return "Ошибка конфигурации";
        } else if (e instanceof ExcelParsingException) {
            return "Ошибка Excel";
        } else if (e instanceof FileProcessingException) {
            return "Ошибка файла";
        } else if (e instanceof SerializationException) {
            return "Ошибка данных";
        } else {
            return "Ошибка";
        }
    }
}
