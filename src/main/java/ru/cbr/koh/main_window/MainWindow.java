package ru.cbr.koh.main_window;

import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.panes_storage.PanelsHolder;
import ru.cbr.koh.properties.ConfigurationService;
import ru.cbr.koh.exceptions.ConfigurationException;
import ru.cbr.koh.exceptions.SerializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.ArrayList;


public class MainWindow {

    private static final Logger logger = LogManager.getLogger(MainWindow.class);
    private static final String DEFAULT_TITLE = "KOH (KO Helper)";
    private static final String DEFAULT_HORIZONTAL_SIZE = "900";
    private static final String DEFAULT_VERTICAL_SIZE = "900";
    
    private final ConfigurationService configurationService;
    private final List<PaneInterface> panels;

    public MainWindow(ConfigurationService configurationService) throws ConfigurationException {
        this.configurationService = configurationService;
        this.panels = new ArrayList<>();
        initializePanels();
    }
    
    private void initializePanels() throws ConfigurationException {
        PanelsHolder panelsHolder = new PanelsHolder();
        this.panels.addAll(panelsHolder.getPanels());
    }

    public void start() {
        JFrame frame = new JFrame(getTitle());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(getHorizontalSize(), getVerticalSize());
        frame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        panels.forEach(panel -> tabbedPane.addTab(panel.getTitle(), panel.createPanel(frame)));

        frame.add(tabbedPane);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    saveAllPanelData();
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
    
    private void saveAllPanelData() throws SerializationException {
        for (PaneInterface panel : panels) {
            if (panel instanceof SaveablePanel) {
                ((SaveablePanel) panel).saveData();
            }
        }
    }

    private int getVerticalSize() {
        String verticalSize = ConfigurationService.getProperty("window.verticalSize");
        if (verticalSize != null) {
            return Integer.parseInt(verticalSize);
        }
        
        try {
            ConfigurationService.setProperty("window.verticalSize", DEFAULT_VERTICAL_SIZE);
        } catch (ConfigurationException e) {
            logger.warn("Не удалось сохранить размер окна по умолчанию", e);
        }
        return Integer.parseInt(DEFAULT_VERTICAL_SIZE);
    }

    private int getHorizontalSize() {
        String horizontalSize = ConfigurationService.getProperty("window.horizontalSize");
        if (horizontalSize != null) {
            return Integer.parseInt(horizontalSize);
        }
        
        try {
            ConfigurationService.setProperty("window.horizontalSize", DEFAULT_HORIZONTAL_SIZE);
        } catch (ConfigurationException e) {
            logger.warn("Не удалось сохранить размер окна по умолчанию", e);
        }
        return Integer.parseInt(DEFAULT_HORIZONTAL_SIZE);
    }

    private String getTitle() {
        String title = ConfigurationService.getProperty("window.title");
        if (title != null) {
            return title;
        }
        
        try {
            ConfigurationService.setProperty("window.title", DEFAULT_TITLE);
        } catch (ConfigurationException e) {
            logger.warn("Не удалось сохранить заголовок окна по умолчанию", e);
        }
        return DEFAULT_TITLE;
    }
}
