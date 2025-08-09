package ru.cbr.koh.main_window;

import ru.cbr.koh.exceptions.SerializationException;

/**
 * Интерфейс для панелей, которые могут сохранять свои данные
 */
public interface SaveablePanel {
    
    /**
     * Сохраняет данные панели
     * @throws SerializationException если произошла ошибка при сохранении
     */
    void saveData() throws SerializationException;
}
