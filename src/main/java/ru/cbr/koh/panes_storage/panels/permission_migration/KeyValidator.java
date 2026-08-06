package ru.cbr.koh.panes_storage.panels.permission_migration;

import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.KeyCandidate;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class KeyValidator {

    public Map<String, Map<String, List<Permission>>> getConflicts(List<Permission> permissions) {
        Map<String, Map<String, List<Permission>>> result = new LinkedHashMap<>();

        for (Permission permission : permissions) {
            KeyCandidate candidate = permission.getKeyCandidate();
            if (candidate == null || !candidate.hasConflict()) {
                continue;
            }

            String excelKey = candidate.getExcelKey();
            String computedKey = candidate.getComputedKey();

            result.computeIfAbsent(excelKey, k -> new LinkedHashMap<>())
                    .computeIfAbsent(computedKey, k -> new ArrayList<>())
                    .add(permission);
        }

        return result;
    }

    public void applyResolution(Permission permission, String key) {
        KeyCandidate candidate = permission.getKeyCandidate();
        if (candidate == null || key == null) {
            return;
        }

        if (key.equals(candidate.getExcelKey())) {
            candidate.resolve();
        } else if (key.equals(candidate.getComputedKey())) {
            candidate.selectComputed();
        }
    }
}
