package ru.cbr.koh.panes_storage.panels.information.domain;

import java.io.Serializable;

public record Information(String keyText, String author, String storyNumber,
                          String storyText) implements Serializable {

}
