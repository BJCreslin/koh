package ru.cbr.koh.panes_storage.panels.permission_migration.information;

import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.main_window.SaveablePanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.domain.Information;
import ru.cbr.koh.properties.ConfigurationService;
import ru.cbr.koh.exceptions.ConfigurationException;
import ru.cbr.koh.exceptions.SerializationException;
import ru.cbr.koh.utils.ModernTheme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.*;


public class InformationPanel implements PaneInterface, SaveablePanel {

    private static final Logger logger = LogManager.getLogger(InformationPanel.class);
    private static final String FILE_NAME = "information.txt";

    public static final int RIGHT_MARGIN = 40;
    public static final int LEFT_MARGIN = 10;
    public static final int DELIMITER_HEIGHT = 30;

    private final ConfigurationService properties;

    private JTextField textField;
    private JTextField authorField;
    private JTextField storyNumberField;
    private JTextField tabNameField;
    private JCheckBox checkBox;
    private JCheckBox excelInputCheckBox;

    private Information info;

    @Override
    public String getTitle() {
        return "📋 Общая информация";
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(
            ModernTheme.PADDING_LARGE, 
            ModernTheme.PADDING_LARGE, 
            ModernTheme.PADDING_LARGE, 
            ModernTheme.PADDING_LARGE
        ));

        // Создаем карточку с основной информацией
        JPanel infoCard = createMainInfoCard();
        
        // Создаем карточку с настройками
        JPanel settingsCard = createSettingsCard();
        
        // Создаем карточку режима ввода данных
        JPanel inputModeCard = createInputModeCard();

        // Добавляем все карточки на главную панель
        mainPanel.add(infoCard);
        mainPanel.add(Box.createRigidArea(new Dimension(0, ModernTheme.PADDING_LARGE)));
        mainPanel.add(settingsCard);
        mainPanel.add(Box.createRigidArea(new Dimension(0, ModernTheme.PADDING_LARGE)));
        mainPanel.add(inputModeCard);
        mainPanel.add(Box.createVerticalGlue()); // Заполнитель для выравнивания по верху

        return mainPanel;
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

    public Information getInformation() {
        return new Information(
                textField.getText(),
                authorField.getText(),
                storyNumberField.getText(),
                tabNameField.getText(),
                checkBox.isSelected(),
                checkBox.isSelected(),
                excelInputCheckBox.isSelected());
    }

    @Override
    public void saveData() throws SerializationException {
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
    
    /**
     * @deprecated Используйте метод saveData() для сохранения данных панели
     */
    @Deprecated
    public static void setInformation() throws SerializationException {
        // Этот метод оставлен для обратной совместимости, но не должен использоваться
        throw new UnsupportedOperationException("Используйте инстансный метод saveData() вместо статического setInformation()");
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
    
    /**
     * Создает карточку с основной информацией
     */
    private JPanel createMainInfoCard() {
        JPanel card = ModernTheme.createCardWithTitle("📝 Основная информация");
        card.setLayout(new BorderLayout());
        
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(ModernTheme.PADDING_SMALL, ModernTheme.PADDING_SMALL, 
                              ModernTheme.PADDING_SMALL, ModernTheme.PADDING_SMALL);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Префикс ключа
        JLabel keyLabel = new JLabel("Префикс ключа:");
        keyLabel.setFont(ModernTheme.FONT_BOLD);
        keyLabel.setForeground(ModernTheme.TEXT_PRIMARY);
        
        textField = ModernTheme.createTextField("Введите префикс ключа");
        textField.setText(getKey());
        
        // Автор
        JLabel authorLabel = new JLabel("Автор:");
        authorLabel.setFont(ModernTheme.FONT_BOLD);
        authorLabel.setForeground(ModernTheme.TEXT_PRIMARY);
        
        authorField = ModernTheme.createTextField("Введите имя автора");
        authorField.setText(getAuthor());

        // Размещение компонентов
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        content.add(keyLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        content.add(textField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        content.add(authorLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        content.add(authorField, gbc);

        card.add(content, BorderLayout.CENTER);
        return card;
    }
    
    /**
     * Создает карточку с настройками истории
     */
    private JPanel createSettingsCard() {
        JPanel card = ModernTheme.createCardWithTitle("🎯 Настройки истории");
        card.setLayout(new BorderLayout());
        
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(ModernTheme.PADDING_SMALL, ModernTheme.PADDING_SMALL, 
                              ModernTheme.PADDING_SMALL, ModernTheme.PADDING_SMALL);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Номер истории
        JLabel storyNumberLabel = new JLabel("Номер истории:");
        storyNumberLabel.setFont(ModernTheme.FONT_BOLD);
        storyNumberLabel.setForeground(ModernTheme.TEXT_PRIMARY);
        
        storyNumberField = ModernTheme.createTextField("Введите номер истории");
        storyNumberField.setText(getStoryNumber());

        // Название истории
        JLabel storyNameLabel = new JLabel("Название истории:");
        storyNameLabel.setFont(ModernTheme.FONT_BOLD);
        storyNameLabel.setForeground(ModernTheme.TEXT_PRIMARY);
        
        tabNameField = ModernTheme.createTextField("Введите название истории");
        tabNameField.setText(getStoryName());

        // Размещение компонентов
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        content.add(storyNumberLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        content.add(storyNumberField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        content.add(storyNameLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        content.add(tabNameField, gbc);

        card.add(content, BorderLayout.CENTER);
        return card;
    }
    
    /**
     * Создает карточку режима ввода данных
     */
    private JPanel createInputModeCard() {
        JPanel card = ModernTheme.createCardWithTitle("⚙️ Режим работы");
        card.setLayout(new BorderLayout());
        
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        
        // Чекбокс сохранения ABAC политик
        checkBox = new JCheckBox("💾 Сохранить ABAC политики в файл");
        checkBox.setSelected(getCheckboxState());
        ModernTheme.styleCheckbox(checkBox);
        checkBox.setFont(ModernTheme.FONT_BOLD);
        checkBox.setForeground(ModernTheme.TEXT_PRIMARY);
        
        // Панель режима Excel
        JPanel excelModePanel = createExcelModePanel();
        
        content.add(checkBox);
        content.add(Box.createRigidArea(new Dimension(0, ModernTheme.PADDING_MEDIUM)));
        content.add(excelModePanel);
        
        card.add(content, BorderLayout.CENTER);
        return card;
    }
    
    /**
     * Создает панель режима Excel
     */
    private JPanel createExcelModePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(ModernTheme.SUCCESS_COLOR.brighter());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ModernTheme.SUCCESS_COLOR, 2),
            BorderFactory.createEmptyBorder(
                ModernTheme.PADDING_MEDIUM,
                ModernTheme.PADDING_MEDIUM, 
                ModernTheme.PADDING_MEDIUM, 
                ModernTheme.PADDING_MEDIUM
            )
        ));
        
        // Переключатель Excel
        excelInputCheckBox = new JCheckBox("📊 Загрузить данные из Excel файла");
        excelInputCheckBox.setSelected(getDefaultExcelInputCheckBox());
        excelInputCheckBox.setFont(ModernTheme.FONT_LARGE_BOLD);
        excelInputCheckBox.setForeground(new Color(0, 100, 0));
        excelInputCheckBox.setBackground(ModernTheme.SUCCESS_COLOR.brighter());
        excelInputCheckBox.setOpaque(true);
        excelInputCheckBox.setFocusPainted(false);
        excelInputCheckBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Описание
        JLabel description = new JLabel(
            "<html><i>При включении этого режима данные будут загружаться из Excel файла.<br/>" +
            "Вкладки 'Profile' и 'Permission' будут отключены.</i></html>"
        );
        description.setFont(ModernTheme.FONT_SMALL);
        description.setForeground(ModernTheme.TEXT_SECONDARY);
        
        panel.add(excelInputCheckBox);
        panel.add(Box.createRigidArea(new Dimension(0, ModernTheme.PADDING_SMALL)));
        panel.add(description);
        
        return panel;
    }
}
