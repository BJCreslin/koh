package ru.cbr.koh.app.error;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SwingErrorHandler implements ErrorHandler {

    private static final Logger LOGGER = Logger.getLogger(SwingErrorHandler.class.getName());

    @Override
    public void handle(Component parent, String userMessage, Exception exception) {
        LOGGER.log(Level.SEVERE, userMessage, exception);
        String details = exception.getMessage();
        if (details == null || details.isBlank()) {
            details = exception.getClass().getSimpleName();
        }

        if (GraphicsEnvironment.isHeadless()) {
            return;
        }

        JOptionPane.showMessageDialog(
                parent,
                userMessage + "\n\n" + details,
                "Ошибка",
                JOptionPane.ERROR_MESSAGE);
    }
}
