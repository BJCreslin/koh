package ru.cbr.koh.panes_storage.panels.permission;

import ru.cbr.koh.panes_storage.PaneInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PermissionPanel implements PaneInterface {

    @Override
    public String getTitle() {
        return "Permission";
    }

    @Override
    public JComponent createPanel() {
        // Создание панели для группы элементов
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Кнопки для добавления новых паннелей
        JButton addButton = new JButton("Add Components");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                JPanel newPanel = createComponentPanel();
//                panel.add(newPanel);
                panel.add(Box.createVerticalStrut(10)); // Добавление разделителя между группами
                panel.add(new JSeparator()); // Разделительная линия
                panel.add(Box.createVerticalStrut(10));
                panel.repaint();
            }
        });
        panel.add(addButton, BorderLayout.SOUTH);
        return panel;

    }

    private JPanel createComponentPanel() {
    JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    // Добавление метки Key и текстового поля
    JLabel labelKey = new JLabel("Key", SwingConstants.CENTER);
        panel.add(labelKey);
    JTextField textFieldKey = new JTextField();
        panel.add(textFieldKey);

        panel.add(Box.createVerticalStrut(10)); // Разделение

    // Добавление выпадающего списка
    JComboBox<PermissionType> comboBox = new JComboBox<>(PermissionType.values());
        panel.add(comboBox);

        panel.add(Box.createVerticalStrut(10)); // Разделение

    // Добавление метки и текстового поля для abacPermPresGroupAction
        panel.add(createLabelAndTextField("abacPermPresGroupAction"));

        panel.add(Box.createVerticalStrut(10)); // Разделение

    // Добавление метки и текстового поля для abacPermPresUserAction
        panel.add(createLabelAndTextField("abacPermPresUserAction"));

        panel.add(Box.createVerticalStrut(10)); // Разделение

    // Добавление метки и текстового поля для name
        panel.add(createLabelAndTextField("name"));

        panel.add(Box.createVerticalStrut(10)); // Разделение

    // Добавление метки и текстового поля для description
        panel.add(createLabelAndTextField("description"));

        return panel;
}

private JPanel createLabelAndTextField(String labelText) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    JLabel label = new JLabel(labelText, SwingConstants.CENTER);
    panel.add(label);
    JTextField textField = new JTextField();
    panel.add(textField);
    return panel;
}
}

