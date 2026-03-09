package ru.cbr.koh.app.validation;

import ru.cbr.koh.panes_storage.panels.permission_migration.information.domain.Information;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.dialog_objects.PermissionDialogObject;

import java.util.ArrayList;
import java.util.List;

public final class MigrationValidator {

    private MigrationValidator() {
    }

    public static void validateInformation(Information information) {
        List<String> errors = new ArrayList<>();

        if (isBlank(information.keyText())) {
            errors.add("Поле Key prefix обязательно");
        }
        if (isBlank(information.author())) {
            errors.add("Поле Author обязательно");
        }
        if (isBlank(information.storyNumber())) {
            errors.add("Поле Story number обязательно");
        }
        if (isBlank(information.storyText())) {
            errors.add("Поле Story name обязательно");
        }

        throwIfAny(errors);
    }

    public static void validatePermissionForm(PermissionDialogObject permission) {
        List<String> errors = new ArrayList<>();

        if (isBlank(permission.getKey())) {
            errors.add("Поле Key обязательно");
        }
        if (permission.getPermissionType() == null) {
            errors.add("Поле Permission Type обязательно");
        }
        if (isBlank(permission.getName())) {
            errors.add("Поле Name обязательно");
        }

        throwIfAny(errors);
    }

    public static void validateMigrationInput(List<?> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            throw new IllegalArgumentException("Нет данных для формирования миграции");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private static void throwIfAny(List<String> errors) {
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join("\n", errors));
        }
    }
}
