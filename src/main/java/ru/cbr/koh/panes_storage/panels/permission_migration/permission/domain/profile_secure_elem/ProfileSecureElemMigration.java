package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.profile_secure_elem;


import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

public class ProfileSecureElemMigration {

    private final Permission permission;

    private final Profile profile;

    private static final String TEMPLATE = """
                    \n
                    <insert tableName="rights_template_profile_secur_elem">
                        <column name="id" valueSequenceNext="app_seq"/>
                        <column name="profile_id" valueComputed="(SELECT id FROM profile WHERE profile_name = '%s')"/>
                        <column name="secur_elem_id"
                                valueComputed="(SELECT id FROM secur_elem WHERE key = '%s')"/>
                    </insert>
            """;

    public ProfileSecureElemMigration(Permission permission, Profile profile) {
        this.permission = permission;
        this.profile = profile;
    }


    @Override
    public String toString() {
        return String.format(TEMPLATE, profile.getName(), permission.getKey());
    }
}
