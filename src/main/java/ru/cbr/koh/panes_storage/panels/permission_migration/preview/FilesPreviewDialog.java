package ru.cbr.koh.panes_storage.panels.permission_migration.preview;

import ru.cbr.koh.panes_storage.panels.permission_migration.output.GeneratedFile;
import ru.cbr.koh.ui.BusinessTheme;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public final class FilesPreviewDialog {

    private FilesPreviewDialog() {
    }

    public static boolean show(Window parent, List<GeneratedFile> files) {
        if (files == null || files.isEmpty()) {
            return false;
        }

        JDialog dialog = new JDialog(parent, "Preview generated files", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel content = new JPanel(new BorderLayout(8, 8));
        content.setBorder(BusinessTheme.pagePadding());

        JLabel title = BusinessTheme.createPageTitle("Preview before save");
        content.add(title, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        for (GeneratedFile file : files) {
            JTextArea textArea = new JTextArea(file.content());
            textArea.setEditable(false);
            textArea.setCaretPosition(0);
            textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

            JScrollPane scrollPane = new JScrollPane(textArea);
            tabbedPane.addTab(file.path().getFileName().toString(), scrollPane);
            tabbedPane.setToolTipTextAt(tabbedPane.getTabCount() - 1, file.displayName());
        }

        content.add(tabbedPane, BorderLayout.CENTER);
        content.add(new JLabel("Проверьте содержимое и нажмите Save files для записи на диск."), BorderLayout.SOUTH);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        JButton saveButton = new JButton("Save files");
        JButton cancelButton = new JButton("Cancel");
        BusinessTheme.stylePrimaryButton(saveButton);
        BusinessTheme.styleSecondaryButton(cancelButton);

        final boolean[] accepted = {false};

        saveButton.addActionListener(e -> {
            accepted[0] = true;
            dialog.dispose();
        });
        cancelButton.addActionListener(e -> dialog.dispose());

        actions.add(cancelButton);
        actions.add(saveButton);

        dialog.add(content, BorderLayout.CENTER);
        dialog.add(actions, BorderLayout.SOUTH);
        dialog.setSize(1000, 700);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return accepted[0];
    }
}
