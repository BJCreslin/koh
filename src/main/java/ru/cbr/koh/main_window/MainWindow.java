package ru.cbr.koh.main_window;

import ru.cbr.koh.panes_storage.PanelsHolder;
import ru.cbr.koh.panes_storage.panels.logger_proxy.LoggerProxyPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.InformationPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.ProfilePanel;
import ru.cbr.koh.properties.ConfigManager;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Optional;


public class MainWindow {

    private static final String DEFAULT_TITLE = "KOH (KO Helper)";
    private static final String DEFAULT_HORIZONTAL_SIZE = "900";
    private static final String DEFAULT_VERTICAL_SIZE = "900";


    public void start() {
        JFrame frame = new JFrame(getTitle());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(getHorizontalSize(), getVerticalSize());
        frame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        PanelsHolder panelsHolder = new PanelsHolder();

        panelsHolder.getPanels().forEach(it -> tabbedPane.addTab(it.getTitle(), it.createPanel(frame)));

        frame.add(tabbedPane);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ProfilePanel.saveCheckBoxesFile();
                InformationPanel.setInformation();
                LoggerProxyPanel.saveDossierKoDirectory();
                System.exit(0);
            }
        });
    }

    private int getVerticalSize() {
        return Integer.parseInt(Optional.ofNullable(ConfigManager.getProperty("window.verticalSize"))
                .orElseGet(() -> {
                    ConfigManager.setProperty("window.verticalSize", DEFAULT_VERTICAL_SIZE);
                    return DEFAULT_VERTICAL_SIZE;
                }));
    }

    private int getHorizontalSize() {
        return Integer.parseInt(Optional.ofNullable(ConfigManager.getProperty("window.horizontalSize"))
                .orElseGet(() -> {
                    ConfigManager.setProperty("window.horizontalSize", DEFAULT_HORIZONTAL_SIZE);
                    return DEFAULT_HORIZONTAL_SIZE;
                }));
    }

    private String getTitle() {
        return Optional.ofNullable(ConfigManager.getProperty("window.title"))
                .orElseGet(() -> {
                    ConfigManager.setProperty("window.title", DEFAULT_TITLE);
                    return DEFAULT_TITLE;
                });
    }
}
