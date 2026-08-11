package ru.cbr.koh.panes_storage.panels.logger_proxy;

import ru.cbr.koh.app.AppContext;
import ru.cbr.koh.app.error.ErrorHandler;
import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.panes_storage.panels.logger_proxy.service.SpyService;
import ru.cbr.koh.panes_storage.panels.logger_proxy.service.SpyServiceImpl;
import ru.cbr.koh.ui.BusinessTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.nio.file.Files;
import java.nio.file.Path;

public class LoggerProxyPanel implements PaneInterface {

    private final JTextField dossierKoProjectField = new JTextField();

    private final SpyService spyService;
    private final LoggerDirectoryStorage loggerDirectoryStorage;
    private final ErrorHandler errorHandler;

    private JToggleButton toggleButton;
    private boolean isProgrammaticToggleChange;

    public LoggerProxyPanel() {
        this(new SpyServiceImpl(), new LoggerDirectoryStorage(), null);
    }

    public LoggerProxyPanel(AppContext appContext) {
        this(new SpyServiceImpl(), new LoggerDirectoryStorage(), appContext.getErrorHandler());
    }

    LoggerProxyPanel(SpyService spyService,
                     LoggerDirectoryStorage loggerDirectoryStorage,
                     ErrorHandler errorHandler) {
        this.spyService = spyService;
        this.loggerDirectoryStorage = loggerDirectoryStorage;
        this.errorHandler = errorHandler;
    }

    @Override
    public String getTitle() {
        return "SQL Logging";
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BusinessTheme.pagePadding());

        JLabel label = BusinessTheme.createPageTitle("Hibernate SQL Logging");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);

        panel.add(Box.createVerticalStrut(8));
        JLabel subtitle = new JLabel("Select Dossier KO project directory");
        BusinessTheme.styleFormLabel(subtitle);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(subtitle);

        dossierKoProjectField.setMaximumSize(new Dimension(Integer.MAX_VALUE, dossierKoProjectField.getPreferredSize().height));
        dossierKoProjectField.setText(loggerDirectoryStorage.load());
        dossierKoProjectField.setAlignmentX(Component.CENTER_ALIGNMENT);
        dossierKoProjectField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                syncToggleStateFromProject();
            }
        });
        panel.add(Box.createVerticalStrut(10));
        panel.add(dossierKoProjectField);

        ImageIcon originalIcon = new ImageIcon("images.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JButton folderButton = new JButton("Select Dossier Ko Directory", scaledIcon);
        folderButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        folderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        BusinessTheme.stylePrimaryButton(folderButton);
        folderButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                dossierKoProjectField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                syncToggleStateFromProject();
            }
        });

        panel.add(Box.createVerticalStrut(10));
        panel.add(folderButton);

        JLabel proxyLabel = new JLabel("Logging mode");
        BusinessTheme.styleSectionLabel(proxyLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(proxyLabel);

        toggleButton = new JToggleButton("OFF");
        toggleButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        toggleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        BusinessTheme.styleToggleButton(toggleButton, false);

        toggleButton.addItemListener(e -> {
            if (isProgrammaticToggleChange) {
                return;
            }
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (runSqlLoggingAction(this::setSqlLogging, panel,
                        "Не удалось включить SQL logging. Проверьте путь и наличие application.yml/application.properties.")) {
                    BusinessTheme.styleToggleButton(toggleButton, true);
                } else {
                    setToggleState(toggleButton, false);
                }
            } else {
                if (runSqlLoggingAction(this::removeSqlLogging, panel,
                        "Не удалось выключить SQL logging. Проверьте права на запись в конфиг приложения.")) {
                    BusinessTheme.styleToggleButton(toggleButton, false);
                } else {
                    setToggleState(toggleButton, true);
                }
            }
        });

        panel.add(Box.createVerticalStrut(10));
        panel.add(toggleButton);
        syncToggleStateFromProject();

        return panel;
    }

    @Override
    public void onClose() {
        loggerDirectoryStorage.save(dossierKoProjectField.getText());
    }

    private void setSqlLogging() {
        String dossierKoDirectory = getValidatedDirectory();
        spyService.addLoggerProxy(dossierKoDirectory);
    }

    private void removeSqlLogging() {
        String dossierKoDirectory = getValidatedDirectory();
        spyService.removeLoggerProxy(dossierKoDirectory);
    }

    private boolean runSqlLoggingAction(Runnable action, JComponent parent, String errorMessage) {
        try {
            action.run();
            return true;
        } catch (RuntimeException ex) {
            if (errorHandler != null) {
                errorHandler.handle(parent, errorMessage, ex);
            } else {
                JOptionPane.showMessageDialog(parent, errorMessage + "\n\n" + ex.getMessage(), "SQL Logging", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
    }

    private void setToggleState(JToggleButton toggleButton, boolean selected) {
        isProgrammaticToggleChange = true;
        try {
            toggleButton.setSelected(selected);
        } finally {
            isProgrammaticToggleChange = false;
        }
        BusinessTheme.styleToggleButton(toggleButton, selected);
    }

    private void syncToggleStateFromProject() {
        if (toggleButton == null) {
            return;
        }

        String directory = dossierKoProjectField.getText();
        if (directory == null || directory.isBlank()) {
            setToggleState(toggleButton, false);
            return;
        }

        try {
            boolean enabled = spyService.isLoggerProxyEnabled(directory.trim());
            setToggleState(toggleButton, enabled);
        } catch (RuntimeException exception) {
            setToggleState(toggleButton, false);
        }
    }

    private String getValidatedDirectory() {
        String directory = dossierKoProjectField.getText();
        if (directory == null || directory.isBlank()) {
            throw new IllegalArgumentException("Каталог проекта не выбран.");
        }
        directory = directory.trim();
        Path directoryPath = Path.of(directory);
        if (Files.notExists(directoryPath) || !Files.isDirectory(directoryPath)) {
            throw new IllegalArgumentException("Указанный путь не является директорией: " + directory);
        }
        return directory;
    }
}
