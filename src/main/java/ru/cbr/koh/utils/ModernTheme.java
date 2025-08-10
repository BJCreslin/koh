package ru.cbr.koh.utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Современная тема оформления для приложения KOH
 */
public final class ModernTheme {
    
    // Основные цвета темы
    public static final Color PRIMARY_COLOR = new Color(64, 123, 255);      // Синий
    public static final Color PRIMARY_DARK = new Color(45, 85, 180);        // Темно-синий
    public static final Color PRIMARY_LIGHT = new Color(135, 171, 255);     // Светло-синий
    
    public static final Color SECONDARY_COLOR = new Color(108, 117, 125);   // Серый
    public static final Color SUCCESS_COLOR = new Color(40, 167, 69);       // Зеленый
    public static final Color WARNING_COLOR = new Color(255, 193, 7);       // Желтый
    public static final Color DANGER_COLOR = new Color(220, 53, 69);        // Красный
    public static final Color INFO_COLOR = new Color(23, 162, 184);         // Голубой
    
    // Цвета фона
    public static final Color BACKGROUND_PRIMARY = new Color(248, 249, 250);
    public static final Color BACKGROUND_SECONDARY = Color.WHITE;
    public static final Color BACKGROUND_DARK = new Color(52, 58, 64);
    
    // Цвета текста
    public static final Color TEXT_PRIMARY = new Color(33, 37, 41);
    public static final Color TEXT_SECONDARY = new Color(108, 117, 125);
    public static final Color TEXT_LIGHT = Color.WHITE;
    
    // Цвета границ
    public static final Color BORDER_COLOR = new Color(222, 226, 230);
    public static final Color BORDER_FOCUS = PRIMARY_COLOR;
    
    // Шрифты
    public static final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_LARGE = new Font("Segoe UI", Font.PLAIN, 16);
    public static final Font FONT_LARGE_BOLD = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);
    
    // Размеры
    public static final int BORDER_RADIUS = 8;
    public static final int PADDING_SMALL = 8;
    public static final int PADDING_MEDIUM = 16;
    public static final int PADDING_LARGE = 24;
    public static final int COMPONENT_HEIGHT = 40;
    public static final int BUTTON_HEIGHT = 44;
    
    private ModernTheme() {
        // Утилитный класс
    }
    
    /**
     * Создает современную кнопку
     */
    public static JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        styleButton(button, PRIMARY_COLOR, TEXT_LIGHT, PRIMARY_DARK);
        return button;
    }
    
    /**
     * Создает вторичную кнопку
     */
    public static JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        styleButton(button, BACKGROUND_SECONDARY, PRIMARY_COLOR, BACKGROUND_PRIMARY);
        button.setBorder(createRoundedBorder(PRIMARY_COLOR, 2));
        return button;
    }
    
    /**
     * Создает кнопку успеха
     */
    public static JButton createSuccessButton(String text) {
        JButton button = new JButton(text);
        styleButton(button, SUCCESS_COLOR, TEXT_LIGHT, new Color(34, 142, 58));
        return button;
    }
    
    /**
     * Создает кнопку с иконкой
     */
    public static JButton createIconButton(String text, Icon icon) {
        JButton button = createPrimaryButton(text);
        if (icon != null) {
            button.setIcon(icon);
            button.setIconTextGap(PADDING_SMALL);
        }
        return button;
    }
    
    /**
     * Стилизует кнопку
     */
    private static void styleButton(JButton button, Color bgColor, Color textColor, Color hoverColor) {
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setFont(FONT_BOLD);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(button.getPreferredSize().width, BUTTON_HEIGHT));
        
        // Эффект наведения
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }
    
    /**
     * Создает современное текстовое поле
     */
    public static JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField();
        styleTextField(textField, placeholder);
        return textField;
    }
    
    /**
     * Стилизует текстовое поле
     */
    public static void styleTextField(JTextField textField, String placeholder) {
        textField.setFont(FONT_REGULAR);
        textField.setBackground(BACKGROUND_SECONDARY);
        textField.setForeground(TEXT_PRIMARY);
        textField.setBorder(createRoundedBorder(BORDER_COLOR, 1));
        textField.setPreferredSize(new Dimension(textField.getPreferredSize().width, COMPONENT_HEIGHT));
        
        // Добавляем padding
        textField.setBorder(BorderFactory.createCompoundBorder(
            createRoundedBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(PADDING_SMALL, PADDING_MEDIUM, PADDING_SMALL, PADDING_MEDIUM)
        ));
        
        // Placeholder text (простая реализация)
        if (placeholder != null && !placeholder.isEmpty()) {
            textField.setToolTipText(placeholder);
        }
        
        // Эффект фокуса
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    createRoundedBorder(BORDER_FOCUS, 2),
                    BorderFactory.createEmptyBorder(PADDING_SMALL-1, PADDING_MEDIUM-1, PADDING_SMALL-1, PADDING_MEDIUM-1)
                ));
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    createRoundedBorder(BORDER_COLOR, 1),
                    BorderFactory.createEmptyBorder(PADDING_SMALL, PADDING_MEDIUM, PADDING_SMALL, PADDING_MEDIUM)
                ));
            }
        });
    }
    
    /**
     * Создает современную панель
     */
    public static JPanel createCard() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_SECONDARY);
        panel.setBorder(createCardBorder());
        return panel;
    }
    
    /**
     * Создает панель с заголовком
     */
    public static JPanel createCardWithTitle(String title) {
        JPanel card = createCard();
        card.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(PADDING_MEDIUM, PADDING_MEDIUM, PADDING_SMALL, PADDING_MEDIUM));
        
        card.add(titleLabel, BorderLayout.NORTH);
        return card;
    }
    
    /**
     * Стилизует чекбокс
     */
    public static void styleCheckbox(JCheckBox checkBox) {
        checkBox.setFont(FONT_REGULAR);
        checkBox.setBackground(BACKGROUND_SECONDARY);
        checkBox.setForeground(TEXT_PRIMARY);
        checkBox.setFocusPainted(false);
        checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    /**
     * Стилизует спиннер
     */
    public static void styleSpinner(JSpinner spinner) {
        spinner.setFont(FONT_REGULAR);
        spinner.setPreferredSize(new Dimension(spinner.getPreferredSize().width, COMPONENT_HEIGHT));
        
        // Стилизуем текстовое поле внутри спиннера
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
            textField.setFont(FONT_REGULAR);
            textField.setBackground(BACKGROUND_SECONDARY);
            textField.setForeground(TEXT_PRIMARY);
            textField.setBorder(BorderFactory.createEmptyBorder(PADDING_SMALL, PADDING_SMALL, PADDING_SMALL, PADDING_SMALL));
        }
    }
    
    /**
     * Создает закругленную границу
     */
    public static Border createRoundedBorder(Color color, int thickness) {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, thickness),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)
        );
    }
    
    /**
     * Создает границу для карточки
     */
    public static Border createCardBorder() {
        return BorderFactory.createCompoundBorder(
            createRoundedBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(PADDING_MEDIUM, PADDING_MEDIUM, PADDING_MEDIUM, PADDING_MEDIUM)
        );
    }
    
    /**
     * Создает тень для компонента (простая реализация)
     */
    public static Border createShadowBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 20), 1),
            BorderFactory.createEmptyBorder(PADDING_SMALL, PADDING_SMALL, PADDING_SMALL, PADDING_SMALL)
        );
    }
    
    /**
     * Применяет современную тему к главному окну
     */
    public static void applyToFrame(JFrame frame) {
        frame.setBackground(BACKGROUND_PRIMARY);
        frame.getContentPane().setBackground(BACKGROUND_PRIMARY);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(frame);
        } catch (Exception e) {
            // Игнорируем ошибки Look and Feel
        }
    }
    
    /**
     * Применяет современную тему к вкладкам
     */
    public static void applyToTabbedPane(JTabbedPane tabbedPane) {
        tabbedPane.setBackground(BACKGROUND_PRIMARY);
        tabbedPane.setForeground(TEXT_PRIMARY);
        tabbedPane.setFont(FONT_BOLD);
        
        tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, 
                                            int x, int y, int w, int h, boolean isSelected) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (isSelected) {
                    g2d.setColor(PRIMARY_COLOR);
                } else {
                    g2d.setColor(BACKGROUND_SECONDARY);
                }
                
                g2d.fillRoundRect(x, y, w, h, BORDER_RADIUS, BORDER_RADIUS);
                g2d.dispose();
            }
            
            @Override
            protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics,
                                   int tabIndex, String title, Rectangle textRect, boolean isSelected) {
                g.setColor(isSelected ? TEXT_LIGHT : TEXT_PRIMARY);
                super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
            }
        });
    }
}
