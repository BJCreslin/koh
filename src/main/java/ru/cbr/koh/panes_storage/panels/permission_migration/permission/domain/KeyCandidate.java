package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain;

import java.util.Objects;

public class KeyCandidate {

    private final String excelKey;
    private final String computedKey;
    private String selectedKey;
    private boolean resolved;

    private KeyCandidate(Builder builder) {
        this.excelKey = builder.excelKey;
        this.computedKey = builder.computedKey;
    }

    public static Builder builder() {
        return new Builder();
    }

    public KeyCandidate resolve() {
        if (resolved) {
            return this;
        }
        resolved = true;
        if (Objects.equals(excelKey, computedKey)) {
            selectedKey = excelKey;
        } else {
            selectedKey = excelKey;
        }
        return this;
    }

    public void selectComputed() {
        selectedKey = computedKey;
        resolved = true;
    }

    public boolean isValid() {
        return !excelKey.isBlank() && !computedKey.isBlank();
    }

    public boolean matchesExcel() {
        return Objects.equals(selectedKey, excelKey);
    }

    public boolean matchesComputed() {
        return Objects.equals(selectedKey, computedKey);
    }

    public boolean hasConflict() {
        return !Objects.equals(excelKey, computedKey);
    }

    public String getExcelKey() {
        return excelKey;
    }

    public String getComputedKey() {
        return computedKey;
    }

    public String getSelectedKey() {
        return selectedKey;
    }

    public boolean isResolved() {
        return resolved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyCandidate that = (KeyCandidate) o;
        return Objects.equals(excelKey, that.excelKey)
                && Objects.equals(computedKey, that.computedKey)
                && Objects.equals(selectedKey, that.selectedKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(excelKey, computedKey, selectedKey);
    }

    public static class Builder {
        private String excelKey;
        private String computedKey;

        public Builder excelKey(String excelKey) {
            this.excelKey = excelKey;
            return this;
        }

        public Builder computedKey(String computedKey) {
            this.computedKey = computedKey;
            return this;
        }

        public KeyCandidate build() {
            return new KeyCandidate(this);
        }
    }
}
