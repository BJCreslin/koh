package ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser;

import java.util.ArrayList;
import java.util.List;

public class KeysStack {

    private final List<String> keys;

    public KeysStack() {
        keys = new ArrayList<>();
    }

    public String getKey() {
        return String.join("#", keys);
    }

    public void push(ValueShiftPair pair) {
        int pos = pair.shift() + 1;
        if (pos < 1) {
            throw new IllegalArgumentException("Значение shift должно быть больше или равно 0");
        }
        
        int currentSize = keys.size();
        String value = ExcelUtils.cleanValue(pair.value());
        
        if (pos <= currentSize) {
            keys.set(pos - 1, value);
            // Оптимизированное удаление элементов с конца
            if (keys.size() > pos) {
                keys.subList(pos, keys.size()).clear();
            }
        } else if (pos == currentSize + 1) {
            keys.add(value);
        } else {
            throw new IllegalArgumentException("Недопустимое значение shift. Ожидается: " + (currentSize + 1));
        }
    }
}
