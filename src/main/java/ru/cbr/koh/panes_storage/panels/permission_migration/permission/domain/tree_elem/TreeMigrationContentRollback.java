package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.tree_elem;

import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;

import java.util.List;
import java.util.stream.Collectors;

public class TreeMigrationContentRollback {

    private static final String TEMPLATE = """
            
             <delete tableName="secur_elem_tree">
                            <where>
                                secur_elem_id IN (
                                SELECT id FROM secur_elem WHERE key IN
                                (%s) )
                            </where>
                        </delete>
            """;

    private final List<Permission> permissions;

    public TreeMigrationContentRollback(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        String keys = permissions.stream().map(Permission::getKey).map(it -> "'" + it + "'").collect(Collectors.joining(
                ",  "));
        return String.format(TEMPLATE, keys);
    }
}
