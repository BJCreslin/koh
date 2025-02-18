package ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser;

import java.util.ArrayList;
import java.util.List;

public class KeysStack {

    private List<String> stack;

    public KeysStack() {
        stack = new ArrayList<>();
    }

    public List<String> getStack() {
        return stack;
    }

    public String getKey() {
        return String.join("#", stack);
    }

    public void push(ValueShiftPair pair) {
        List<String> newStack = new ArrayList<>();
        if (pair.shift() > 0) {
            for (int i = 0; i < pair.shift(); i++) {
                newStack.add(stack.get(i));
            }
        }
        newStack.add(changeN(pair.value()));
        stack = newStack;
    }

    private static String changeN(String value) {
        if (value.contains("/n")) {
            value = value.replace("/n", "&#13;&#10;");
        }
        return value;
    }
}
