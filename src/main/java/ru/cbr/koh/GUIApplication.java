package ru.cbr.koh;


import ru.cbr.koh.app.error.SwingErrorHandler;
import ru.cbr.koh.main_window.MainWindow;
import ru.cbr.koh.properties.ConfigurationSchemaValidator;
import ru.cbr.koh.properties.PropertiesService;

public class GUIApplication {

    public static void main(String[] args) {
        try {
            ConfigurationSchemaValidator.validate(PropertiesService.getInstance());
            MainWindow mainWindow = new MainWindow();
            mainWindow.start();
        } catch (RuntimeException exception) {
            new SwingErrorHandler().handle(null, "Ошибка инициализации приложения", exception);
        }
    }

}
