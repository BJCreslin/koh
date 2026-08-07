package ru.cbr.koh.panes_storage.panels.permission_migration.preview;

import ru.cbr.koh.panes_storage.panels.permission_migration.KeyValidator;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.ui.BusinessTheme;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class KeyConflictDialog {

    private static final int COL_EXCEL = 0;
    private static final int COL_COMPUTED = 1;
    private static final int COL_CHOICE = 2;

    private static final String DEFAULT_FONT_FAMILY = "SansSerif";

    private static final Color HIGHLIGHT_COLOR = new Color(0xE8F5E9);

    private KeyConflictDialog() {
    }

    public static boolean show(Window parent, Map<String, Map<String, List<Permission>>> conflicts, String excelFileName) {
        if (conflicts == null || conflicts.isEmpty()) {
            return true;
        }

        JDialog dialog = new JDialog(parent, "Разрешение конфликтов ключей", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel content = new JPanel(new BorderLayout(8, 8));
        content.setBorder(BusinessTheme.pagePadding());

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

        JLabel title = BusinessTheme.createPageTitle("Конфликты ключей secur_elem.key");
        northPanel.add(title);

        JLabel description = new JLabel("<html>Обнаружены расхождения между ключом из Excel и вычисленным ключом по дереву. " +
                "Выберите для каждой строки, какой ключ использовать. Нажмите «Применить» для сохранения выбора.</html>");
        description.setBorder(BorderFactory.createEmptyBorder(4, 0, 8, 0));
        description.setAlignmentX(Component.LEFT_ALIGNMENT);
        northPanel.add(description);

        JPanel bulkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        JLabel bulkLabel = new JLabel("Массовое назначение:");
        bulkLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 4));
        JButton allExcelButton = new JButton("Excel для всех");
        JButton allComputedButton = new JButton("Computed для всех");
        BusinessTheme.styleSecondaryButton(allExcelButton);
        BusinessTheme.styleSecondaryButton(allComputedButton);

        DefaultTableModel model = buildModel(conflicts);

        allExcelButton.addActionListener(e -> setAllChoices(model, "excel"));
        allComputedButton.addActionListener(e -> setAllChoices(model, "computed"));

        JButton exportButton = new JButton("Экспорт...");
        BusinessTheme.styleSecondaryButton(exportButton);
        exportButton.addActionListener(e -> exportConflicts(model, excelFileName));

        bulkPanel.add(bulkLabel);
        bulkPanel.add(allExcelButton);
        bulkPanel.add(allComputedButton);
        bulkPanel.add(exportButton);
        bulkPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        bulkPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        northPanel.add(bulkPanel);

        content.add(northPanel, BorderLayout.NORTH);
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
        table.setRowHeight(32);
        table.getTableHeader().setReorderingAllowed(false);

        configureKeyColumn(table, COL_EXCEL, "#c62828");
        configureKeyColumn(table, COL_COMPUTED, "#1565c0");
        configureChoiceColumn(table);

        table.getColumnModel().getColumn(COL_EXCEL).setPreferredWidth(380);
        table.getColumnModel().getColumn(COL_COMPUTED).setPreferredWidth(380);
        table.getColumnModel().getColumn(COL_CHOICE).setPreferredWidth(90);

        addCopyActions(table, model);

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
        dialog.setSize(950, 600);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return accepted[0];
    }

    private static void setAllChoices(DefaultTableModel model, String choice) {
        for (int row = 0; row < model.getRowCount(); row++) {
            model.setValueAt(choice, row, COL_CHOICE);
        }
    }

    private static void addCopyActions(JTable table, DefaultTableModel model) {
        InputMap inputMap = table.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap actionMap = table.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK), "copyCell");
        actionMap.put("copyCell", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                if (row < 0 || col < 0) return;
                if (col == COL_CHOICE) return;
                String value = model.getValueAt(row, col).toString();
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(value), null);
            }
        });

        JPopupMenu popup = new JPopupMenu();
        JMenuItem copyItem = new JMenuItem("Копировать значение");
        copyItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            int col = table.getSelectedColumn();
            if (row < 0 || col < 0) return;
            if (col == COL_CHOICE) return;
            String value = model.getValueAt(row, col).toString();
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(value), null);
        });
        popup.add(copyItem);

        table.setComponentPopupMenu(popup);
    }

    private static void exportConflicts(DefaultTableModel model, String excelFileName) {
        String baseName = excelFileName != null ? excelFileName : "unknown";
        if (baseName.endsWith(".xlsx")) {
            baseName = baseName.substring(0, baseName.length() - 5);
        } else if (baseName.endsWith(".xls")) {
            baseName = baseName.substring(0, baseName.length() - 4);
        }
        String fileName = "AbacProfiles_" + baseName + ".txt";

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Экспорт конфликтов ключей");
        chooser.setSelectedFile(new java.io.File(fileName));

        int result = chooser.showSaveDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) return;

        File file = chooser.getSelectedFile();
        if (!file.getName().endsWith(".txt")) {
            file = new java.io.File(file.getAbsolutePath() + ".txt");
        }

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"))) {
            writer.write("Excel ключ\tВычисленный ключ\tВыбор\n");
            for (int row = 0; row < model.getRowCount(); row++) {
                String excelKey = model.getValueAt(row, COL_EXCEL).toString();
                String computedKey = model.getValueAt(row, COL_COMPUTED).toString();
                String choice = model.getValueAt(row, COL_CHOICE).toString();
                writer.write(excelKey + "\t" + computedKey + "\t" + choice + "\n");
            }
            JOptionPane.showMessageDialog(null,
                    "Конфликты сохранены в файл:\n" + file.getAbsolutePath(),
                    "Экспорт завершён",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Не удалось сохранить файл: " + ex.getMessage(),
                    "Ошибка экспорта",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static DefaultTableModel buildModel(Map<String, Map<String, List<Permission>>> conflicts) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Excel ключ", "Вычисленный ключ", "Выбор"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == COL_CHOICE;
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                super.setValueAt(aValue, row, column);
                if (column == COL_CHOICE) {
                    fireTableRowsUpdated(row, row);
                }
            }
        };

        for (Map.Entry<String, Map<String, List<Permission>>> excelEntry : conflicts.entrySet()) {
            for (Map.Entry<String, List<Permission>> computedEntry : excelEntry.getValue().entrySet()) {
                model.addRow(new Object[]{
                        excelEntry.getKey(),
                        computedEntry.getKey(),
                        "excel"
                });
            }
        }
        return model;
    }

    private static void configureKeyColumn(JTable table, int columnIndex, String diffColor) {
        TableColumn column = table.getColumnModel().getColumn(columnIndex);
        column.setCellRenderer(new KeyDiffRenderer(columnIndex, diffColor));
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

    private static final class KeyDiffRenderer implements javax.swing.table.TableCellRenderer {
        private final int otherColumn;
        private final String diffColor;

        KeyDiffRenderer(int otherColumn, String diffColor) {
            this.otherColumn = otherColumn;
            this.diffColor = diffColor;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            String myKey = value == null ? "" : value.toString();
            Object otherValue = table.getValueAt(row, otherColumn);
            String otherKey = otherValue == null ? "" : otherValue.toString();
            Object choiceValue = table.getValueAt(row, COL_CHOICE);
            String choice = choiceValue == null ? "" : choiceValue.toString();

            boolean isSelectedByChoice =
                    (column == COL_EXCEL && "excel".equals(choice))
                            || (column == COL_COMPUTED && "computed".equals(choice));

            JTextPane textPane = new JTextPane();
            textPane.setContentType("text/html");
            textPane.setText(renderDiff(myKey, otherKey));
            textPane.setOpaque(true);
            textPane.setEditable(false);
            textPane.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

            Color background;
            Color foreground;
            if (isSelected) {
                background = table.getSelectionBackground();
                foreground = table.getSelectionForeground();
            } else if (isSelectedByChoice) {
                background = HIGHLIGHT_COLOR;
                foreground = table.getForeground();
            } else {
                background = table.getBackground();
                foreground = table.getForeground();
            }
            textPane.setBackground(background);
            textPane.setForeground(foreground);

            return textPane;
        }

        private String renderDiff(String myKey, String otherKey) {
            String[] mine = myKey.split("#", -1);
            String[] others = otherKey.split("#", -1);
            StringBuilder sb = new StringBuilder("<html><body style='font-family: sans-serif; margin: 0; padding: 0;'>");
            for (int i = 0; i < mine.length; i++) {
                if (i > 0) {
                    sb.append("<font color='#9e9e9e'>#</font>");
                }
                boolean matches = i < others.length && mine[i].equals(others[i]);
                if (matches) {
                    sb.append(escape(mine[i]));
                } else {
                    sb.append("<font color='").append(diffColor).append("'><b>")
                            .append(escape(mine[i]))
                            .append("</b></font>");
                }
            }
            if (mine.length < others.length) {
                for (int i = mine.length; i < others.length; i++) {
                    sb.append("<font color='#9e9e9e'>#</font>");
                    sb.append("<font color='").append(diffColor).append("'><i>")
                            .append(escape(others[i]))
                            .append("</i></font>");
                }
            }
            sb.append("</body></html>");
            return sb.toString();
        }

        private static String escape(String text) {
            return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
        }
    }
}
