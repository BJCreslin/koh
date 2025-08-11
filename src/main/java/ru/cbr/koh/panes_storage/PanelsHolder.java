package ru.cbr.koh.panes_storage;

import ru.cbr.koh.panes_storage.strategy.PanelContext;
import ru.cbr.koh.panes_storage.strategy.PanelStrategyFactory;
import ru.cbr.koh.panes_storage.strategy.PanelType;

import java.util.ArrayList;
import java.util.List;

/**
 * Держатель панелей, использующий паттерн Strategy
 */
public class PanelsHolder {

    private final List<PaneInterface> panels;

    public PanelsHolder() {
        panels = createPanelsUsingStrategy();
    }

    /**
     * Получить список панелей
     * @return список панелей
     */
    public List<PaneInterface> getPanels() {
        return new ArrayList<>(panels);
    }
    
    /**
     * Получить панель по типу
     * @param panelType тип панели
     * @return панель или null, если не найдена
     */
    public PaneInterface getPanelByType(PanelType panelType) {
        return panels.stream()
                .filter(panel -> panel instanceof PanelContext)
                .map(panel -> (PanelContext) panel)
                .filter(context -> context.getPanelType() == panelType)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Добавить панель
     * @param panelType тип панели для добавления
     */
    public void addPanel(PanelType panelType) {
        PanelContext context = new PanelContext(PanelStrategyFactory.createStrategy(panelType));
        panels.add(context);
    }
    
    /**
     * Удалить панель по типу
     * @param panelType тип панели для удаления
     * @return true, если панель была удалена
     */
    public boolean removePanel(PanelType panelType) {
        return panels.removeIf(panel -> panel instanceof PanelContext && 
                ((PanelContext) panel).getPanelType() == panelType);
    }

    /**
     * Создать панели, используя фабрику
     * @return список панелей
     */
    private List<PaneInterface> createPanelsUsingStrategy() {
        return PanelFactory.createDefaultPanels();
    }
}
