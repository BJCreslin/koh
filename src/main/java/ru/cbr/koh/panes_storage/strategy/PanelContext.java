package ru.cbr.koh.panes_storage.strategy;

import ru.cbr.koh.exceptions.SerializationException;
import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.main_window.SaveablePanel;

import javax.swing.*;

/**
 * Контекст для выполнения стратегии панели
 */
public class PanelContext implements PaneInterface, SaveablePanel {
    
    private final PanelStrategy strategy;
    
    public PanelContext(PanelStrategy strategy) {
        this.strategy = strategy;
    }
    
    @Override
    public String getTitle() {
        return strategy.getTitle();
    }
    
    @Override
    public JComponent createPanel(JFrame frame) {
        return strategy.createPanel(frame);
    }
    
    @Override
    public void saveData() throws SerializationException {
        if (strategy instanceof SaveablePanelStrategy) {
            ((SaveablePanelStrategy) strategy).saveData();
        }
    }
    
    /**
     * Получить тип панели
     * @return тип панели
     */
    public PanelType getPanelType() {
        return strategy.getPanelType();
    }
    
    /**
     * Получить стратегию
     * @return текущая стратегия
     */
    public PanelStrategy getStrategy() {
        return strategy;
    }
}
