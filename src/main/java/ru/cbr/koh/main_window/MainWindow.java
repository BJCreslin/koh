package ru.cbr.koh.main_window;

import ru.cbr.koh.app.AppContext;
import ru.cbr.koh.panes_storage.PanelsHolder;
import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.properties.ApplicationProperties;
import ru.cbr.koh.properties.PropertiesService;
import ru.cbr.koh.ui.BusinessTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MainWindow {

    private final AppContext appContext;

    public MainWindow() {
        this(new AppContext(PropertiesService.getInstance()));
    }

    MainWindow(AppContext appContext) {
        this.appContext = appContext;
    }

    public void start() {
        BusinessTheme.apply();
        ApplicationProperties properties = appContext.getProperties();

        JFrame frame = new JFrame(properties.getTitle());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(properties.getHorizontalSize(), properties.getVerticalSize());
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(245, 247, 250));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        PanelsHolder panelsHolder = new PanelsHolder(appContext);

        panelsHolder.getPanels().forEach(it -> tabbedPane.addTab(it.getTitle(), it.createPanel(frame)));

        frame.add(tabbedPane);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (PaneInterface pane : panelsHolder.getPanels()) {
                    try {
                        pane.onClose();
                    } catch (RuntimeException exception) {
                        appContext.getErrorHandler().handle(frame, "Не удалось сохранить состояние панели " + pane.getTitle(), exception);
                    }
                }
            }
        });
    }
}
