# Plan: In-Progress Key Validation with UI Dialog

## Goal
Add validation of `secur_elem.key` by comparing Excel keys (`p` column) with computed keys before generating migration files — with a modal dialog for conflicts.

## Execution Plan

All steps are sequential within a single dev session. TDD applies but commit/merge decisions are deferred to the end.

### Step 1 — `KeyCandidate` Data Class
Create `KeyCandidate` in `ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain`:
- Fields: `excelKey`, `computedKey`, `selectedKey` (all non-blank)
- `isValid()`, `matchesExcel()`, `matchesComputed()`
- `toBuilder()` pattern for updates
- Block: `keyBlank`, `keysDiffer`

**Tests:**
- All valid / invalid combinations
- Equality and hashcode with nulls

### Step 2 — `Permission` Key Migration
- Add `keyCandidate: Optional<KeyCandidate>`
- Add `getKey()`: uses `selectedKey` from candidate, falls back to existing `key`
- Add `hasKeyConflict()`: true when candidate is present and keys differ
- Add `resolveKey(String key)`: updates `selectedKey`, clears conflict
- `setKey()` and `getKey()` now delegate through `KeyCandidate`

**Tests:**
- Legacy path: existing `key` works when no candidate is present
- Candidate path: `getKey()` returns `selectedKey`
- `hasKeyConflict()` flags correctly
- `resolveKey()` clears the flag

### Step 3 — `FileReader` Integration
`readPermission()` creates `KeyCandidate`:
```java
final String excelKey = StringUtils.trimToEmpty(cellValue(sheet, rowIndex, KEY_COLUMN)).replace((char) 160, ' ');
final String computedKey = keysStack.buildKey();
final KeyCandidate candidate = KeyCandidate.builder()
    .excelKey(excelKey)
    .computedKey(computedKey)
    .build()
    .resolve();
p.withKeyCandidate(candidate);
```

**Tests:**
- Excel matches computed → `selectedKey` = both, valid true
- Excel differs from computed → `selectedKey` = excel, valid false

### Step 4 — `KeyValidator` Class
`KeyValidator` in `ru.cbr.koh.panes_storage.panels.permission_migration`:
- `validate(List<Permission>) → Map<Permission, KeyCandidate>` (entries where keys differ)
- `getConflicts(List<Permission>) → Map<String, List<Permission>>` (grouped by `excelKey → computedKey`)

**Tests:**
- No conflicts → empty result
- All conflicts → correct grouping

### Step 5 — `KeyConflictDialog`
Modal JDialog in `ru.cbr.koh.panes_storage.panels`:
- Groups by `excelKey → computedKey`
- Table: Excel Key | Computed Key | Selected Key | Rows
- Checkboxes for selecting computed keys
- Resolves all affected `Permission` objects via `resolveKey()`
- Returns boolean (OK / Cancel)
- Uses `ModalDialog` builder from context

### Step 6 — `ExcelInputPanel` Integration
```java
// After fileReader.read() in migrationPreview(), before buildSecureElemMigrations()
if (preview.hasKeyConflicts()) {
    KeyConflictDialog dialog = new KeyConflictDialog(frame, preview.getConflicts());
    if (dialog.show() == CANCEL) {
        throw new UserCancelledException();
    }
}
```

### Step 7 — End-to-End Test
Test double Excel file with known conflicting keys, verify conflict count, run resolution, assert generated XML keys.

---

## Dependencies
- Step 1 is prerequisite for Step 2
- Step 2 is prerequisite for Step 3
- Step 3 is prerequisite for Step 4
- Steps 5 and 6 may be parallel after Step 4
- Step 7 depends on all previous steps
