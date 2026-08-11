package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyCandidateTest {

    @Test
    void buildWithMatchingKeys() {
        KeyCandidate candidate = KeyCandidate.builder()
                .excelKey("A#B")
                .computedKey("A#B")
                .build()
                .resolve();

        assertTrue(candidate.isValid());
        assertTrue(candidate.matchesExcel());
        assertTrue(candidate.matchesComputed());
        assertFalse(candidate.hasConflict());
        assertEquals("A#B", candidate.getSelectedKey());
    }

    @Test
    void buildWithDifferingKeys() {
        KeyCandidate candidate = KeyCandidate.builder()
                .excelKey("A#B")
                .computedKey("X#Y")
                .build()
                .resolve();

        assertTrue(candidate.isValid());
        assertFalse(candidate.matchesComputed());
        assertTrue(candidate.hasConflict());
        assertEquals("A#B", candidate.getSelectedKey());
    }

    @Test
    void resolveWithComputedKeySelection() {
        KeyCandidate candidate = KeyCandidate.builder()
                .excelKey("A#B")
                .computedKey("X#Y")
                .build();

        candidate.selectComputed();
        assertEquals("X#Y", candidate.getSelectedKey());
        assertTrue(candidate.matchesComputed());
        assertFalse(candidate.matchesExcel());
    }

    @Test
    void blankExcelKey() {
        KeyCandidate candidate = KeyCandidate.builder()
                .excelKey("")
                .computedKey("A#B")
                .build()
                .resolve();

        assertFalse(candidate.isValid());
    }

    @Test
    void blankComputedKey() {
        KeyCandidate candidate = KeyCandidate.builder()
                .excelKey("A#B")
                .computedKey("")
                .build()
                .resolve();

        assertFalse(candidate.isValid());
    }

    @Test
    void bothBlank() {
        KeyCandidate candidate = KeyCandidate.builder()
                .excelKey("")
                .computedKey("")
                .build()
                .resolve();

        assertFalse(candidate.isValid());
    }

    @Test
    void equalityWithNull() {
        KeyCandidate c1 = KeyCandidate.builder()
                .excelKey("A#B")
                .computedKey("A#B")
                .build()
                .resolve();
        KeyCandidate c2 = KeyCandidate.builder()
                .excelKey("A#B")
                .computedKey("A#B")
                .build()
                .resolve();

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void notEqualWithDifferentKeys() {
        KeyCandidate c1 = KeyCandidate.builder()
                .excelKey("A#B")
                .computedKey("A#B")
                .build()
                .resolve();
        KeyCandidate c2 = KeyCandidate.builder()
                .excelKey("X#Y")
                .computedKey("X#Y")
                .build()
                .resolve();

        assertNotEquals(c1, c2);
    }

    @Test
    void notResolveReturnsNullSelectedKey() {
        KeyCandidate candidate = KeyCandidate.builder()
                .excelKey("A#B")
                .computedKey("X#Y")
                .build();

        assertNull(candidate.getSelectedKey());
    }
}
