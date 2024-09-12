package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.tree_elem;

import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.TreeType;

public class TreeMigration {

    private final Permission permission;

    private static final String TEMPLATE = """
                    \n
                    <insert tableName="secur_elem_tree">
                        <column name="id" valueSequenceNext = "app_seq"/>
                        <column name="tree_list_id" value = "%s"/>
                        <column name="secur_elem_id"
                                valueComputed="(SELECT id FROM secur_elem WHERE key = '%s')"/>
                        <column name="description" value = "%s"/>
                    </insert>
            """;


    public TreeMigration(Permission permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (TreeType treeType : permission.getTreeType()) {
            sb.append(String.format(TEMPLATE, treeType.getId(), permission.getKey(), permission.getDescription()));
        }
        return sb.toString();
    }

}
