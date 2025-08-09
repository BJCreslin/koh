package ru.cbr.koh.panes_storage.panels.permission_migration.permission.factory;

import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.dialog_objects.PermissionDialogObject;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

import java.util.List;

/**
 * Фабрика для создания Permission объектов из PermissionDialogObject
 */
public class DialogPermissionFactory implements PermissionFactory<PermissionDialogObject> {

    @Override
    public Permission create(PermissionDialogObject source) {
        if (source == null) {
            throw new IllegalArgumentException("PermissionDialogObject cannot be null");
        }
        
        return new Permission(source);
    }

    @Override
    public boolean canHandle(Object source) {
        return source instanceof PermissionDialogObject;
    }

    /**
     * Создает Permission из PermissionDialogObject с дополнительными профилями
     */
    public Permission createWithProfiles(PermissionDialogObject dialogObject, List<Profile> profiles) {
        if (dialogObject == null) {
            throw new IllegalArgumentException("PermissionDialogObject cannot be null");
        }
        
        return new Permission(dialogObject, profiles);
    }

    /**
     * Создает Permission из PermissionDialogObject с одним профилем
     */
    public Permission createWithProfile(PermissionDialogObject dialogObject, Profile profile) {
        return createWithProfiles(dialogObject, List.of(profile));
    }

    /**
     * Создает Permission из PermissionDialogObject для конкретного департамента
     */
    public Permission createForDepartment(PermissionDialogObject dialogObject, List<Profile> departmentProfiles) {
        if (dialogObject == null) {
            throw new IllegalArgumentException("PermissionDialogObject cannot be null");
        }
        
        if (departmentProfiles == null || departmentProfiles.isEmpty()) {
            return create(dialogObject);
        }
        
        return createWithProfiles(dialogObject, departmentProfiles);
    }
}
