package ru.cbr.koh.panes_storage.panels.logger_proxy;

import ru.cbr.koh.panes_storage.PaneInterface;

import javax.swing.*;

public class LoggerProxyPanel  implements PaneInterface {

    @Override
    public String getTitle() {
        return "Logger Proxy";
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        JPanel panel = new JPanel();


        return panel;
    }
}
