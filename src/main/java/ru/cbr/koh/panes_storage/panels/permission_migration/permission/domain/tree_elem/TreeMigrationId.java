package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.tree_elem;

import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.base_clases.PermissionIdAbstract;

public class TreeMigrationId extends PermissionIdAbstract {

    private final String textPart;

    private static final String ID_TEMPLATE = "%s_add_permissions_to_%s_to_elem_tree";

    public TreeMigrationId(String textPart) {
        this.textPart = textPart;
    }

    @Override
    public String toString() {
        return super.create(textPart, ID_TEMPLATE);
    }

}
