package ru.cbr.koh.panes_storage.panels.permission_migration.permission;

import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.InformationPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.base_clases.ChangeLog;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.dialog_objects.PermissionDialogObject;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.PermissionType;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.TreeType;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.ProfilePanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.save_abac_profile_file.AbacProfileFileSaver;
import ru.cbr.koh.utils.JPanelUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PermissionPanel implements PaneInterface {

    private static final List<PermissionDialogObject> dataList = new ArrayList<>();

    private PermissionDialogObject lastPermission;

    @Override
    public String getTitle() {
        return "Permission";
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        JPanel panel = new JPanel();

        JButton openDialogButton = new JButton("Add Permission");
        openDialogButton.addActionListener(new OpenDialogActionListener(frame, panel));
        panel.add(openDialogButton);

        JButton createMigrationButton = new JButton("Create Migration");
        createMigrationButton.addActionListener(new CreateMigrationActionListener(frame, panel));
        panel.add(createMigrationButton);

        JPanelUtils.drawLine(panel);
        return panel;
    }

    private class CreateMigrationActionListener implements ActionListener {
        private final JFrame parentFrame;
        private final JPanel panel;

        public CreateMigrationActionListener(JFrame parentFrame, JPanel panel) {
            this.parentFrame = parentFrame;
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            List<Profile> profiles = ProfilePanel.getCheckedProfiles();
            List<Permission> permissions =
                    dataList.stream().map(it -> new Permission(it, profiles)).toList();

            var information = InformationPanel.getInformation();
            ChangeLog changeLog = new ChangeLog(information, permissions);
            changeLog.create();
        }
    }


    private class OpenDialogActionListener implements ActionListener {
        private final JFrame parentFrame;
        private final JPanel panel;

        public OpenDialogActionListener(JFrame parentFrame, JPanel panel) {
            this.parentFrame = parentFrame;
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog dialog = new JDialog(parentFrame, "Add new Permission");
            dialog.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(5, 5, 5, 5);
            int y = 0;

            gbc.gridx = 0;
            gbc.gridy = y++;
            dialog.add(new JLabel("Key:"), gbc);
            JTextField keyField = new JTextField(20);
            if (lastPermission != null && lastPermission.getKey() != null) {
                keyField.setText(lastPermission.getKey());
            }
            gbc.gridx = 1;
            dialog.add(keyField, gbc);

            gbc.gridx = 0;
            gbc.gridy = y++;
            dialog.add(new JLabel("Permission Type:"), gbc);
            JComboBox<PermissionType> comboBox = new JComboBox<>(PermissionType.values());
            if (lastPermission == null) {
                comboBox.setSelectedIndex(1);
            } else {
                comboBox.setSelectedIndex(lastPermission.getPermissionType().ordinal());
            }
            gbc.gridx = 1;
            dialog.add(comboBox, gbc);

            gbc.gridx = 0;
            gbc.gridy = y++;
            dialog.add(new JLabel("abacPermPresGroupAction:"), gbc);
            JTextField groupActionField = new JTextField(20);
            if (lastPermission != null && lastPermission.getGroupAction() != null) {
                groupActionField.setText(lastPermission.getGroupAction());
            }
            gbc.gridx = 1;
            dialog.add(groupActionField, gbc);

            gbc.gridx = 0;
            gbc.gridy = y++;
            dialog.add(new JLabel("abacPermPresUserAction:"), gbc);
            JTextField userActionField = new JTextField(20);
            if (lastPermission != null && lastPermission.getUserAction() != null) {
                userActionField.setText(lastPermission.getUserAction());
            }
            gbc.gridx = 1;
            dialog.add(userActionField, gbc);

            gbc.gridx = 0;
            gbc.gridy = y++;
            dialog.add(new JLabel("name:"), gbc);
            JTextField nameField = new JTextField(20);
            if (lastPermission != null && lastPermission.getName() != null) {
                nameField.setText(lastPermission.getName());
            }
            gbc.gridx = 1;
            dialog.add(nameField, gbc);

            gbc.gridx = 0;
            gbc.gridy = y++;
            dialog.add(new JLabel("description:"), gbc);
            JTextField descriptionField = new JTextField(20);
            if (lastPermission != null && lastPermission.getDescription() != null) {
                descriptionField.setText(lastPermission.getDescription());
            }
            gbc.gridx = 1;
            dialog.add(descriptionField, gbc);

            JCheckBox koCheckBox = new JCheckBox(TreeType.KO.getText());
            if (lastPermission != null && lastPermission.getTreeType() != null) {
                koCheckBox.setSelected(lastPermission.getTreeType().stream().anyMatch(it -> it.equals(TreeType.KO)));
            } else {
                koCheckBox.setSelected(true);
            }
            gbc.gridx = 0;
            gbc.gridy = y++;
            dialog.add(koCheckBox, gbc);

            JCheckBox gibrCheckBox = new JCheckBox(TreeType.GIBR.getText());
            if (lastPermission != null && lastPermission.getTreeType() != null) {
                gibrCheckBox.setSelected(lastPermission.getTreeType().stream().anyMatch(it -> it.equals(TreeType.GIBR)));
            } else {
                gibrCheckBox.setSelected(false);
            }
            gbc.gridx = 0;
            gbc.gridy = y++;
            dialog.add(gibrCheckBox, gbc);

            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(e1 -> {
                PermissionDialogObject obj = new PermissionDialogObject(
                        keyField.getText(),
                        (PermissionType) comboBox.getSelectedItem(),
                        groupActionField.getText(),
                        userActionField.getText(),
                        nameField.getText(),
                        descriptionField.getText(),
                        getTree(koCheckBox, gibrCheckBox)
                );
                lastPermission = obj;
                dataList.add(obj);
                drawAddedPermission(panel, obj);
                dialog.dispose();
            });
            gbc.gridx = 1;
            gbc.gridy = y++;
            dialog.add(saveButton, gbc);

            JButton canselButton = new JButton("Cansel");
            canselButton.addActionListener(e1 -> {
                dialog.dispose();
            });
            gbc.gridx = 2;
            dialog.add(canselButton, gbc);

            dialog.pack();
            dialog.setLocationRelativeTo(parentFrame);
            dialog.setVisible(true);
        }

        private void drawAddedPermission(JPanel panel, PermissionDialogObject addedPermission) {
            Permission permission = new Permission(addedPermission);

            JTextArea textArea = new JTextArea(10, 30);
            textArea.setText(permission.toString());
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            panel.add(textArea);

            JPanelUtils.drawLine(panel);

            panel.revalidate(); // Обновляем панель
            panel.repaint(); // Перерисовываем панель
        }
    }

    private List<TreeType> getTree(JCheckBox nogibrCheckBox, JCheckBox gibrCheckBox) {
        List<TreeType> treeTypes = new ArrayList<>();
        if (nogibrCheckBox.isSelected()) {
            treeTypes.add(TreeType.KO);
        }
        if (gibrCheckBox.isSelected()) {
            treeTypes.add(TreeType.GIBR);
        }
        if (treeTypes.isEmpty()) {
            treeTypes.add(TreeType.KO);
        }
        return treeTypes;
    }
}

