package ru.cbr.koh.panes_storage.panels.permission_migration.excel;

import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser.FileReader;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.InformationPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.base_clases.ChangeLog;
import ru.cbr.koh.properties.ConfigManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.util.List;

public class ExcelInputPanel implements PaneInterface {

    private char rowSelector = 'i'; // символ для выбора строки значений (по умолчанию 'i')
    private int profileStartColumn = 11; // номер столбца, с которого начинаются профили (по умолчанию 11)

    private File file;

    public void ExcelInputPanel() {
        var profileStartColumnString = ConfigManager.getProperty("excel.profileStartColumn");
        if (profileStartColumnString != null) {
            profileStartColumn = Integer.parseInt(ConfigManager.getProperty("excel.profileStartColumn"));
        }
    }

    @Override
    public String getTitle() {
        return "Data from Excel";
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Панель для настроек Excel
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(2, 2, 10, 10));
        settingsPanel.setBorder(BorderFactory.createTitledBorder("Excel Settings"));
        settingsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        settingsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Добавление компонента для выбора символа строки
        JLabel rowSelectorLabel = new JLabel("Row Selector Symbol:");
        JTextField rowSelectorField = new JTextField(String.valueOf(rowSelector), 1);
        // Увеличить размер текущего шрифта на 4 пункта
        float newSize = rowSelectorField.getFont().getSize() + 4f;
        rowSelectorField.setFont(rowSelectorField.getFont().deriveFont(newSize));
        rowSelectorField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String text = rowSelectorField.getText();
                if (text != null && !text.isEmpty()) {
                    rowSelector = text.charAt(0);
                    // Сохраняем в конфигурации
                    ConfigManager.setProperty("excel.rowSelector", String.valueOf(rowSelector));
                }
            }
        });

        // Добавление компонента для выбора начального столбца профилей
        JLabel profileColumnLabel = new JLabel("Profile Start Column:");
        JSpinner profileColumnSpinner = new JSpinner(
                new SpinnerNumberModel(profileStartColumn, 1, 100, 1));
        profileColumnSpinner.addChangeListener(e -> {
            profileStartColumn = (Integer) profileColumnSpinner.getValue();
            // Сохраняем в конфигурации
            ConfigManager.setProperty("excel.profileStartColumn", String.valueOf(profileStartColumn));
        });

        settingsPanel.add(rowSelectorLabel);
        settingsPanel.add(rowSelectorField);
        settingsPanel.add(profileColumnLabel);
        settingsPanel.add(profileColumnSpinner);

        // Загрузка сохраненных настроек
        loadSettings(rowSelectorField, profileColumnSpinner);

        ImageIcon originalIcon = new ImageIcon("images.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JButton folderButton = new JButton("Select xlsx Permissions File", scaledIcon);  //
        folderButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        folderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        folderButton.setFocusPainted(false);

        folderButton.addActionListener(e -> {
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

        // Добавляем компоненты на основную панель
        jPanel.add(settingsPanel);
        jPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Отступ
        jPanel.add(folderButton);
        return jPanel;
    }

    /**
     * Загружает сохраненные настройки из конфигурации
     */
    private void loadSettings(JTextField rowSelectorField, JSpinner profileColumnSpinner) {
        // Загрузка символа строки
        String savedRowSelector = ConfigManager.getProperty("excel.rowSelector");
        if (savedRowSelector != null && !savedRowSelector.isEmpty()) {
            rowSelector = savedRowSelector.charAt(0);
            rowSelectorField.setText(String.valueOf(rowSelector));
        }

        // Загрузка номера столбца
        String savedProfileColumn = ConfigManager.getProperty("excel.profileStartColumn");
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
        FileReader reader = new FileReader(file, rowSelector, profileStartColumn);
        List<Permission> permissions = reader.read();
        var information = InformationPanel.getInformation();
        ChangeLog changeLog = new ChangeLog(information, permissions);
        changeLog.create();
    }

    private void saveCurrentDirectoryToProperty() {
        ConfigManager.setProperty("project.pathExcel", file.getParentFile().getAbsolutePath());
    }

    private void setCurrentDirectory(JFileChooser fileChooser) {
        var pathExcel = ConfigManager.getProperty("project.pathExcel");
        if (pathExcel == null || pathExcel.isEmpty()) {
            return;
        }
        fileChooser.setCurrentDirectory(new File(pathExcel));
    }
}
