package ru.cbr.koh.panes_storage.panels.permission_migration.preview;

import ru.cbr.koh.panes_storage.panels.permission_migration.KeyValidator;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.ui.BusinessTheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class KeyConflictDialog {

    private static final int COL_EXCEL = 0;
    private static final int COL_COMPUTED = 1;
    private static final int COL_ROWS = 2;
    private static final int COL_CHOICE = 3;

    private KeyConflictDialog() {
    }

    public static boolean show(Window parent, Map<String, Map<String, List<Permission>>> conflicts) {
        if (conflicts == null || conflicts.isEmpty()) {
            return true;
        }

        JDialog dialog = new JDialog(parent, "Resolve key conflicts", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel content = new JPanel(new BorderLayout(8, 8));
        content.setBorder(BusinessTheme.pagePadding());

        JLabel title = BusinessTheme.createPageTitle("Конфликты ключей secur_elem.key");
        content.add(title, BorderLayout.NORTH);

        JLabel description = new JLabel("<html>Обнаружены расхождения между ключом из Excel и вычисленным ключом по дереву. " +
                "Выберите для каждой строки, какой ключ использовать. Нажмите «Применить» для сохранения выбора.</html>");
        description.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        content.add(description, BorderLayout.NORTH);

        DefaultTableModel model = buildModel(conflicts);
        JTable table = new JTable(model) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == COL_CHOICE ? String.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == COL_CHOICE;
            }
        };
        table.setRowHeight(24);
        table.getTableHeader().setReorderingAllowed(false);

        configureChoiceColumn(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 400));
        content.add(scrollPane, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        JButton applyButton = new JButton("Применить");
        JButton cancelButton = new JButton("Отмена");
        BusinessTheme.stylePrimaryButton(applyButton);
        BusinessTheme.styleSecondaryButton(cancelButton);

        final boolean[] accepted = {false};

        applyButton.addActionListener(e -> {
            if (applyResolutions(model, conflicts)) {
                accepted[0] = true;
                dialog.dispose();
            }
        });
        cancelButton.addActionListener(e -> dialog.dispose());

        actions.add(cancelButton);
        actions.add(applyButton);

        dialog.add(content, BorderLayout.CENTER);
        dialog.add(actions, BorderLayout.SOUTH);
        dialog.setSize(950, 550);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return accepted[0];
    }

    private static DefaultTableModel buildModel(Map<String, Map<String, List<Permission>>> conflicts) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Excel ключ", "Вычисленный ключ", "Затронуто строк", "Выбор"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == COL_CHOICE;
            }
        };

        for (Map.Entry<String, Map<String, List<Permission>>> excelEntry : conflicts.entrySet()) {
            for (Map.Entry<String, List<Permission>> computedEntry : excelEntry.getValue().entrySet()) {
                model.addRow(new Object[]{
                        excelEntry.getKey(),
                        computedEntry.getKey(),
                        computedEntry.getValue().size(),
                        "excel"
                });
            }
        }
        return model;
    }

    private static void configureChoiceColumn(JTable table) {
        TableColumn column = table.getColumnModel().getColumn(COL_CHOICE);
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"excel", "computed"});
        comboBox.setEditable(false);
        DefaultCellEditor editor = new DefaultCellEditor(comboBox);
        editor.setClickCountToStart(1);
        column.setCellEditor(editor);
    }

    private static boolean applyResolutions(DefaultTableModel model,
                                            Map<String, Map<String, List<Permission>>> conflicts) {
        KeyValidator validator = new KeyValidator();
        List<String> unresolved = new ArrayList<>();

        for (int row = 0; row < model.getRowCount(); row++) {
            String excelKey = (String) model.getValueAt(row, COL_EXCEL);
            String computedKey = (String) model.getValueAt(row, COL_COMPUTED);
            String choice = (String) model.getValueAt(row, COL_CHOICE);

            if (choice == null || choice.isBlank()) {
                unresolved.add("Строка " + (row + 1) + ": выбор не сделан");
                continue;
            }

            List<Permission> permissions = findPermissions(conflicts, excelKey, computedKey);
            if (permissions == null) {
                continue;
            }

            String resolved = "computed".equals(choice) ? computedKey : excelKey;
            for (Permission permission : permissions) {
                validator.applyResolution(permission, resolved);
            }
        }

        if (!unresolved.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    String.join("\n", unresolved),
                    "Не все конфликты разрешены",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private static List<Permission> findPermissions(Map<String, Map<String, List<Permission>>> conflicts,
                                                    String excelKey,
                                                    String computedKey) {
        Map<String, List<Permission>> computedMap = conflicts.get(excelKey);
        if (computedMap == null) {
            return null;
        }
        return computedMap.get(computedKey);
    }
}
