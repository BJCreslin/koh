package ru.cbr.koh.panes_storage.panels.permission_migration;

import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.main_window.SaveablePanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.excel.ExcelInputPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.InformationPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.PermissionPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.ProfilePanel;
import ru.cbr.koh.properties.ConfigurationService;
import ru.cbr.koh.exceptions.ConfigurationException;
import ru.cbr.koh.exceptions.SerializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public class PermissionMigrationPanel implements PaneInterface, SaveablePanel {

    private static final Logger logger = LogManager.getLogger(PermissionMigrationPanel.class);
    private ProfilePanel profilePanel;
    private InformationPanel informationPanel;

    @Override
    public String getTitle() {
        return "permissions";
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        JTabbedPane nestedTabbedPane = new JTabbedPane();

        try {
            informationPanel = new InformationPanel();
            nestedTabbedPane.addTab(informationPanel.getTitle(), informationPanel.createPanel(frame));

            profilePanel = new ProfilePanel();
            nestedTabbedPane.addTab(profilePanel.getTitle(), profilePanel.createPanel(frame));

            PermissionPanel permissionPanel = new PermissionPanel(profilePanel, informationPanel);
            nestedTabbedPane.addTab(permissionPanel.getTitle(), permissionPanel.createPanel(frame));

            ExcelInputPanel excelInputPanel = new ExcelInputPanel();
            nestedTabbedPane.addTab(excelInputPanel.getTitle(), excelInputPanel.createPanel(frame));

            setDefaultPanels(nestedTabbedPane);

            informationPanel.getExcelInputCheckBox().addItemListener(
                    e -> {
                        boolean enabled = informationPanel.getExcelInputCheckBox().isSelected();
                        nestedTabbedPane.setEnabledAt(1, !enabled);
                        nestedTabbedPane.setEnabledAt(2, !enabled);
                        nestedTabbedPane.setEnabledAt(3, enabled);
                    }
            );

            return nestedTabbedPane;
        } catch (ConfigurationException | SerializationException e) {
            logger.error("Ошибка при создании панели разрешений", e);
            JLabel errorLabel = new JLabel("Ошибка инициализации: " + e.getMessage());
            errorLabel.setForeground(java.awt.Color.RED);
            JPanel errorPanel = new JPanel();
            errorPanel.add(errorLabel);
            return errorPanel;
        }
    }

    public ProfilePanel getProfilePanel() {
        return profilePanel;
    }
    
    public InformationPanel getInformationPanel() {
        return informationPanel;
    }
    
    @Override
    public void saveData() throws SerializationException {
        if (profilePanel != null) {
            profilePanel.saveData();
        }
        if (informationPanel != null) {
            informationPanel.saveData();
        }
    }

    private void setDefaultPanels(JTabbedPane nestedTabbedPane) {
        try {
            ConfigurationService propertiesService = ConfigurationService.getInstance();
            boolean enabled = propertiesService.getFromExcel();
            nestedTabbedPane.setEnabledAt(1, !enabled);
            nestedTabbedPane.setEnabledAt(2, !enabled);
            nestedTabbedPane.setEnabledAt(3, enabled);
        } catch (ConfigurationException e) {
            logger.error("Ошибка при получении конфигурации для установки панелей по умолчанию", e);
            // Устанавливаем значения по умолчанию
            nestedTabbedPane.setEnabledAt(1, true);
            nestedTabbedPane.setEnabledAt(2, true);
            nestedTabbedPane.setEnabledAt(3, false);
        }
    }
}
