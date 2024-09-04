package ru.cbr.koh.panes_storage;

import javax.swing.*;

public interface PaneInterface {

    String getTitle();

    JComponent createPanel(JFrame frame);
}
