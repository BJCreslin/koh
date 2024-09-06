package ru.cbr.koh.panes_storage.panels.permission_panel.domain.profile_secure_elem;


import ru.cbr.koh.panes_storage.panels.permission_panel.domain.base_clases.PermissionIdAbstract;

public class ProfileSecureElemPermissionId extends PermissionIdAbstract {

    private final String textPart;

    private static final String ID_TEMPLATE = "%s_add_permissions_to_%s_to_profiles";

    public ProfileSecureElemPermissionId(String textPart) {
        this.textPart = textPart;
    }

    @Override
    public String toString() {
        return super.create(textPart, ID_TEMPLATE);
    }
}
