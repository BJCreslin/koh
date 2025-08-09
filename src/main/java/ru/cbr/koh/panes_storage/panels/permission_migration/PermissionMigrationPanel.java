package ru.cbr.koh.panes_storage.panels.permission_migration;

import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.panes_storage.panels.permission_migration.excel.ExcelInputPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.InformationPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.PermissionPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.ProfilePanel;
import ru.cbr.koh.properties.ConfigurationService;

import javax.swing.*;

public class PermissionMigrationPanel implements PaneInterface {

    private ProfilePanel profilePanel;

    @Override
    public String getTitle() {
        return "permissions";
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        JTabbedPane nestedTabbedPane = new JTabbedPane();

        InformationPanel informationPanel = new InformationPanel();
        nestedTabbedPane.addTab(informationPanel.getTitle(), informationPanel.createPanel(frame));

        profilePanel = new ProfilePanel();
        nestedTabbedPane.addTab(profilePanel.getTitle(), profilePanel.createPanel(frame));

        PermissionPanel permissionPanel = new PermissionPanel();
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
    }

    public ProfilePanel getProfilePanel() {
        return profilePanel;
    }

    private void setDefaultPanels(JTabbedPane nestedTabbedPane) {
        ConfigurationService propertiesService = ConfigurationService.getInstance();
        boolean enabled = propertiesService.getFromExcel();
        nestedTabbedPane.setEnabledAt(1, !enabled);
        nestedTabbedPane.setEnabledAt(2, !enabled);
        nestedTabbedPane.setEnabledAt(3, enabled);
    }
}
