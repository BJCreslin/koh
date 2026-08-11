package ru.cbr.koh.panes_storage.panels.permission_migration.profile;

import ru.cbr.koh.app.error.ErrorHandler;
import ru.cbr.koh.app.error.SwingErrorHandler;
import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.ui.BusinessTheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class ProfilePanel implements PaneInterface {

    private final Map<Profile, JCheckBox> checkBoxByProfile = new EnumMap<>(Profile.class);
    private final ProfileSelectionStorage profileSelectionStorage;
    private final ErrorHandler errorHandler;

    public ProfilePanel() {
        this(new ProfileSelectionStorage(), new SwingErrorHandler());
    }

    public ProfilePanel(ProfileSelectionStorage profileSelectionStorage, ErrorHandler errorHandler) {
        this.profileSelectionStorage = profileSelectionStorage;
        this.errorHandler = errorHandler;
    }

    @Override
    public String getTitle() {
        return "Profiles";
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
        checkBoxPanel.setBorder(new EmptyBorder(8, 8, 8, 8));

        checkBoxByProfile.clear();
        addCheckBoxes(checkBoxPanel);
        fillCheckBoxesFromStorage();

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BusinessTheme.pagePadding());

        container.add(BusinessTheme.createPageTitle("Profiles"));
        container.add(Box.createVerticalStrut(8));
        container.add(createActionPanel(frame));
        container.add(Box.createVerticalStrut(8));

        JScrollPane scrollPane = new JScrollPane(checkBoxPanel);
        scrollPane.setBorder(BusinessTheme.cardBorder());
        container.add(scrollPane);

        return container;
    }

    @Override
    public void onClose() {
        saveCurrentSelection();
    }

    public List<Profile> getCheckedProfiles() {
        if (checkBoxByProfile.isEmpty()) {
            return Collections.emptyList();
        }
        return checkBoxByProfile.entrySet().stream()
                .filter(entry -> entry.getValue().isSelected())
                .map(Map.Entry::getKey)
                .toList();
    }

    private JPanel createActionPanel(JFrame frame) {
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        actions.setOpaque(false);

        JComboBox<ProfilePreset> presetComboBox = new JComboBox<>(ProfilePreset.values());
        JButton applyPresetButton = new JButton("Apply preset");
        BusinessTheme.styleSecondaryButton(applyPresetButton);
        applyPresetButton.addActionListener(e -> applyPreset((ProfilePreset) presetComboBox.getSelectedItem()));

        JButton importButton = new JButton("Import");
        BusinessTheme.styleSecondaryButton(importButton);
        importButton.addActionListener(e -> importSelection(frame));

        JButton exportButton = new JButton("Export");
        BusinessTheme.styleSecondaryButton(exportButton);
        exportButton.addActionListener(e -> exportSelection(frame));

        actions.add(new JLabel("Preset:"));
        actions.add(presetComboBox);
        actions.add(applyPresetButton);
        actions.add(importButton);
        actions.add(exportButton);

        return actions;
    }

    private void applyPreset(ProfilePreset preset) {
        if (preset == null) {
            return;
        }

        Set<Profile> profiles = preset.profiles();
        checkBoxByProfile.forEach((profile, checkBox) -> checkBox.setSelected(profiles.contains(profile)));
    }

    private void fillCheckBoxesFromStorage() {
        List<Profile> selectedProfiles = profileSelectionStorage.load().stream()
                .map(Profile::getByCodeOrDisplayName)
                .filter(Objects::nonNull)
                .toList();

        if (selectedProfiles.isEmpty()) {
            return;
        }
        Set<Profile> selected = new HashSet<>(selectedProfiles);
        checkBoxByProfile.forEach((profile, checkBox) -> checkBox.setSelected(selected.contains(profile)));
    }

    private void addCheckBoxes(JPanel checkBoxPanel) {
        for (Profile option : Profile.values()) {
            JCheckBox checkBox = new JCheckBox(option.getDisplayName());
            checkBoxPanel.add(checkBox);
            checkBoxByProfile.put(option, checkBox);
        }
    }

    private void saveCurrentSelection() {
        profileSelectionStorage.save(getCheckedProfiles());
    }

    private void exportSelection(Component parent) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Export selected profiles");
        chooser.setFileFilter(new FileNameExtensionFilter("Text files (*.txt)", "txt"));

        int result = chooser.showSaveDialog(parent);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        Path filePath = chooser.getSelectedFile().toPath();
        List<String> selectedProfiles = getCheckedProfiles().stream()
                .map(Profile::getCode)
                .toList();

        try {
            Files.write(filePath, selectedProfiles);
        } catch (IOException e) {
            errorHandler.handle(parent, "Не удалось экспортировать профили", e);
        }
    }

    private void importSelection(Component parent) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Import selected profiles");
        chooser.setFileFilter(new FileNameExtensionFilter("Text files (*.txt)", "txt"));

        int result = chooser.showOpenDialog(parent);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try {
            List<String> lines = Files.readAllLines(chooser.getSelectedFile().toPath());
            Set<Profile> selectedProfiles = lines.stream()
                    .map(Profile::getByCodeOrDisplayName)
                    .filter(Objects::nonNull)
                    .collect(java.util.stream.Collectors.toSet());
            checkBoxByProfile.forEach((profile, checkBox) -> checkBox.setSelected(selectedProfiles.contains(profile)));
        } catch (IOException e) {
            errorHandler.handle(parent, "Не удалось импортировать профили", e);
        }
    }
}
