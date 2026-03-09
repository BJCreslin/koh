package ru.cbr.koh.panes_storage.panels.permission_migration.information;

import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.domain.Information;
import ru.cbr.koh.properties.ApplicationProperties;
import ru.cbr.koh.properties.PropertiesService;
import ru.cbr.koh.ui.BusinessTheme;

import javax.swing.*;
import java.awt.*;

public class InformationPanel implements PaneInterface {

    public static final int RIGHT_MARGIN = 40;
    public static final int LEFT_MARGIN = 10;
    public static final int DELIMITER_HEIGHT = 30;

    private final ApplicationProperties properties;
    private final InformationStorage informationStorage;
    private final Information savedInformation;

    private JTextField textField;
    private JTextField authorField;
    private JTextField storyNumberField;
    private JTextField tabNameField;
    private JCheckBox saveAbacPoliciesCheckBox;
    private JCheckBox saveAbacAttributeCodeCheckBox;
    private JCheckBox excelInputCheckBox;

    public InformationPanel() {
        this(PropertiesService.getInstance(), new InformationStorage());
    }

    public InformationPanel(ApplicationProperties properties, InformationStorage informationStorage) {
        this.properties = properties;
        this.informationStorage = informationStorage;
        this.savedInformation = informationStorage.load().orElse(null);
    }

    @Override
    public String getTitle() {
        return "Common information";
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setBorder(BusinessTheme.pagePadding());
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel keyLabel = new JLabel("Key prefix");
        BusinessTheme.styleFormLabel(keyLabel);

        textField = new JTextField(getKey());

        JLabel authorLabel = new JLabel("Author");
        BusinessTheme.styleFormLabel(authorLabel);

        authorField = new JTextField(getAuthor());

        JLabel storyNumberLabel = new JLabel("Story number");
        BusinessTheme.styleFormLabel(storyNumberLabel);

        storyNumberField = new JTextField(getStoryNumber());

        JLabel tabNameLabel = new JLabel("Story name");
        BusinessTheme.styleFormLabel(tabNameLabel);

        tabNameField = new JTextField(getStoryName());

        excelInputCheckBox = new JCheckBox("Input data from excel");
        excelInputCheckBox.setSelected(getDefaultExcelInputCheckBox());

        saveAbacPoliciesCheckBox = new JCheckBox("Save abac's politics to file");
        saveAbacPoliciesCheckBox.setSelected(getSaveAbacPoliciesCheckboxState());

        saveAbacAttributeCodeCheckBox = new JCheckBox("Save ABAC attribute code to file");
        saveAbacAttributeCodeCheckBox.setSelected(getAbacAttributeCodeCheckboxState());

        Dimension txtFieldSize = new Dimension(
                properties.getHorizontalSize() - RIGHT_MARGIN,
                textField.getPreferredSize().height);

        textField.setPreferredSize(txtFieldSize);
        authorField.setPreferredSize(txtFieldSize);
        storyNumberField.setPreferredSize(txtFieldSize);
        tabNameField.setPreferredSize(txtFieldSize);
        saveAbacPoliciesCheckBox.setPreferredSize(txtFieldSize);
        saveAbacAttributeCodeCheckBox.setPreferredSize(txtFieldSize);
        excelInputCheckBox.setPreferredSize(txtFieldSize);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(LEFT_MARGIN)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(textField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(authorField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(storyNumberField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tabNameField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(saveAbacPoliciesCheckBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(saveAbacAttributeCodeCheckBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(excelInputCheckBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(10))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, Short.MAX_VALUE)
                                .addComponent(keyLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, Short.MAX_VALUE)
                                .addComponent(authorLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, Short.MAX_VALUE)
                                .addComponent(storyNumberLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, Short.MAX_VALUE)
                                .addComponent(tabNameLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(keyLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(textField))
                        .addGap(DELIMITER_HEIGHT)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(authorLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(authorField))
                        .addGap(DELIMITER_HEIGHT)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(storyNumberLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(storyNumberField))
                        .addGap(DELIMITER_HEIGHT)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(tabNameLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(tabNameField))
                        .addGap(DELIMITER_HEIGHT)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(saveAbacPoliciesCheckBox))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(saveAbacAttributeCodeCheckBox))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(excelInputCheckBox))
        );

        return panel;
    }

    @Override
    public void onClose() {
        if (textField == null) {
            return;
        }
        informationStorage.save(getInformation());
    }

    public Information getInformation() {
        if (textField == null) {
            return new Information(
                    getKey(),
                    getAuthor(),
                    getStoryNumber(),
                    getStoryName(),
                    getSaveAbacPoliciesCheckboxState(),
                    getAbacAttributeCodeCheckboxState(),
                    getDefaultExcelInputCheckBox());
        }
        return new Information(
                textField.getText(),
                authorField.getText(),
                storyNumberField.getText(),
                tabNameField.getText(),
                saveAbacPoliciesCheckBox.isSelected(),
                saveAbacAttributeCodeCheckBox.isSelected(),
                excelInputCheckBox.isSelected());
    }

    public JCheckBox getExcelInputCheckBox() {
        return excelInputCheckBox;
    }

    private String getStoryName() {
        if (savedInformation == null) {
            return properties.getStoryName();
        }
        return savedInformation.storyText();
    }

    private String getStoryNumber() {
        if (savedInformation == null) {
            return properties.getStoryNumber();
        }
        return savedInformation.storyNumber();
    }

    private String getAuthor() {
        if (savedInformation == null) {
            return properties.getAuthor();
        }
        return savedInformation.author();
    }

    private String getKey() {
        if (savedInformation == null) {
            return properties.getStoryKey();
        }
        return savedInformation.keyText();
    }

    private boolean getSaveAbacPoliciesCheckboxState() {
        if (savedInformation == null) {
            return properties.getSaveAbacPolitics();
        }
        return savedInformation.shouldWriteAbakFile();
    }

    private boolean getAbacAttributeCodeCheckboxState() {
        if (savedInformation == null) {
            return properties.getSaveAbacAttributeCode();
        }
        return savedInformation.shouldWriteAbacAttributeCode();
    }

    private boolean getDefaultExcelInputCheckBox() {
        if (savedInformation == null) {
            return properties.getFromExcel();
        }
        return savedInformation.fromExcel();
    }
}
