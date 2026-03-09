package ru.cbr.koh.panes_storage.panels.permission_migration.permission;

import ru.cbr.koh.app.async.UiTaskRunner;
import ru.cbr.koh.app.error.ErrorHandler;
import ru.cbr.koh.app.service.MigrationPreview;
import ru.cbr.koh.app.service.PermissionMigrationService;
import ru.cbr.koh.app.validation.MigrationValidator;
import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.domain.Information;
import ru.cbr.koh.panes_storage.panels.permission_migration.preview.FilesPreviewDialog;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.dialog_objects.PermissionDialogObject;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.PermissionType;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.TreeType;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;
import ru.cbr.koh.ui.BusinessTheme;
import ru.cbr.koh.utils.JPanelUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PermissionPanel implements PaneInterface {

    private final List<PermissionDialogObject> dataList = new ArrayList<>();

    private final Supplier<Information> informationSupplier;
    private final Supplier<List<Profile>> profilesSupplier;
    private final PermissionMigrationService migrationService;
    private final UiTaskRunner taskRunner;
    private final ErrorHandler errorHandler;

    private PermissionDialogObject lastPermission;

    public PermissionPanel(Supplier<Information> informationSupplier,
                           Supplier<List<Profile>> profilesSupplier,
                           PermissionMigrationService migrationService,
                           UiTaskRunner taskRunner,
                           ErrorHandler errorHandler) {
        this.informationSupplier = informationSupplier;
        this.profilesSupplier = profilesSupplier;
        this.migrationService = migrationService;
        this.taskRunner = taskRunner;
        this.errorHandler = errorHandler;
    }

    @Override
    public String getTitle() {
        return "Permission";
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BusinessTheme.pagePadding());

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));

        JButton openDialogButton = new JButton("Add Permission");
        BusinessTheme.styleSecondaryButton(openDialogButton);
        openDialogButton.addActionListener(new OpenDialogActionListener(frame, panel));
        actionsPanel.add(openDialogButton);

        JButton createMigrationButton = new JButton("Create Migration");
        BusinessTheme.stylePrimaryButton(createMigrationButton);
        createMigrationButton.addActionListener(new CreateMigrationActionListener(frame, panel));
        actionsPanel.add(createMigrationButton);

        panel.add(actionsPanel);
        panel.add(Box.createVerticalStrut(10));
        JPanelUtils.drawLine(panel);
        return panel;
    }

    private class CreateMigrationActionListener implements ActionListener {

        private final JFrame frame;
        private final JPanel panel;

        private CreateMigrationActionListener(JFrame frame, JPanel panel) {
            this.frame = frame;
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (dataList.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Сначала добавьте хотя бы один Permission", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }

            taskRunner.runWithProgressResult(
                    frame,
                    "Build Preview",
                    "Формируем предпросмотр файлов...",
                    () -> migrationService.previewFromDialogObjects(
                            List.copyOf(dataList),
                            profilesSupplier.get(),
                            informationSupplier.get()),
                    preview -> confirmAndSavePreview(frame, panel, preview),
                    "Не удалось сформировать предпросмотр migration");
        }

        private void confirmAndSavePreview(JFrame frame, JPanel panel, MigrationPreview preview) {
            boolean shouldSave = FilesPreviewDialog.show(frame, preview.files());
            if (!shouldSave) {
                return;
            }
            taskRunner.runWithProgress(
                    frame,
                    "Save Files",
                    "Сохраняем файлы...",
                    () -> migrationService.savePreview(preview),
                    () -> JOptionPane.showMessageDialog(panel, "Файлы успешно сохранены", "Success", JOptionPane.INFORMATION_MESSAGE),
                    "Не удалось сохранить файлы");
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
            JDialog dialog = new JDialog(parentFrame, "Add new Permission", true);
            dialog.setLayout(new GridBagLayout());
            dialog.getContentPane().setBackground(Color.WHITE);
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
            dialog.add(new JLabel("Name:"), gbc);
            JTextField nameField = new JTextField(20);
            if (lastPermission != null && lastPermission.getName() != null) {
                nameField.setText(lastPermission.getName());
            }
            gbc.gridx = 1;
            dialog.add(nameField, gbc);

            gbc.gridx = 0;
            gbc.gridy = y++;
            dialog.add(new JLabel("Description:"), gbc);
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
            BusinessTheme.stylePrimaryButton(saveButton);
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

                try {
                    MigrationValidator.validatePermissionForm(obj);
                } catch (RuntimeException exception) {
                    errorHandler.handle(dialog, "Некорректные данные permission", exception);
                    return;
                }

                lastPermission = obj;
                dataList.add(obj);
                drawAddedPermission(panel, obj);
                dialog.dispose();
            });
            gbc.gridx = 1;
            gbc.gridy = y++;
            dialog.add(saveButton, gbc);

            JButton cancelButton = new JButton("Cancel");
            BusinessTheme.styleSecondaryButton(cancelButton);
            cancelButton.addActionListener(e1 -> dialog.dispose());
            gbc.gridx = 2;
            dialog.add(cancelButton, gbc);

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
            panel.add(Box.createVerticalStrut(10));
            panel.add(new JScrollPane(textArea));

            JPanelUtils.drawLine(panel);

            panel.revalidate();
            panel.repaint();
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
