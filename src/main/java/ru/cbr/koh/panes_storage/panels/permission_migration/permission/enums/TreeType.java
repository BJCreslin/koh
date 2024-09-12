package ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums;

public enum TreeType {

    KO(1, "Дерево прав для всех департаментов кроме ГИБР"),
    GIBR(2, "Дерево прав ГИБР");

    private final int id;
    private final String text;

    private TreeType(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
