package ru.cbr.koh.main_window;

import ru.cbr.koh.panes_storage.PanelsHolder;
import ru.cbr.koh.panes_storage.panels.logger_proxy.LoggerProxyPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.InformationPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.ProfilePanel;
import ru.cbr.koh.properties.ConfigurationService;
import ru.cbr.koh.exceptions.ConfigurationException;
import ru.cbr.koh.exceptions.SerializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Optional;


public class MainWindow {

    private static final Logger logger = LogManager.getLogger(MainWindow.class);
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
                try {
                    ProfilePanel.saveCheckBoxesFile();
                    InformationPanel.setInformation();
                    LoggerProxyPanel.saveDossierKoDirectory();
                    logger.info("Приложение завершается корректно");
                } catch (SerializationException ex) {
                    logger.error("Ошибка при сохранении данных перед закрытием приложения", ex);
                    JOptionPane.showMessageDialog(frame, 
                        "Ошибка при сохранении данных: " + ex.getMessage(), 
                        "Ошибка", 
                        JOptionPane.ERROR_MESSAGE);
                }
                System.exit(0);
            }
        });
    }

    private int getVerticalSize() {
        return Integer.parseInt(Optional.ofNullable(ConfigurationService.getProperty("window.verticalSize"))
                .orElseGet(() -> {
                    try {
                        ConfigurationService.setProperty("window.verticalSize", DEFAULT_VERTICAL_SIZE);
                    } catch (ConfigurationException e) {
                        logger.warn("Не удалось сохранить размер окна по умолчанию", e);
                    }
                    return DEFAULT_VERTICAL_SIZE;
                }));
    }

    private int getHorizontalSize() {
        return Integer.parseInt(Optional.ofNullable(ConfigurationService.getProperty("window.horizontalSize"))
                .orElseGet(() -> {
                    try {
                        ConfigurationService.setProperty("window.horizontalSize", DEFAULT_HORIZONTAL_SIZE);
                    } catch (ConfigurationException e) {
                        logger.warn("Не удалось сохранить размер окна по умолчанию", e);
                    }
                    return DEFAULT_HORIZONTAL_SIZE;
                }));
    }

    private String getTitle() {
        return Optional.ofNullable(ConfigurationService.getProperty("window.title"))
                .orElseGet(() -> {
                    try {
                        ConfigurationService.setProperty("window.title", DEFAULT_TITLE);
                    } catch (ConfigurationException e) {
                        logger.warn("Не удалось сохранить заголовок окна по умолчанию", e);
                    }
                    return DEFAULT_TITLE;
                });
    }
}
