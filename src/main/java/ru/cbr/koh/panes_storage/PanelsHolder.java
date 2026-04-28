package ru.cbr.koh.panes_storage;

import ru.cbr.koh.app.AppContext;
import ru.cbr.koh.panes_storage.panels.application_logs.ApplicationLogsPanel;
import ru.cbr.koh.panes_storage.panels.logger_proxy.LoggerProxyPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.PermissionMigrationPanel;

import java.util.ArrayList;
import java.util.List;

public class PanelsHolder {

    private final List<PaneInterface> panels;

    public PanelsHolder(AppContext appContext) {
        panels = new ArrayList<>();
        panels.add(new PermissionMigrationPanel(appContext));
        panels.add(new LoggerProxyPanel(appContext));
        panels.add(new ApplicationLogsPanel(appContext));
    }

    public List<PaneInterface> getPanels() {
        return panels;
    }
}
