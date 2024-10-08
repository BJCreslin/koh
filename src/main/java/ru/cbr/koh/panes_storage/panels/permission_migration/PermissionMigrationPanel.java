package ru.cbr.koh.panes_storage.panels.permission_migration;

import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.InformationPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.PermissionPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.ProfilePanel;

import javax.swing.*;

public class PermissionMigrationPanel implements PaneInterface {

    @Override
    public String getTitle() {
        return "permissions";
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        JTabbedPane nestedTabbedPane = new JTabbedPane();

        InformationPanel informationPanel = new InformationPanel();
        nestedTabbedPane.addTab(informationPanel.getTitle(), informationPanel.createPanel(frame));

        ProfilePanel profilePanel = new ProfilePanel();
        nestedTabbedPane.addTab(profilePanel.getTitle(), profilePanel.createPanel(frame));

        PermissionPanel permissionPanel = new PermissionPanel();
        nestedTabbedPane.addTab(permissionPanel.getTitle(), permissionPanel.createPanel(frame));

        return nestedTabbedPane;
    }

}
