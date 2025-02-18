package ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser;

import java.util.ArrayList;
import java.util.List;

public class KeysStack {

    private List<String> keys;

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
        String value = changeValue(pair.value());
        if (pos <= currentSize) {
            keys.set(pos - 1, value);
            while (keys.size() > pos) {
                keys.remove(keys.size() - 1);
            }
        } else if (pos == currentSize + 1) {
            keys.add(value);
        } else {
            throw new IllegalArgumentException("Недопустимое значение shift. Ожидается: " + (currentSize + 1));
        }
    }

    private static String changeValue(String value) {
        if (value.contains("/n")) {
            value = value.replace("/n", "&#13;&#10;");
        }
        return value;
    }
}
