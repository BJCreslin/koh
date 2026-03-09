package ru.cbr.koh.app.error;

import java.awt.*;

public interface ErrorHandler {

    void handle(Component parent, String userMessage, Exception exception);
}
