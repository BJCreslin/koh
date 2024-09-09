package ru.cbr.koh.utils;

import javax.swing.*;
import java.awt.*;


public final class JPanelUtils {

    public static void drawLine(JPanel panel) {
        // Добавляем разделительную линию
        JPanel linePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(panel.getWidth(), 10);
            }
        };
        panel.add(linePanel);
    }

    private JPanelUtils() {
    }
}
