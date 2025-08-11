package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.secure_elem;


import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;

public class SecureElemMigration {

    private static final String CHANGE_TEMPLATE = """
                        <insert tableName="secur_elem">
                            <column name="id" valueSequenceNext="app_seq"/>
                            <column name="parent_id" valueComputed="(SELECT id FROM secur_elem WHERE key = '%s')"/>
                            <column name="type" value="%s"/>
                            <column name="name" value='%s'/>
                            <column name="rel_key" value="%s"/>
                            <column name="key" value="%s"/>
                            <column name="abac_perm_pres_attr_code"
                                    value="%s"/>
                            <column name="abac_perm_pres_group_action" value="%s"/>
                            %s
                            <column name="order_no"  value="%s"/>
                        </insert>
                       \s
           \s""";

    private static final String CHANGE_TEMPLATE_KO = """
                        <insert tableName="secur_elem">
                           <column name="id" valueSequenceNext="app_seq"/>
                           <column name="parent_id"
                                    valueComputed="(SELECT id FROM secur_elem WHERE key = '%s')"/>
                           <column name="type" value="%s"/>
                           <column name="name" value='%s'/>
                           <column name="rel_key" value="%s"/>
                           <column name="key" value="%s"/>
                           <column name="abac_perm_pres_ko_attr_code" value="%s"/>
                           <column name="securable_by_ko" valueNumeric="1"/>
                           <column name="abac_perm_pres_group_action" value="%s"/>
                           <column name="abac_perm_pres_user_action" value="%s"/>
                           %s
                           <column name="order_no"  value="%d"/>
                        </insert>
                       \s
           \s""";

    private final Permission permission;

    public SecureElemMigration(Permission permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        if (permission.isKoPermission()) {
            return String.format(CHANGE_TEMPLATE_KO,
                    permission.getParent(),
                    permission.getType().name().trim(),
                    permission.getName().trim(),
                    permission.getRelKey().trim(),
                    permission.getKey().trim(),
                    permission.getAbacPermPresAttrCode().trim(),
                    permission.getAbacPermPresGroupAction().trim(),
                    permission.getAbacPermPresUserAction().trim(),
                    getDescription(permission.getDescription() != null ? permission.getDescription().trim() : permission.getDescription()),
                    permission.getOrder()
            );
        } else {
            return String.format(CHANGE_TEMPLATE,
                    permission.getParent(),
                    permission.getType().name(),
                    permission.getName(),
                    permission.getRelKey(),
                    permission.getKey(),
                    permission.getAbacPermPresAttrCode(),
                    permission.getAbacPermPresGroupAction(),
                    getDescription(permission.getDescription()),
                    permission.getOrder()
            );
        }
    }

    private String getDescription(String description) {
        if (description != null && !description.isBlank()) {
            return String.format(" <column name=\"description\" value = '%s'/>", description);
        }
        return "";
    }
}
