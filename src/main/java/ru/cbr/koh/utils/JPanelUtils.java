package ru.cbr.koh.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Утилиты для работы с панелями (обновлено для современного дизайна)
 */
public final class JPanelUtils {

    /**
     * Добавляет современную разделительную линию
     * @deprecated Используйте ModernUIUtils.createModernSeparator()
     */
    @Deprecated
    public static void drawLine(JPanel panel) {
        // Добавляем современную разделительную линию
        JSeparator separator = ModernUIUtils.createModernSeparator();
        separator.setPreferredSize(new Dimension(panel.getWidth(), 1));
        panel.add(separator);
    }
    
    /**
     * Добавляет современную разделительную линию с отступами
     */
    public static void addModernSeparator(JPanel panel) {
        panel.add(Box.createRigidArea(new Dimension(0, ModernTheme.PADDING_SMALL)));
        
        JSeparator separator = ModernUIUtils.createModernSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        panel.add(separator);
        
        panel.add(Box.createRigidArea(new Dimension(0, ModernTheme.PADDING_SMALL)));
    }
    
    /**
     * Создает современную панель с заголовком и контентом
     */
    public static JPanel createSectionPanel(String title, JComponent content) {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        section.setBorder(ModernTheme.createCardBorder());
        
        // Заголовок секции
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(ModernTheme.FONT_TITLE);
        titleLabel.setForeground(ModernTheme.TEXT_PRIMARY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, ModernTheme.PADDING_SMALL, 0));
        
        section.add(titleLabel, BorderLayout.NORTH);
        section.add(content, BorderLayout.CENTER);
        
        return section;
    }
    
    /**
     * Создает панель с кнопками действий
     */
    public static JPanel createActionPanel(JButton... buttons) {
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, ModernTheme.PADDING_SMALL, 0));
        actionPanel.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        
        for (JButton button : buttons) {
            actionPanel.add(button);
        }
        
        return actionPanel;
    }
    
    /**
     * Создает панель формы с полями
     */
    public static JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        return formPanel;
    }
    
    /**
     * Добавляет поле формы в панель
     */
    public static void addFormField(JPanel formPanel, String labelText, JComponent field, int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(ModernTheme.PADDING_SMALL, ModernTheme.PADDING_SMALL, 
                              ModernTheme.PADDING_SMALL, ModernTheme.PADDING_SMALL);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Метка
        JLabel label = new JLabel(labelText);
        label.setFont(ModernTheme.FONT_BOLD);
        label.setForeground(ModernTheme.TEXT_PRIMARY);
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        formPanel.add(label, gbc);
        
        // Поле
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(field, gbc);
    }
    
    /**
     * Создает панель с вертикальным списком элементов
     */
    public static JPanel createVerticalListPanel(JComponent... components) {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        
        for (int i = 0; i < components.length; i++) {
            JComponent component = components[i];
            component.setAlignmentX(Component.LEFT_ALIGNMENT);
            listPanel.add(component);
            
            // Добавляем отступ между элементами (кроме последнего)
            if (i < components.length - 1) {
                listPanel.add(Box.createRigidArea(new Dimension(0, ModernTheme.PADDING_MEDIUM)));
            }
        }
        
        return listPanel;
    }
    
    /**
     * Создает панель с горизонтальным списком элементов
     */
    public static JPanel createHorizontalListPanel(JComponent... components) {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.X_AXIS));
        listPanel.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        
        for (int i = 0; i < components.length; i++) {
            JComponent component = components[i];
            component.setAlignmentY(Component.CENTER_ALIGNMENT);
            listPanel.add(component);
            
            // Добавляем отступ между элементами (кроме последнего)
            if (i < components.length - 1) {
                listPanel.add(Box.createRigidArea(new Dimension(ModernTheme.PADDING_MEDIUM, 0)));
            }
        }
        
        return listPanel;
    }

    private JPanelUtils() {
        // Утилитный класс
    }
}
