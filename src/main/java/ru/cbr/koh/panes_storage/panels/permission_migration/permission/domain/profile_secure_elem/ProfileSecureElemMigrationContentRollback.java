package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.profile_secure_elem;


import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

import java.util.List;
import java.util.stream.Collectors;


public class ProfileSecureElemMigrationContentRollback {

    private static final String TEMPLATE = """
             <delete tableName="rights_template_profile_secur_elem">
                   <where>
                       profile_id IN (
                         SELECT id FROM profile
                            WHERE profile_name IN (%s) )
                            AND secur_elem_id IN (
                                SELECT id FROM secur_elem WHERE key IN (%s) )
                   </where>
             </delete>
""";

    private final String content;

    public ProfileSecureElemMigrationContentRollback(List<Permission> permissions, Profile profile) {

        this.content = String.format(TEMPLATE, getProfile(profile), getPermissionKeys(permissions));
    }

    private String getPermissionKeys(List<Permission> permissions) {
        return permissions.stream()
                .map(Permission::getKey)
                .map(it -> "'" + it + "'")
                .collect(Collectors.joining(",  "));
    }

    private String getProfile(Profile profile) {
        return "'" + profile.getName() + "'";
    }

    @Override
    public String toString() {
        return content;
    }
}
