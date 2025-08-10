package ru.cbr.koh.utils;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Дополнительные утилиты для создания современного пользовательского интерфейса
 */
public final class ModernUIUtils {
    
    private ModernUIUtils() {
        // Утилитный класс
    }
    
    /**
     * Создает современную прогресс-бар
     */
    public static JProgressBar createModernProgressBar() {
        JProgressBar progressBar = new JProgressBar();
        progressBar.setBackground(ModernTheme.BACKGROUND_PRIMARY);
        progressBar.setForeground(ModernTheme.PRIMARY_COLOR);
        progressBar.setBorderPainted(false);
        progressBar.setStringPainted(true);
        progressBar.setFont(ModernTheme.FONT_SMALL);
        progressBar.setPreferredSize(new Dimension(300, 24));
        return progressBar;
    }
    
    /**
     * Создает современный разделитель
     */
    public static JSeparator createModernSeparator() {
        JSeparator separator = new JSeparator();
        separator.setForeground(ModernTheme.BORDER_COLOR);
        separator.setBackground(ModernTheme.BORDER_COLOR);
        return separator;
    }
    
    /**
     * Создает современную область прокрутки
     */
    public static JScrollPane createModernScrollPane(Component component) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setBorder(ModernTheme.createRoundedBorder(ModernTheme.BORDER_COLOR, 1));
        scrollPane.getViewport().setBackground(ModernTheme.BACKGROUND_SECONDARY);
        scrollPane.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        
        styleScrollBar(scrollPane.getVerticalScrollBar());
        styleScrollBar(scrollPane.getHorizontalScrollBar());
        
        return scrollPane;
    }
    
    /**
     * Стилизует полосу прокрутки
     */
    private static void styleScrollBar(JScrollBar scrollBar) {
        scrollBar.setBackground(ModernTheme.BACKGROUND_PRIMARY);
        scrollBar.setForeground(ModernTheme.PRIMARY_COLOR);
        scrollBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = ModernTheme.SECONDARY_COLOR;
                this.trackColor = ModernTheme.BACKGROUND_PRIMARY;
            }
            
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }
            
            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }
            
            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });
    }
    
    /**
     * Создает современное текстовое поле для многострочного текста
     */
    public static JTextArea createModernTextArea(String placeholder, int rows) {
        JTextArea textArea = new JTextArea(rows, 0);
        textArea.setFont(ModernTheme.FONT_REGULAR);
        textArea.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        textArea.setForeground(ModernTheme.TEXT_PRIMARY);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(
            ModernTheme.PADDING_SMALL, 
            ModernTheme.PADDING_SMALL, 
            ModernTheme.PADDING_SMALL, 
            ModernTheme.PADDING_SMALL
        ));
        
        if (placeholder != null && !placeholder.isEmpty()) {
            textArea.setToolTipText(placeholder);
        }
        
        return textArea;
    }
    
    /**
     * Создает современный список
     */
    public static <T> JList<T> createModernList(ListModel<T> model) {
        JList<T> list = new JList<>(model);
        list.setFont(ModernTheme.FONT_REGULAR);
        list.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        list.setForeground(ModernTheme.TEXT_PRIMARY);
        list.setSelectionBackground(ModernTheme.PRIMARY_LIGHT);
        list.setSelectionForeground(ModernTheme.TEXT_LIGHT);
        list.setBorder(BorderFactory.createEmptyBorder(
            ModernTheme.PADDING_SMALL, 
            ModernTheme.PADDING_SMALL, 
            ModernTheme.PADDING_SMALL, 
            ModernTheme.PADDING_SMALL
        ));
        list.setFixedCellHeight(ModernTheme.COMPONENT_HEIGHT);
        return list;
    }
    
    /**
     * Создает современную таблицу
     */
    public static JTable createModernTable() {
        JTable table = new JTable();
        table.setFont(ModernTheme.FONT_REGULAR);
        table.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        table.setForeground(ModernTheme.TEXT_PRIMARY);
        table.setSelectionBackground(ModernTheme.PRIMARY_LIGHT);
        table.setSelectionForeground(ModernTheme.TEXT_LIGHT);
        table.setGridColor(ModernTheme.BORDER_COLOR);
        table.setRowHeight(ModernTheme.COMPONENT_HEIGHT);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));
        
        JTableHeader header = table.getTableHeader();
        if (header != null) {
            header.setFont(ModernTheme.FONT_BOLD);
            header.setBackground(ModernTheme.PRIMARY_COLOR);
            header.setForeground(ModernTheme.TEXT_LIGHT);
            header.setBorder(BorderFactory.createEmptyBorder(
                ModernTheme.PADDING_SMALL, 
                ModernTheme.PADDING_SMALL, 
                ModernTheme.PADDING_SMALL, 
                ModernTheme.PADDING_SMALL
            ));
        }
        
        return table;
    }
    
    /**
     * Создает современное комбо-бокс
     */
    public static <T> JComboBox<T> createModernComboBox(T[] items) {
        JComboBox<T> comboBox = new JComboBox<>(items);
        comboBox.setFont(ModernTheme.FONT_REGULAR);
        comboBox.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        comboBox.setForeground(ModernTheme.TEXT_PRIMARY);
        comboBox.setBorder(ModernTheme.createRoundedBorder(ModernTheme.BORDER_COLOR, 1));
        comboBox.setPreferredSize(new Dimension(comboBox.getPreferredSize().width, ModernTheme.COMPONENT_HEIGHT));
        return comboBox;
    }
    
    /**
     * Создает современный слайдер
     */
    public static JSlider createModernSlider(int min, int max, int value) {
        JSlider slider = new JSlider(min, max, value);
        slider.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        slider.setForeground(ModernTheme.PRIMARY_COLOR);
        slider.setFont(ModernTheme.FONT_SMALL);
        return slider;
    }
    
    /**
     * Создает всплывающее уведомление
     */
    public static void showNotification(Component parent, String title, String message, NotificationType type) {
        Color bgColor;
        Color textColor = ModernTheme.TEXT_LIGHT;
        String icon;
        
        switch (type) {
            case SUCCESS:
                bgColor = ModernTheme.SUCCESS_COLOR;
                icon = "✅";
                break;
            case WARNING:
                bgColor = ModernTheme.WARNING_COLOR;
                textColor = ModernTheme.TEXT_PRIMARY;
                icon = "⚠️";
                break;
            case ERROR:
                bgColor = ModernTheme.DANGER_COLOR;
                icon = "❌";
                break;
            case INFO:
            default:
                bgColor = ModernTheme.INFO_COLOR;
                icon = "ℹ️";
                break;
        }
        
        JDialog notification = new JDialog();
        notification.setUndecorated(true);
        notification.setModal(false);
        notification.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bgColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 2),
            BorderFactory.createEmptyBorder(
                ModernTheme.PADDING_MEDIUM,
                ModernTheme.PADDING_MEDIUM, 
                ModernTheme.PADDING_MEDIUM, 
                ModernTheme.PADDING_MEDIUM
            )
        ));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, ModernTheme.PADDING_SMALL));
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(bgColor);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(ModernTheme.FONT_BOLD);
        titleLabel.setForeground(textColor);
        
        JLabel messageLabel = new JLabel("<html>" + message + "</html>");
        messageLabel.setFont(ModernTheme.FONT_REGULAR);
        messageLabel.setForeground(textColor);
        
        textPanel.add(titleLabel);
        if (!message.isEmpty()) {
            textPanel.add(Box.createVerticalStrut(4));
            textPanel.add(messageLabel);
        }
        
        panel.add(iconLabel, BorderLayout.WEST);
        panel.add(textPanel, BorderLayout.CENTER);
        
        notification.add(panel);
        notification.pack();
        
        if (parent != null) {
            Point parentLocation = parent.getLocationOnScreen();
            Dimension parentSize = parent.getSize();
            Dimension notificationSize = notification.getSize();
            
            int x = parentLocation.x + parentSize.width - notificationSize.width - 20;
            int y = parentLocation.y + 20;
            
            notification.setLocation(x, y);
        } else {
            notification.setLocationRelativeTo(null);
        }
        
        notification.setVisible(true);
        
        Timer timer = new Timer(4000, e -> notification.dispose());
        timer.setRepeats(false);
        timer.start();
        
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                notification.dispose();
            }
        });
    }
    
    /**
     * Типы уведомлений
     */
    public enum NotificationType {
        SUCCESS, WARNING, ERROR, INFO
    }
    
    /**
     * Создает современную панель загрузки
     */
    public static JPanel createLoadingPanel(String message) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        panel.setBorder(ModernTheme.createCardBorder());
        
        JProgressBar progressBar = createModernProgressBar();
        progressBar.setIndeterminate(true);
        
        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setFont(ModernTheme.FONT_REGULAR);
        messageLabel.setForeground(ModernTheme.TEXT_PRIMARY);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(
            ModernTheme.PADDING_MEDIUM, 0, ModernTheme.PADDING_SMALL, 0
        ));
        
        panel.add(messageLabel, BorderLayout.CENTER);
        panel.add(progressBar, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Создает современный диалог подтверждения
     */
    public static boolean showConfirmDialog(Component parent, String title, String message) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(
            ModernTheme.PADDING_LARGE,
            ModernTheme.PADDING_LARGE, 
            ModernTheme.PADDING_LARGE, 
            ModernTheme.PADDING_LARGE
        ));
        
        JLabel messageLabel = new JLabel("<html>" + message + "</html>");
        messageLabel.setFont(ModernTheme.FONT_REGULAR);
        messageLabel.setForeground(ModernTheme.TEXT_PRIMARY);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, ModernTheme.PADDING_MEDIUM, 0));
        buttonPanel.setBackground(ModernTheme.BACKGROUND_SECONDARY);
        
        final boolean[] result = {false};
        
        JButton confirmButton = ModernTheme.createPrimaryButton("Подтвердить");
        confirmButton.addActionListener(e -> {
            result[0] = true;
            dialog.dispose();
        });
        
        JButton cancelButton = ModernTheme.createSecondaryButton("Отмена");
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(confirmButton);
        
        mainPanel.add(messageLabel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
        
        return result[0];
    }
}
