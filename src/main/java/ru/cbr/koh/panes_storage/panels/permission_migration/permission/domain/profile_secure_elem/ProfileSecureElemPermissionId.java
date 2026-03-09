package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.profile_secure_elem;


import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.base_clases.PermissionIdAbstract;

public class ProfileSecureElemPermissionId extends PermissionIdAbstract {

    private final String generatedId;

    private static final String ID_TEMPLATE = "%s_add_permissions_to_%s_to_profiles";

    public ProfileSecureElemPermissionId(String textPart) {
        this.generatedId = super.create(textPart, ID_TEMPLATE);
    }

    @Override
    public String toString() {
        return generatedId;
    }
}
