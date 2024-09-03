package ru.cbr.koh.panes_storage.panels.profile;

import ru.cbr.koh.panes_storage.PaneInterface;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class ProfilePanel implements PaneInterface {

    private static final List<JCheckBox> checkBoxList = new ArrayList<>();

    private static final String FILE_NAME = "selectedCheckboxes.txt";

    @Override
    public String getTitle() {
        return "Profiles";
    }

    @Override
    public JComponent createPanel() {

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));

        addCheckBoxes(checkBoxPanel, checkBoxList);

        fillCheckBoxes(checkBoxList);

        return checkBoxPanel;
    }

    private void fillCheckBoxes(List<JCheckBox> checkBoxList) {
        List<String> checkedList = getSavedCheckBoxes();
        if (checkedList.isEmpty()) {
            return;
        }
        checkedList.stream()
                .filter(Objects::nonNull)
                .forEach(line ->
                        checkBoxList.stream()
                                .filter(checkBox -> checkBox.getText().equals(line))
                                .forEach(checkBox -> checkBox.setSelected(true)));
    }

    private List<String> getSavedCheckBoxes() {
        Path path = Paths.get(FILE_NAME);
        if (Files.exists(path)) {
            try {
                return Files.readAllLines(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return Collections.emptyList();
    }

    private static void addCheckBoxes(JPanel checkBoxPanel, List<JCheckBox> checkBoxList) {
        for (Profile option : Profile.values()) {
            JCheckBox checkBox = new JCheckBox(option.getDisplayName());
            checkBoxPanel.add(checkBox);
            checkBoxList.add(checkBox);
        }
    }

    public static void saveCheckBoxesFile() {
        if (!checkBoxList.isEmpty()) {
            Set<String> checkedBoxesNames =
                    checkBoxList.stream()
                            .filter(Objects::nonNull)
                            .filter(AbstractButton::isSelected)
                            .map(AbstractButton::getText)
                            .collect(Collectors.toSet());
            try (FileWriter writer = new FileWriter(FILE_NAME)) {
                for (String name : checkedBoxesNames) {
                    writer.write(name + System.lineSeparator());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
