package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.base_clases;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class PermissionIdAbstract {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");

    public String create(String textPart, String idTemplate) {
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(FORMATTER);

        return String.format(idTemplate, formattedDateTime, textPart);
    }
}
