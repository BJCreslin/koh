package ru.cbr.koh.panes_storage.panels.permission_migration.information.domain;

import java.io.Serializable;

public record Information(String keyText, String author, String storyNumber,
                          String storyText, boolean shouldWriteAbakFile) implements Serializable {

}
