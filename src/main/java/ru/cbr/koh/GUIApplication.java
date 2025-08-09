package ru.cbr.koh;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.cbr.koh.main_window.MainWindow;
import ru.cbr.koh.properties.ConfigurationService;
import ru.cbr.koh.exceptions.ConfigurationException;
import ru.cbr.koh.utils.ResourceValidator;

public class GUIApplication {

    private static final Logger logger = LogManager.getLogger(GUIApplication.class);

    public static void main(String[] args) {
        logger.info("Запуск GUI приложения...");
        
        // Валидация ресурсов перед запуском
        if (!ResourceValidator.validateAllResources()) {
            logger.error("Критическая ошибка: отсутствуют обязательные ресурсы. Приложение не может быть запущено.");
            System.err.println("Ошибка: отсутствуют критические файлы ресурсов. Проверьте логи для деталей.");
            System.exit(1);
        }
        
        try {
            ConfigurationService configurationService = ConfigurationService.getInstance();
            MainWindow mainWindow = new MainWindow(configurationService);
            mainWindow.start();
        } catch (ConfigurationException e) {
            logger.fatal("Ошибка загрузки конфигурации", e);
            System.err.println("Ошибка загрузки конфигурации: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            logger.fatal("Критическая ошибка при запуске приложения", e);
            System.err.println("Критическая ошибка при запуске приложения: " + e.getMessage());
            System.exit(1);
        }
    }
}
