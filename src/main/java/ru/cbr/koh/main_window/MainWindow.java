package ru.cbr.koh.main_window;

import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.panes_storage.PanelsHolder;
import ru.cbr.koh.properties.ConfigurationService;
import ru.cbr.koh.exceptions.ConfigurationException;
import ru.cbr.koh.exceptions.SerializationException;
import ru.cbr.koh.utils.ModernTheme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
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
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            logger.warn("Не удалось установить системную тему", e);
        }
        
        JFrame frame = new JFrame(getTitle());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(getHorizontalSize(), getVerticalSize());
        frame.setLocationRelativeTo(null);
        
        ModernTheme.applyToFrame(frame);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(ModernTheme.BACKGROUND_PRIMARY);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(
            ModernTheme.PADDING_MEDIUM, 
            ModernTheme.PADDING_MEDIUM, 
            ModernTheme.PADDING_MEDIUM, 
            ModernTheme.PADDING_MEDIUM
        ));
        
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        ModernTheme.applyToTabbedPane(tabbedPane);
        
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(
            ModernTheme.PADDING_MEDIUM, 0, 0, 0
        ));

        panels.forEach(panel -> {
            JComponent panelComponent = panel.createPanel(frame);
            JPanel wrappedPanel = wrapPanelInModernContainer(panelComponent);
            tabbedPane.addTab(panel.getTitle(), wrappedPanel);
        });

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        frame.add(mainPanel);
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
    
    /**
     * Создает современный заголовок приложения
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        headerPanel.setBorder(ModernTheme.createCardBorder());
        
        JLabel titleLabel = new JLabel(getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(ModernTheme.PRIMARY_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        JLabel subtitleLabel = new JLabel("Помощник разработчика Досье КО");
        subtitleLabel.setFont(ModernTheme.FONT_REGULAR);
        subtitleLabel.setForeground(ModernTheme.TEXT_SECONDARY);
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(subtitleLabel);
        
        JLabel iconLabel = new JLabel("🚀");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, ModernTheme.PADDING_MEDIUM));
        
        headerPanel.add(iconLabel, BorderLayout.WEST);
        headerPanel.add(textPanel, BorderLayout.CENTER);
        
        return headerPanel;
    }
    
    /**
     * Оборачивает панель в современный контейнер
     */
    private JPanel wrapPanelInModernContainer(JComponent panel) {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(ModernTheme.BACKGROUND_PRIMARY);
        container.setBorder(BorderFactory.createEmptyBorder(
            ModernTheme.PADDING_MEDIUM,
            ModernTheme.PADDING_MEDIUM, 
            ModernTheme.PADDING_MEDIUM, 
            ModernTheme.PADDING_MEDIUM
        ));
        
        if (panel instanceof JPanel) {
            panel.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        }
        
        container.add(panel, BorderLayout.CENTER);
        return container;
    }
}
