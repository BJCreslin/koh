package ru.cbr.koh.panes_storage.panels.logger_proxy;

import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.panes_storage.panels.logger_proxy.service.SpyService;
import ru.cbr.koh.panes_storage.panels.logger_proxy.service.SpyServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoggerProxyPanel implements PaneInterface {

    private static final String FILE_NAME = "logger.txt";

    private static final JTextField dossierKoProjectField = new JTextField();

    private final SpyService spyService;

    public LoggerProxyPanel() {
        spyService = new SpyServiceImpl();
    }

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

        dossierKoProjectField.setMaximumSize(new Dimension(Integer.MAX_VALUE, dossierKoProjectField.getPreferredSize().height));
        dossierKoProjectField.setText(getDossierKoDirectory());
        panel.add(Box.createVerticalStrut(10));
        panel.add(dossierKoProjectField);

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
                dossierKoProjectField.setText(fileChooser.getSelectedFile().getAbsolutePath());
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
                setLoggerProxy();
            } else {
                toggleButton.setText("OFF");
            }
        });

        panel.add(Box.createVerticalStrut(10));
        panel.add(toggleButton);

        return panel;
    }

    private void setLoggerProxy() {
        String dossierKoDirectory = dossierKoProjectField.getText();
        if (dossierKoDirectory.isEmpty() || Files.notExists(Paths.get(dossierKoDirectory)) || !Files.isDirectory(Paths.get(dossierKoDirectory))) {
            return;
        }
        spyService.addLoggerProxy(dossierKoDirectory);

    }

    private String getDossierKoDirectory() {
        Path path = Paths.get(FILE_NAME);
        if (Files.exists(path)) {
            try {
                return Files.readAllLines(path).get(0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "Choose a Dossier Ko Directory: D:\\javaproject\\ko";
    }

    public static void saveDossierKoDirectory() {
        if (dossierKoProjectField != null && !dossierKoProjectField.getText().isEmpty()) {
            try (FileWriter writer = new FileWriter(FILE_NAME)) {
                writer.write(dossierKoProjectField.getText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
