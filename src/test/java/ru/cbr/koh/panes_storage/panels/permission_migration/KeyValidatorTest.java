package ru.cbr.koh.panes_storage.panels.permission_migration;

import org.junit.jupiter.api.Test;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.KeyCandidate;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.PermissionType;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.TreeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class KeyValidatorTest {

    private Permission permissionWithCandidate(String excelKey, String computedKey) {
        KeyCandidate candidate = KeyCandidate.builder()
                .excelKey(excelKey)
                .computedKey(computedKey)
                .build()
                .resolve();
        return new Permission(
                excelKey,
                PermissionType.ACTION,
                "",
                "",
                "name",
                List.of(),
                "",
                List.of(TreeType.KO),
                null
        ).withKeyCandidate(candidate);
    }

    @Test
    void emptyListReturnsEmptyConflicts() {
        KeyValidator validator = new KeyValidator();
        Map<String, Map<String, List<Permission>>> conflicts = validator.getConflicts(List.of());
        assertTrue(conflicts.isEmpty());
    }

    @Test
    void noConflictsWhenKeysMatch() {
        KeyValidator validator = new KeyValidator();
        Permission p = permissionWithCandidate("A#B", "A#B");
        Map<String, Map<String, List<Permission>>> conflicts = validator.getConflicts(List.of(p));
        assertTrue(conflicts.isEmpty());
    }

    @Test
    void groupsByExcelAndComputedKey() {
        KeyValidator validator = new KeyValidator();
        Permission p1 = permissionWithCandidate("A#B", "X#Y");
        Permission p2 = permissionWithCandidate("A#B", "X#Y");
        Permission p3 = permissionWithCandidate("A#B", "Z#W");

        Map<String, Map<String, List<Permission>>> conflicts = validator.getConflicts(List.of(p1, p2, p3));

        assertEquals(1, conflicts.size());
        assertTrue(conflicts.containsKey("A#B"));
        Map<String, List<Permission>> grouped = conflicts.get("A#B");
        assertEquals(2, grouped.size());
        assertEquals(2, grouped.get("X#Y").size());
        assertEquals(1, grouped.get("Z#W").size());
    }

    @Test
    void groupsDifferentExcelKeys() {
        KeyValidator validator = new KeyValidator();
        Permission p1 = permissionWithCandidate("A#B", "X#Y");
        Permission p2 = permissionWithCandidate("C#D", "P#Q");

        Map<String, Map<String, List<Permission>>> conflicts = validator.getConflicts(List.of(p1, p2));

        assertEquals(2, conflicts.size());
        assertTrue(conflicts.containsKey("A#B"));
        assertTrue(conflicts.containsKey("C#D"));
    }

    @Test
    void permissionsWithoutCandidateAreIgnored() {
        KeyValidator validator = new KeyValidator();
        Permission p1 = new Permission(
                "X#Y",
                PermissionType.ACTION,
                "",
                "",
                "name",
                List.of(),
                "",
                List.of(TreeType.KO),
                null
        );
        Permission p2 = permissionWithCandidate("A#B", "X#Y");

        Map<String, Map<String, List<Permission>>> conflicts = validator.getConflicts(List.of(p1, p2));

        assertEquals(1, conflicts.size());
    }

    @Test
    void resolveKeyReplacesExcelWithComputed() {
        KeyValidator validator = new KeyValidator();
        Permission p = permissionWithCandidate("A#B", "X#Y");

        validator.applyResolution(p, "X#Y");

        assertEquals("X#Y", p.getKeyCandidate().getSelectedKey());
        assertTrue(p.getKeyCandidate().matchesComputed());
    }

    @Test
    void resolveKeyToExcel() {
        KeyValidator validator = new KeyValidator();
        Permission p = permissionWithCandidate("A#B", "X#Y");

        validator.applyResolution(p, "A#B");

        assertEquals("A#B", p.getKeyCandidate().getSelectedKey());
        assertTrue(p.getKeyCandidate().matchesExcel());
    }

    @Test
    void getConflictsReturnsEmptyForNullCandidates() {
        KeyValidator validator = new KeyValidator();
        Permission p = new Permission(
                "X#Y",
                PermissionType.ACTION,
                "",
                "",
                "name",
                List.of(),
                "",
                List.of(TreeType.KO),
                null
        );
        Map<String, Map<String, List<Permission>>> conflicts = validator.getConflicts(new ArrayList<>(List.of(p)));
        assertTrue(conflicts.isEmpty());
    }
}
