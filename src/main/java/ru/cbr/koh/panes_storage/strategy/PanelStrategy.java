package ru.cbr.koh.panes_storage.strategy;

import javax.swing.*;

/**
 * Интерфейс стратегии для создания панелей
 */
public interface PanelStrategy {
    
    /**
     * Получить название панели
     * @return название панели
     */
    String getTitle();
    
    /**
     * Создать компонент панели
     * @param frame родительский фрейм
     * @return созданный компонент
     */
    JComponent createPanel(JFrame frame);
    
    /**
     * Получить тип панели для идентификации
     * @return тип панели
     */
    PanelType getPanelType();
}
