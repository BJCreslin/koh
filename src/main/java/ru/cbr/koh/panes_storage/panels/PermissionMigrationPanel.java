package ru.cbr.koh.panes_storage.panes;

import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.panes_storage.panes.profile.ProfilePanel;

import javax.swing.*;

public class PermissionPane implements PaneInterface {

    @Override
    public String getTitle() {
        return "permissions";
    }

    @Override
    public JComponent createPanel() {
        JTabbedPane nestedTabbedPane = new JTabbedPane();
        ProfilePanel profilePanel = new ProfilePanel();
        nestedTabbedPane.addTab(profilePanel.getTitle(), profilePanel.createPanel());
        nestedTabbedPane.addTab("Nested Tab 2", createPanel("Content in Nested Tab 2"));
        return nestedTabbedPane;
    }

    private JPanel createPanel(String content) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(content));
        return panel;
    }
}
