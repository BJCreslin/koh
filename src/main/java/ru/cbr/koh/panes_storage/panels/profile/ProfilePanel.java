package ru.cbr.koh.panes_storage.panels.profile;

import ru.cbr.koh.panes_storage.PaneInterface;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ProfilePanel implements PaneInterface {

    @Override
    public String getTitle() {
        return "Profiles";
    }

    @Override
    public JComponent createPanel() {
        final List<JCheckBox> checkBoxList = new ArrayList<>();

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));

        for (Profile option : Profile.values()) {
            JCheckBox checkBox = new JCheckBox(option.getDisplayName());
            checkBoxPanel.add(checkBox);
            checkBoxList.add(checkBox);
        }
        return checkBoxPanel;
    }
}
