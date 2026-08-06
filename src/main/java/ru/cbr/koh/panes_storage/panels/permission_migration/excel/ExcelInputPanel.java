package ru.cbr.koh.panes_storage.panels.permission_migration.excel;

import ru.cbr.koh.app.async.UiTaskRunner;
import ru.cbr.koh.app.service.ExcelReadResult;
import ru.cbr.koh.app.service.MigrationPreview;
import ru.cbr.koh.app.service.PermissionMigrationService;
import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.domain.Information;
import ru.cbr.koh.panes_storage.panels.permission_migration.preview.FilesPreviewDialog;
import ru.cbr.koh.panes_storage.panels.permission_migration.preview.KeyConflictDialog;
import ru.cbr.koh.properties.ApplicationProperties;
import ru.cbr.koh.ui.BusinessTheme;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.util.function.Supplier;

public class ExcelInputPanel implements PaneInterface {

    private static final char DEFAULT_ROW_SELECTOR = 'i';

    private final Supplier<Information> informationSupplier;
    private final ApplicationProperties properties;
    private final PermissionMigrationService migrationService;
    private final UiTaskRunner taskRunner;

    private char rowSelector = DEFAULT_ROW_SELECTOR;
    private int profileStartColumn = 11;

    private File file;

    public ExcelInputPanel(Supplier<Information> informationSupplier,
                           ApplicationProperties properties,
                           PermissionMigrationService migrationService,
                           UiTaskRunner taskRunner) {
        this.informationSupplier = informationSupplier;
        this.properties = properties;
        this.migrationService = migrationService;
        this.taskRunner = taskRunner;
    }

    @Override
    public String getTitle() {
        return "Data from Excel";
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BusinessTheme.pagePadding());

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(2, 2, 10, 10));
        settingsPanel.setBorder(BusinessTheme.sectionBorder("Excel settings"));
        settingsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        settingsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel rowSelectorLabel = new JLabel("Row Selector Symbol:");
        BusinessTheme.styleFormLabel(rowSelectorLabel);
        JTextField rowSelectorField = new JTextField(String.valueOf(rowSelector), 1);
        rowSelectorField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String text = rowSelectorField.getText();
                rowSelector = resolveRowSelector(text);
                rowSelectorField.setText(String.valueOf(rowSelector));
                properties.setExcelRowSelector(rowSelector);
            }
        });

        JLabel profileColumnLabel = new JLabel("Profile Start Column:");
        BusinessTheme.styleFormLabel(profileColumnLabel);
        JSpinner profileColumnSpinner = new JSpinner(
                new SpinnerNumberModel(profileStartColumn, 1, 200, 1));
        profileColumnSpinner.addChangeListener(e -> {
            profileStartColumn = (Integer) profileColumnSpinner.getValue();
            properties.setExcelProfileStartColumn(profileStartColumn);
        });

        settingsPanel.add(rowSelectorLabel);
        settingsPanel.add(rowSelectorField);
        settingsPanel.add(profileColumnLabel);
        settingsPanel.add(profileColumnSpinner);

        loadSettings(rowSelectorField, profileColumnSpinner);

        ImageIcon originalIcon = new ImageIcon("images.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JButton fileButton = new JButton("Select xlsx Permissions File", scaledIcon);
        fileButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        fileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        BusinessTheme.stylePrimaryButton(fileButton);

        fileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
            setCurrentDirectory(fileChooser);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                saveCurrentDirectoryToProperty();
                createChangelogMigration(frame, panel);
            }
        });

        panel.add(settingsPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(fileButton);
        return panel;
    }

    private void loadSettings(JTextField rowSelectorField, JSpinner profileColumnSpinner) {
        rowSelector = properties.getExcelRowSelector();
        rowSelectorField.setText(String.valueOf(rowSelector));

        profileStartColumn = properties.getExcelProfileStartColumn();
        profileColumnSpinner.setValue(profileStartColumn);
    }

    private void createChangelogMigration(JFrame frame, JPanel panel) {
        if (taskRunner == null) {
            Information information = informationSupplier.get();
            ExcelReadResult readResult = migrationService.readAndValidate(file, rowSelector, profileStartColumn, information);
            MigrationPreview preview = resolveConflictsAndRebuild(frame, readResult, information);
            if (preview == null) {
                return;
            }
            if (FilesPreviewDialog.show(frame, preview.files())) {
                migrationService.savePreview(preview);
            }
            return;
        }

        taskRunner.runWithProgressResult(
                frame,
                "Build Preview",
                "Читаем Excel и формируем предпросмотр...",
                () -> migrationService.readAndValidate(file, rowSelector, profileStartColumn, informationSupplier.get()),
                readResult -> {
                    MigrationPreview preview = resolveConflictsAndRebuild(frame, readResult, informationSupplier.get());
                    if (preview != null) {
                        confirmAndSavePreview(frame, panel, preview);
                    }
                },
                "Не удалось создать предпросмотр из Excel");
    }

    private MigrationPreview resolveConflictsAndRebuild(JFrame frame, ExcelReadResult readResult, Information information) {
        if (readResult.conflicts() != null && !readResult.conflicts().isEmpty()) {
            boolean resolved = KeyConflictDialog.show(frame, readResult.conflicts());
            if (!resolved) {
                return null;
            }
            return migrationService.buildPreviewFromPermissions(readResult.permissions(), information);
        }
        return readResult.preview();
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

    private void saveCurrentDirectoryToProperty() {
        if (file != null && file.getParentFile() != null) {
            properties.setPathExcel(file.getParentFile().getAbsolutePath());
        }
    }

    private void setCurrentDirectory(JFileChooser fileChooser) {
        String pathExcel = properties.getPathExcel();
        if (pathExcel == null || pathExcel.isBlank()) {
            return;
        }

        File directory = new File(pathExcel);
        if (directory.exists() && directory.isDirectory()) {
            fileChooser.setCurrentDirectory(directory);
        }
    }

    private char resolveRowSelector(String value) {
        if (value == null) {
            return DEFAULT_ROW_SELECTOR;
        }
        String normalized = value.trim();
        if (normalized.length() != 1) {
            return DEFAULT_ROW_SELECTOR;
        }
        return normalized.charAt(0);
    }
}
