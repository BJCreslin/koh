package ru.cbr.koh.panes_storage.strategy;

import ru.cbr.koh.exceptions.SerializationException;

/**
 * Интерфейс стратегии для панелей, которые могут сохранять данные
 */
public interface SaveablePanelStrategy extends PanelStrategy {
    
    /**
     * Сохранить данные панели
     * @throws SerializationException если произошла ошибка при сохранении
     */
    void saveData() throws SerializationException;
}
