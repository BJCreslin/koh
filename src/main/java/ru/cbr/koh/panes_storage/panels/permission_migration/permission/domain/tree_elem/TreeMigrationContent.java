package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.tree_elem;

import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;

import java.util.List;
import java.util.stream.Collectors;

public class TreeMigrationContent {

    private final List<? extends Permission> permissions;

    public TreeMigrationContent(List<? extends Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return permissions.stream()
                .map(TreeMigration::new)
                .map(TreeMigration::toString)
                .collect(Collectors.joining());
    }
}
