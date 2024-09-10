package ru.cbr.koh.main_window;

import ru.cbr.koh.panes_storage.PanelsHolder;
import ru.cbr.koh.panes_storage.panels.logger_proxy.LoggerProxyPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.InformationPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.ProfilePanel;
import ru.cbr.koh.properties.PropertiesService;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow {

    private final PropertiesService properties = PropertiesService.getInstance();

    public void start() {
        JFrame frame = new JFrame(properties.getTitle());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(properties.getHorizontalSize(), properties.getVerticalSize());
        frame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        PanelsHolder panelsHolder = new PanelsHolder();

        panelsHolder.getPanels().forEach(it -> tabbedPane.addTab(it.getTitle(), it.createPanel(frame)));

        frame.add(tabbedPane);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ProfilePanel.saveCheckBoxesFile();
                InformationPanel.setInformation();
                LoggerProxyPanel.saveDossierKoDirectory();
                System.exit(0);
            }
        });
    }
}
