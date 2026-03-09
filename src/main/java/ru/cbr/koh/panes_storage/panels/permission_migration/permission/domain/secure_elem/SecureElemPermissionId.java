package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.secure_elem;


import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.base_clases.PermissionIdAbstract;

public class SecureElemPermissionId extends PermissionIdAbstract {

    private final String generatedId;

    private static final String ID_TEMPLATE = "%s_add_permissions_to_%s";

    public SecureElemPermissionId(String textPart) {
        this.generatedId = super.create(textPart, ID_TEMPLATE);
    }

    @Override
    public String toString() {
        return generatedId;
    }
}
