package ru.cbr.koh.app.validation;

import org.junit.jupiter.api.Test;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.domain.Information;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.dialog_objects.PermissionDialogObject;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.PermissionType;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.TreeType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MigrationValidatorTest {

    @Test
    void shouldRejectBlankInformation() {
        Information information = new Information("", "", "", "", false, false, true);
        assertThrows(IllegalArgumentException.class, () -> MigrationValidator.validateInformation(information));
    }

    @Test
    void shouldAcceptValidInformation() {
        Information information = new Information("key", "author", "story-1", "story", false, false, true);
        assertDoesNotThrow(() -> MigrationValidator.validateInformation(information));
    }

    @Test
    void shouldRejectInvalidPermissionForm() {
        PermissionDialogObject invalid = new PermissionDialogObject(
                "",
                null,
                null,
                null,
                "",
                "desc",
                List.of(TreeType.KO));

        assertThrows(IllegalArgumentException.class, () -> MigrationValidator.validatePermissionForm(invalid));
    }

    @Test
    void shouldAcceptValidPermissionForm() {
        PermissionDialogObject valid = new PermissionDialogObject(
                "a#b",
                PermissionType.ACTION,
                "GROUP",
                "USER",
                "Name",
                "desc",
                List.of(TreeType.KO));

        assertDoesNotThrow(() -> MigrationValidator.validatePermissionForm(valid));
    }

    @Test
    void shouldRejectEmptyMigrationInput() {
        assertThrows(IllegalArgumentException.class, () -> MigrationValidator.validateMigrationInput(List.of()));
    }
}
