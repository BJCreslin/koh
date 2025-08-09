package ru.cbr.koh.panes_storage.panels.permission_migration.information;

import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.domain.Information;
import ru.cbr.koh.properties.ConfigurationService;
import ru.cbr.koh.exceptions.ConfigurationException;
import ru.cbr.koh.exceptions.SerializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.*;


public class InformationPanel implements PaneInterface {

    private static final Logger logger = LogManager.getLogger(InformationPanel.class);
    private static final String FILE_NAME = "information.txt";

    public static final int RIGHT_MARGIN = 40;
    public static final int LEFT_MARGIN = 10;
    public static final int DELIMITER_HEIGHT = 30;

    private final ConfigurationService properties;

    private static JTextField textField;

    private static JTextField authorField;

    private static JTextField storyNumberField;

    private static JTextField tabNameField;

    private static JCheckBox checkBox;

    private static JCheckBox excelInputCheckBox;

    private Information info;

    @Override
    public String getTitle() {
        return "Common information";
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);


        JLabel keyLabel = new JLabel();
        keyLabel.setText("Key prefix");

        textField = new JTextField();
        textField.setText(getKey());

        JLabel authorLabel = new JLabel();
        authorLabel.setText("Author");

        authorField = new JTextField();
        authorField.setText(getAuthor());


        JLabel storyNumberLabel = new JLabel();
        storyNumberLabel.setText("Story number");

        storyNumberField = new JTextField();
        storyNumberField.setText(getStoryNumber());

        JLabel tabNameLabel = new JLabel();
        tabNameLabel.setText("Story name");

        tabNameField = new JTextField();
        tabNameField.setText(getStoryName());


        excelInputCheckBox = new JCheckBox("Input data from excel");
        excelInputCheckBox.setSelected(getDefaultExcelInputCheckBox());

        checkBox = new JCheckBox("Save abac's politics to file");
        checkBox.setSelected(getCheckboxState());

        Dimension txtFieldSize = new Dimension(properties.getHorizontalSize() - RIGHT_MARGIN,
                textField.getPreferredSize().height);
        textField.setPreferredSize(txtFieldSize);
        authorField.setPreferredSize(txtFieldSize);
        storyNumberField.setPreferredSize(txtFieldSize);
        tabNameField.setPreferredSize(txtFieldSize);
        checkBox.setPreferredSize(txtFieldSize);
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
                                        .addComponent(checkBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                                .addComponent(tabNameLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(checkBox))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(excelInputCheckBox))
        );

        return panel;
    }

    private String getStoryName() {
        if (info == null) {
            return ConfigurationService.getProperty("story.name");
        }
        return info.storyText();
    }

    private String getStoryNumber() {
        if (info == null) {
            return ConfigurationService.getProperty("story.number");
        }
        return info.storyNumber();
    }

    private String getAuthor() {
        if (info == null) {
            return ConfigurationService.getProperty("story.author");
        }
        return info.author();
    }

    private String getKey() {
        if (info == null) {
            return ConfigurationService.getProperty("story.key");
        }
        return info.keyText();
    }

    private boolean getCheckboxState() {
        if (info == null) {
            return properties.getSaveAbacPolitics();
        }
        return info.shouldWriteAbakFile();
    }

    public static Information getInformation() {
        return new Information(
                textField.getText(),
                authorField.getText(),
                storyNumberField.getText(),
                tabNameField.getText(),
                checkBox.isSelected(),
                checkBox.isSelected(),
                excelInputCheckBox.isSelected());
    }

    public static void setInformation() throws SerializationException {
        Information information = getInformation();
        try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            out.writeObject(information);
            logger.info("Информация успешно сохранена в {}", FILE_NAME);

        } catch (IOException e) {
            logger.error("Ошибка сохранения информации в файл {}", FILE_NAME, e);
            throw new SerializationException("Не удалось сохранить информацию в файл: " + FILE_NAME, e);
        }
    }

    public InformationPanel() throws ConfigurationException, SerializationException {
        try {
            properties = ConfigurationService.getInstance();
        } catch (ConfigurationException e) {
            logger.error("Ошибка инициализации конфигурации", e);
            throw e;
        }
        
        info = null;
        try (FileInputStream fileIn = new FileInputStream(FILE_NAME);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            info = (Information) in.readObject();
            logger.debug("Информация успешно загружена из {}", FILE_NAME);
        } catch (FileNotFoundException e) {
            logger.info("Файл {} не найден, будут использованы значения по умолчанию", FILE_NAME);
            // Это нормальная ситуация при первом запуске
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Ошибка загрузки информации из файла {}", FILE_NAME, e);
            throw new SerializationException("Не удалось загрузить информацию из файла: " + FILE_NAME, e);
        }
    }

    public boolean getDefaultExcelInputCheckBox() {
        if (info == null) {
            return properties.getFromExcel();
        }
        return info.fromExcel();
    }

    public JCheckBox getExcelInputCheckBox() {
        return excelInputCheckBox;
    }
}
