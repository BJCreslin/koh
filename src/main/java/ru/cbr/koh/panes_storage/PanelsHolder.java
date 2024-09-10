package ru.cbr.koh.panes_storage;

import ru.cbr.koh.panes_storage.panels.logger_proxy.LoggerProxyPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.PermissionMigrationPanel;

import java.util.ArrayList;
import java.util.List;

public class PanelsHolder {

    private final List<PaneInterface> panels;

    public PanelsHolder() {
        panels = new ArrayList<>();
        panels.add(new PermissionMigrationPanel());
        panels.add(new LoggerProxyPanel());
    }

    public List<PaneInterface> getPanels() {
        return panels;
    }
}
