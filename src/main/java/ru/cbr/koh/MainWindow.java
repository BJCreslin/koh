package ru.cbr.koh;

import ru.cbr.koh.panes_storage.PanelsHolder;
import ru.cbr.koh.properties.PropertiesService;

import javax.swing.*;

public class MainWindow {

    private final PropertiesService properties = PropertiesService.getInstance();

    public void start() {
        JFrame frame = new JFrame(properties.getTitle());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(properties.getHorizontalSize(), properties.getVerticalSize());
        frame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        PanelsHolder panelsHolder = new PanelsHolder();

        panelsHolder.getPanels().forEach(it->tabbedPane.addTab(it.getTitle(), it.createPanel()));


        frame.add(tabbedPane);
        frame.setVisible(true);
    }
}
