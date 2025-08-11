package ru.cbr.koh.panes_storage.panels.permission_migration.excel;

import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser.FileReader;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.InformationPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.base_clases.ChangeLog;
import ru.cbr.koh.properties.ConfigurationService;
import ru.cbr.koh.exceptions.ConfigurationException;
import ru.cbr.koh.exceptions.ExcelParsingException;
import ru.cbr.koh.utils.ModernTheme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.util.List;

public class ExcelInputPanel implements PaneInterface {

    private static final Logger logger = LogManager.getLogger(ExcelInputPanel.class);
    private char rowSelector = 'i'; // символ для выбора строки значений (по умолчанию 'i')
    private int profileStartColumn = 11; // номер столбца, с которого начинаются профили (по умолчанию 11)

    private File file;
    private final InformationPanel informationPanel;

    public ExcelInputPanel(InformationPanel informationPanel) {
        this.informationPanel = informationPanel;
        var profileStartColumnString = ConfigurationService.getProperty("excel.profileStartColumn");
        if (profileStartColumnString != null) {
            profileStartColumn = Integer.parseInt(profileStartColumnString);
        }
    }

    @Override
    public String getTitle() {
        return "📊 Excel Input";
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(
            ModernTheme.PADDING_LARGE, 
            ModernTheme.PADDING_LARGE, 
            ModernTheme.PADDING_LARGE, 
            ModernTheme.PADDING_LARGE
        ));

        JPanel settingsCard = ModernTheme.createCardWithTitle("⚙️ Настройки Excel");
        settingsCard.setLayout(new BorderLayout());
        
        JPanel settingsContent = new JPanel(new GridBagLayout());
        settingsContent.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(ModernTheme.PADDING_SMALL, ModernTheme.PADDING_SMALL, 
                              ModernTheme.PADDING_SMALL, ModernTheme.PADDING_SMALL);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel rowSelectorLabel = new JLabel("Символ выбора строки:");
        rowSelectorLabel.setFont(ModernTheme.FONT_BOLD);
        rowSelectorLabel.setForeground(ModernTheme.TEXT_PRIMARY);
        
        JTextField rowSelectorField = ModernTheme.createTextField("Символ строки");
        rowSelectorField.setText(String.valueOf(rowSelector));
        rowSelectorField.setPreferredSize(new Dimension(80, ModernTheme.COMPONENT_HEIGHT));
        rowSelectorField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String text = rowSelectorField.getText();
                if (text != null && !text.isEmpty()) {
                    rowSelector = text.charAt(0);
                    try {
                        ConfigurationService.setPropertyStatic("excel.rowSelector", String.valueOf(rowSelector));
                    } catch (ConfigurationException ex) {
                        logger.warn("Не удалось сохранить настройку excel.rowSelector", ex);
                    }
                }
            }
        });

        JLabel profileColumnLabel = new JLabel("Начальный столбец профилей:");
        profileColumnLabel.setFont(ModernTheme.FONT_BOLD);
        profileColumnLabel.setForeground(ModernTheme.TEXT_PRIMARY);
        
        JSpinner profileColumnSpinner = new JSpinner(
                new SpinnerNumberModel(profileStartColumn, 1, 100, 1));
        ModernTheme.styleSpinner(profileColumnSpinner);
        profileColumnSpinner.setPreferredSize(new Dimension(120, ModernTheme.COMPONENT_HEIGHT));
        profileColumnSpinner.addChangeListener(e -> {
            profileStartColumn = (Integer) profileColumnSpinner.getValue();
            try {
                ConfigurationService.setPropertyStatic("excel.profileStartColumn", String.valueOf(profileStartColumn));
            } catch (ConfigurationException ex) {
                logger.warn("Не удалось сохранить настройку excel.profileStartColumn", ex);
            }
        });

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        settingsContent.add(rowSelectorLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        settingsContent.add(rowSelectorField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        settingsContent.add(profileColumnLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        settingsContent.add(profileColumnSpinner, gbc);

        settingsCard.add(settingsContent, BorderLayout.CENTER);
        
        loadSettings(rowSelectorField, profileColumnSpinner);

        JButton fileButton = createModernFileButton(frame);

        JPanel infoPanel = createInfoPanel();

        mainPanel.add(settingsCard);
        mainPanel.add(Box.createRigidArea(new Dimension(0, ModernTheme.PADDING_LARGE)));
        mainPanel.add(fileButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, ModernTheme.PADDING_MEDIUM)));
        mainPanel.add(infoPanel);
        mainPanel.add(Box.createVerticalGlue());
        
        return mainPanel;
    }

    /**
     * Загружает сохраненные настройки из конфигурации
     */
    private void loadSettings(JTextField rowSelectorField, JSpinner profileColumnSpinner) {
        // Загрузка символа строки
        String savedRowSelector = ConfigurationService.getProperty("excel.rowSelector");
        if (savedRowSelector != null && !savedRowSelector.isEmpty()) {
            rowSelector = savedRowSelector.charAt(0);
            rowSelectorField.setText(String.valueOf(rowSelector));
        }

        // Загрузка номера столбца
        String savedProfileColumn = ConfigurationService.getProperty("excel.profileStartColumn");
        if (savedProfileColumn != null && !savedProfileColumn.isEmpty()) {
            try {
                profileStartColumn = Integer.parseInt(savedProfileColumn);
                profileColumnSpinner.setValue(profileStartColumn);
            } catch (NumberFormatException e) {
                // Если значение не является числом, используем значение по умолчанию
            }
        }
    }

    private void createChangelogMigration() {
        try {
            FileReader reader = new FileReader(file, rowSelector, profileStartColumn);
            List<Permission> permissions = reader.read();
            var information = informationPanel.getInformation();
            ChangeLog changeLog = new ChangeLog(information, permissions);
            changeLog.create();
            logger.info("Changelog успешно создан из файла: {}", file.getName());
        } catch (ExcelParsingException e) {
            logger.error("Ошибка при обработке Excel файла: {}", file.getName(), e);
            JOptionPane.showMessageDialog(null, 
                "Ошибка при обработке Excel файла:\n" + e.getMessage(), 
                "Ошибка парсинга Excel", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            logger.error("Неожиданная ошибка при создании changelog", e);
            JOptionPane.showMessageDialog(null, 
                "Неожиданная ошибка при создании changelog:\n" + e.getMessage(), 
                "Ошибка", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveCurrentDirectoryToProperty() {
        try {
            ConfigurationService.setPropertyStatic("project.pathExcel", file.getParentFile().getAbsolutePath());
        } catch (ConfigurationException e) {
            logger.warn("Не удалось сохранить путь к файлу в конфигурацию", e);
        }
    }

    private void setCurrentDirectory(JFileChooser fileChooser) {
        var pathExcel = ConfigurationService.getProperty("project.pathExcel");
        if (pathExcel == null || pathExcel.isEmpty()) {
            return;
        }
        fileChooser.setCurrentDirectory(new File(pathExcel));
    }
    
    /**
     * Создает современную кнопку для выбора файла
     */
    private JButton createModernFileButton(JFrame frame) {
        ImageIcon icon = null;
        try {
            ImageIcon originalIcon = new ImageIcon("images.png");
            Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImage);
        } catch (Exception e) {
            logger.debug("Не удалось загрузить иконку для кнопки", e);
        }
        
        JButton fileButton;
        if (icon != null) {
            fileButton = ModernTheme.createIconButton("📄 Выбрать Excel файл с разрешениями", icon);
        } else {
            fileButton = ModernTheme.createPrimaryButton("📄 Выбрать Excel файл с разрешениями");
        }
        
        fileButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, ModernTheme.BUTTON_HEIGHT));
        fileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        fileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
            setCurrentDirectory(fileChooser);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                createChangelogMigration();
                saveCurrentDirectoryToProperty();
            }
        });
        
        return fileButton;
    }
    
    /**
     * Создает информационную панель с инструкциями
     */
    private JPanel createInfoPanel() {
        JPanel infoCard = ModernTheme.createCardWithTitle("ℹ️ Информация");
        infoCard.setLayout(new BorderLayout());
        
        JTextArea infoText = new JTextArea();
        infoText.setText(
            "Инструкции по использованию:\n\n" +
            "1. Настройте символ выбора строки (по умолчанию 'i')\n" +
            "2. Укажите начальный столбец профилей (по умолчанию 11)\n" +
            "3. Выберите Excel файл с разрешениями (.xlsx)\n" +
            "4. Система автоматически создаст changelog миграции\n\n" +
            "Примечание: Убедитесь, что Excel файл содержит правильную структуру данных " +
            "с профилями, начинающимися с указанного столбца."
        );
        
        infoText.setFont(ModernTheme.FONT_REGULAR);
        infoText.setForeground(ModernTheme.TEXT_SECONDARY);
        infoText.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        infoText.setEditable(false);
        infoText.setWrapStyleWord(true);
        infoText.setLineWrap(true);
        infoText.setBorder(BorderFactory.createEmptyBorder(
            ModernTheme.PADDING_SMALL, 0, 0, 0
        ));
        
        infoCard.add(infoText, BorderLayout.CENTER);
        
        // Ограничиваем высоту информационной панели
        infoCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        
        return infoCard;
    }
}
