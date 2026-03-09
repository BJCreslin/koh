package ru.cbr.koh.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class BusinessTheme {

    private static final Logger LOGGER = Logger.getLogger(BusinessTheme.class.getName());

    private static final Color APP_BACKGROUND = new Color(245, 247, 250);
    private static final Color SURFACE = Color.WHITE;
    private static final Color BORDER = new Color(209, 214, 220);
    private static final Color TEXT_PRIMARY = new Color(36, 43, 51);
    private static final Color TEXT_MUTED = new Color(97, 105, 116);
    private static final Color ACCENT = new Color(31, 78, 121);
    private static final Color ACCENT_DARK = new Color(22, 56, 88);

    private static final FontUIResource BASE_FONT = new FontUIResource("Segoe UI", Font.PLAIN, 13);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font SECTION_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 12);

    private static boolean applied;

    private BusinessTheme() {
    }

    public static synchronized void apply() {
        if (applied) {
            return;
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Не удалось установить системный LookAndFeel", exception);
        }

        applyGlobalFonts();

        UIManager.put("Panel.background", APP_BACKGROUND);
        UIManager.put("TabbedPane.background", APP_BACKGROUND);
        UIManager.put("TabbedPane.selected", SURFACE);
        UIManager.put("TabbedPane.focus", APP_BACKGROUND);
        UIManager.put("TabbedPane.contentAreaColor", SURFACE);
        UIManager.put("TabbedPane.light", BORDER);
        UIManager.put("TabbedPane.highlight", BORDER);
        UIManager.put("TabbedPane.shadow", BORDER);
        UIManager.put("TabbedPane.darkShadow", BORDER);

        UIManager.put("Label.foreground", TEXT_PRIMARY);
        UIManager.put("TextField.background", SURFACE);
        UIManager.put("TextField.foreground", TEXT_PRIMARY);
        UIManager.put("TextField.caretForeground", ACCENT_DARK);
        UIManager.put("TextField.border", new CompoundBorder(new LineBorder(BORDER), new EmptyBorder(6, 8, 6, 8)));

        UIManager.put("TextArea.background", new Color(251, 252, 253));
        UIManager.put("TextArea.foreground", TEXT_PRIMARY);
        UIManager.put("TextArea.border", new CompoundBorder(new LineBorder(BORDER), new EmptyBorder(8, 8, 8, 8)));

        UIManager.put("CheckBox.background", APP_BACKGROUND);
        UIManager.put("CheckBox.foreground", TEXT_PRIMARY);
        UIManager.put("ComboBox.background", SURFACE);
        UIManager.put("ComboBox.foreground", TEXT_PRIMARY);

        UIManager.put("Button.foreground", TEXT_PRIMARY);
        UIManager.put("Button.background", SURFACE);
        UIManager.put("Button.border", new CompoundBorder(new LineBorder(BORDER), new EmptyBorder(7, 12, 7, 12)));

        applied = true;
    }

    public static JLabel createPageTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(TITLE_FONT);
        label.setForeground(TEXT_PRIMARY);
        return label;
    }

    public static void styleSectionLabel(JLabel label) {
        label.setFont(SECTION_FONT);
        label.setForeground(TEXT_PRIMARY);
    }

    public static void styleFormLabel(JLabel label) {
        label.setFont(LABEL_FONT);
        label.setForeground(TEXT_MUTED);
    }

    public static void stylePrimaryButton(AbstractButton button) {
        button.setBackground(ACCENT);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new CompoundBorder(new LineBorder(ACCENT_DARK), new EmptyBorder(8, 14, 8, 14)));
    }

    public static void styleSecondaryButton(AbstractButton button) {
        button.setBackground(SURFACE);
        button.setForeground(TEXT_PRIMARY);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new CompoundBorder(new LineBorder(BORDER), new EmptyBorder(8, 14, 8, 14)));
    }

    public static void styleToggleButton(JToggleButton button, boolean selected) {
        if (selected) {
            stylePrimaryButton(button);
            button.setText("ON");
        } else {
            styleSecondaryButton(button);
            button.setText("OFF");
        }
    }

    public static Border pagePadding() {
        return new EmptyBorder(18, 18, 18, 18);
    }

    public static Border cardBorder() {
        return new CompoundBorder(new LineBorder(BORDER), new EmptyBorder(14, 14, 14, 14));
    }

    public static Border sectionBorder(String title) {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(new LineBorder(BORDER), title);
        titledBorder.setTitleFont(SECTION_FONT);
        titledBorder.setTitleColor(TEXT_PRIMARY);
        return new CompoundBorder(titledBorder, new EmptyBorder(8, 8, 8, 8));
    }

    private static void applyGlobalFonts() {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, BASE_FONT);
            }
        }
    }
}
