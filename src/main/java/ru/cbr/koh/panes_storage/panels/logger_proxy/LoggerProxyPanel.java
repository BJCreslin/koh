package ru.cbr.koh.panes_storage.panels.logger_proxy;

import ru.cbr.koh.panes_storage.PaneInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class LoggerProxyPanel implements PaneInterface {

    @Override
    public String getTitle() {
        return "Logger Proxy";
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel label = new JLabel("Choose a Dossier Ko Directory", SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label);

        JTextField textField = new JTextField();
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textField.getPreferredSize().height));
        panel.add(Box.createVerticalStrut(10));
        panel.add(textField);

        ImageIcon originalIcon = new ImageIcon("images.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);


        JButton folderButton = new JButton("Select Dossier Ko Directory", scaledIcon);  //
        folderButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        folderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        folderButton.setFocusPainted(false);
        folderButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        panel.add(Box.createVerticalStrut(10));
        panel.add(folderButton);

        JLabel proxyLabel = new JLabel("Proxy Logging");
        proxyLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(Box.createVerticalStrut(10));  // Add some vertical space
        panel.add(proxyLabel);

        JToggleButton toggleButton = new JToggleButton("OFF");
        toggleButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        toggleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        toggleButton.setFont(new Font("Arial", Font.BOLD, 18));
        toggleButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                toggleButton.setText("ON");
            } else {
                toggleButton.setText("OFF");
            }
        });

        panel.add(Box.createVerticalStrut(10));
        panel.add(toggleButton);

        return panel;
    }
}
