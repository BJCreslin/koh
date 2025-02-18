package ru.cbr.koh.panes_storage.panels.permission_migration.excel;

import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser.FileReader;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.InformationPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.base_clases.ChangeLog;
import ru.cbr.koh.properties.PropertiesService;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.List;

public class ExcelInputPanel implements PaneInterface {

    private File file;

    private final PropertiesService properties = PropertiesService.getInstance();

    @Override
    public String getTitle() {
        return "Data from Excel";
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ImageIcon originalIcon = new ImageIcon("images.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JButton folderButton = new JButton("Select Dossier Ko Directory", scaledIcon);  //
        folderButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        folderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        folderButton.setFocusPainted(false);

        folderButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
            setCurrentDirectory(fileChooser);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                createChangelogMigration();
                saveCurrentDirectoryToProperty();
            }
        });
        jPanel.add(folderButton);
        return jPanel;
    }

    private void createChangelogMigration() {
        FileReader reader = new FileReader(file);
        List<Permission> permissions = reader.read();
        var information = InformationPanel.getInformation();
        ChangeLog changeLog = new ChangeLog(information, permissions);
        changeLog.create();
    }

    private void saveCurrentDirectoryToProperty() {
        properties.setPathExcel(file.getParentFile().getAbsolutePath());
    }

    private void setCurrentDirectory(JFileChooser fileChooser) {
        var dir = properties.getPathExcel();
        if (dir == null || dir.isEmpty()) {
            return;
        }
        fileChooser.setCurrentDirectory(new File(dir));
    }
}
