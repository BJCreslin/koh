package ru.cbr.koh.panes_storage.panels.permission_migration;

import ru.cbr.koh.exceptions.SerializationException;
import ru.cbr.koh.main_window.SaveablePanel;
import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.InformationPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.ProfilePanel;
import ru.cbr.koh.panes_storage.strategy.PanelContext;
import ru.cbr.koh.panes_storage.strategy.PanelType;
import ru.cbr.koh.panes_storage.strategy.PanelStrategyFactory;
import ru.cbr.koh.panes_storage.strategy.impl.PermissionMigrationPanelStrategy;

import javax.swing.*;

/**
 * Панель миграции разрешений, использующая паттерн Strategy
 * @deprecated Используйте {@link PanelContext} с {@link PermissionMigrationPanelStrategy}
 */
@Deprecated
public class PermissionMigrationPanel implements PaneInterface, SaveablePanel {

    private final PanelContext context;
    private final PermissionMigrationPanelStrategy strategy;

    public PermissionMigrationPanel() {
        this.strategy = (PermissionMigrationPanelStrategy) PanelStrategyFactory.createStrategy(PanelType.PERMISSION_MIGRATION);
        this.context = new PanelContext(strategy);
    }

    @Override
    public String getTitle() {
        return context.getTitle();
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        return context.createPanel(frame);
    }

    /**
     * Получить панель профиля
     * @return панель профиля
     */
    public ProfilePanel getProfilePanel() {
        return strategy.getProfilePanel();
    }
    
    /**
     * Получить информационную панель
     * @return информационная панель
     */
    public InformationPanel getInformationPanel() {
        return strategy.getInformationPanel();
    }
    
    @Override
    public void saveData() throws SerializationException {
        context.saveData();
    }
}
