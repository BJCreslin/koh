package ru.cbr.koh.panes_storage.panels.application_logs;

import org.springframework.data.domain.Page;
import ru.cbr.koh.app.AppContext;
import ru.cbr.koh.logs.domain.ErrorGroupEntity;
import ru.cbr.koh.logs.domain.LogEventEntity;
import ru.cbr.koh.logs.domain.LogLevel;
import ru.cbr.koh.logs.domain.SchedulerPresetEntity;
import ru.cbr.koh.logs.service.ApplicationLogFacade;
import ru.cbr.koh.logs.service.LogIngestionResult;
import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.ui.BusinessTheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class ApplicationLogsPanel implements PaneInterface {

    private final AppContext appContext;
    private final ApplicationLogFacade facade;

    private DefaultTableModel eventsModel;
    private DefaultTableModel groupsModel;
    private DefaultTableModel schedulerModel;
    private DefaultTableModel presetsModel;
    private JComboBox<String> levelFilter;
    private JCheckBox attentionFilter;
    private JTextField fromDateField;
    private JTextField toDateField;
    private JTextField schedulerLoggerField;
    private JTextField schedulerExecutorField;
    private PaginationControls eventsPagination;
    private PaginationControls schedulerPagination;

    public ApplicationLogsPanel(AppContext appContext) {
        this.appContext = appContext;
        this.facade = appContext.getApplicationLogFacade();
    }

    @Override
    public String getTitle() {
        return "Просмотр логов приложения";
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BusinessTheme.pagePadding());

        panel.add(createToolbar(frame), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Ошибки и WARN", createEventsPanel(frame));
        tabs.addTab("Группы", createGroupsPanel(frame));
        tabs.addTab("Scheduler events", createSchedulerPanel());
        tabs.addTab("Пресеты", createPresetsPanel());
        panel.add(tabs, BorderLayout.CENTER);

        refreshAll();
        return panel;
    }

    private JComponent createToolbar(JFrame frame) {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        toolbar.setOpaque(false);

        JButton downloadButton = new JButton("Скачать и разобрать логи");
        JButton localButton = new JButton("Разобрать локальный dossier-ko.log");
        JButton cleanupButton = new JButton("Удалить старше даты");
        JButton llmButton = new JButton("Найти решение через LLM");

        BusinessTheme.stylePrimaryButton(downloadButton);
        BusinessTheme.styleSecondaryButton(localButton);
        BusinessTheme.styleSecondaryButton(cleanupButton);
        BusinessTheme.styleSecondaryButton(llmButton);

        downloadButton.addActionListener(e -> runDownloadAndIngest(frame));
        localButton.addActionListener(e -> ingestLocalLog(frame));
        cleanupButton.addActionListener(e -> cleanupBefore(frame));
        llmButton.addActionListener(e -> JOptionPane.showMessageDialog(frame,
                facade.llmStub(selectedGroupId()),
                "LLM",
                JOptionPane.INFORMATION_MESSAGE));

        toolbar.add(downloadButton);
        toolbar.add(localButton);
        toolbar.add(cleanupButton);
        toolbar.add(llmButton);
        return toolbar;
    }

    private JComponent createEventsPanel(JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JPanel header = new JPanel(new BorderLayout(4, 4));
        header.setOpaque(false);
        header.add(createFilters(), BorderLayout.NORTH);
        eventsPagination = new PaginationControls(this::refreshEvents);
        header.add(eventsPagination, BorderLayout.SOUTH);
        panel.add(header, BorderLayout.NORTH);

        eventsModel = new DefaultTableModel(
                new Object[]{"id", "time", "level", "logger", "executor", "attention", "message"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(eventsModel);
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int modelRow = table.convertRowIndexToModel(table.getSelectedRow());
                Long id = (Long) eventsModel.getValueAt(modelRow, 0);
                if (table.getSelectedColumn() >= 0 && table.convertColumnIndexToModel(table.getSelectedColumn()) == 6) {
                    showEventDetails(frame, id);
                }
            }
        });
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JComponent createFilters() {
        JPanel filters = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        filters.setOpaque(false);

        levelFilter = new JComboBox<>(new String[]{"ALL", "ERROR", "WARN"});
        attentionFilter = new JCheckBox("Attention");
        fromDateField = new JTextField(10);
        toDateField = new JTextField(10);
        JButton applyButton = new JButton("Применить");
        BusinessTheme.styleSecondaryButton(applyButton);
        applyButton.addActionListener(e -> {
            eventsPagination.reset();
            refreshEvents();
        });

        filters.add(new JLabel("Level"));
        filters.add(levelFilter);
        filters.add(new JLabel("From yyyy-MM-dd"));
        filters.add(fromDateField);
        filters.add(new JLabel("To yyyy-MM-dd"));
        filters.add(toDateField);
        filters.add(attentionFilter);
        filters.add(applyButton);
        return filters;
    }

    private JComponent createGroupsPanel(JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        actions.setOpaque(false);

        JButton attentionButton = new JButton("Переключить Attention");
        JButton refreshButton = new JButton("Обновить");
        BusinessTheme.styleSecondaryButton(attentionButton);
        BusinessTheme.styleSecondaryButton(refreshButton);
        attentionButton.addActionListener(e -> toggleSelectedGroupAttention(frame));
        refreshButton.addActionListener(e -> refreshGroups());
        actions.add(attentionButton);
        actions.add(refreshButton);

        groupsModel = new DefaultTableModel(
                new Object[]{"id", "count", "first", "last", "previous", "attention", "exception", "source", "message"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(groupsModel);
        table.setAutoCreateRowSorter(true);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                int viewRow = table.rowAtPoint(event.getPoint());
                if (event.getClickCount() == 2 && viewRow >= 0) {
                    int modelRow = table.convertRowIndexToModel(viewRow);
                    Long groupId = (Long) groupsModel.getValueAt(modelRow, 0);
                    showGroupMessage(frame, groupId);
                }
            }
        });

        panel.add(actions, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JComponent createSchedulerPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JPanel filters = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        filters.setOpaque(false);

        schedulerLoggerField = new JTextField(14);
        schedulerExecutorField = new JTextField(20);
        JButton applyButton = new JButton("Применить");
        BusinessTheme.styleSecondaryButton(applyButton);
        applyButton.addActionListener(e -> {
            schedulerPagination.reset();
            refreshSchedulerEvents();
        });

        filters.add(new JLabel("Class"));
        filters.add(schedulerLoggerField);
        filters.add(new JLabel("Executor"));
        filters.add(schedulerExecutorField);
        filters.add(applyButton);

        schedulerModel = new DefaultTableModel(new Object[]{"id", "time", "class", "executor", "message"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(schedulerModel);
        table.setAutoCreateRowSorter(true);

        JPanel header = new JPanel(new BorderLayout(4, 4));
        header.setOpaque(false);
        header.add(filters, BorderLayout.NORTH);
        schedulerPagination = new PaginationControls(this::refreshSchedulerEvents);
        header.add(schedulerPagination, BorderLayout.SOUTH);

        panel.add(header, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JComponent createPresetsPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        actions.setOpaque(false);

        JButton addButton = new JButton("Добавить/редактировать");
        JButton deleteButton = new JButton("Удалить");
        JButton applyButton = new JButton("Применить к Scheduler view");
        BusinessTheme.styleSecondaryButton(addButton);
        BusinessTheme.styleSecondaryButton(deleteButton);
        BusinessTheme.styleSecondaryButton(applyButton);
        addButton.addActionListener(e -> savePresetDialog());
        deleteButton.addActionListener(e -> deleteSelectedPreset());
        applyButton.addActionListener(e -> applySelectedPreset());

        actions.add(addButton);
        actions.add(deleteButton);
        actions.add(applyButton);

        presetsModel = new DefaultTableModel(new Object[]{"id", "name", "class", "executor", "message"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(presetsModel);
        table.setAutoCreateRowSorter(true);

        panel.add(actions, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private void runDownloadAndIngest(JFrame frame) {
        char[] password = requestCloudPassword(frame);
        if (password.length == 0) {
            return;
        }
        appContext.getTaskRunner().runWithProgress(
                frame,
                "Logs",
                "Скачиваем и разбираем последний zpe-all-logs.zip...",
                () -> {
                    try {
                        LogIngestionResult result = facade.downloadAndIngestLatest(String.valueOf(password));
                        SwingUtilities.invokeLater(() -> showIngestionResult(frame, result));
                    } finally {
                        java.util.Arrays.fill(password, '\0');
                    }
                },
                this::refreshAll,
                "Не удалось скачать или разобрать логи");
    }

    private char[] requestCloudPassword(JFrame frame) {
        JPasswordField passwordField = new JPasswordField(24);
        JPanel panel = new JPanel(new GridLayout(1, 2, 8, 8));
        panel.add(new JLabel("Пароль"));
        panel.add(passwordField);
        int result = JOptionPane.showConfirmDialog(
                frame,
                panel,
                "Доступ к архиву логов",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) {
            return new char[0];
        }
        return passwordField.getPassword();
    }

    private void ingestLocalLog(JFrame frame) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = chooser.showOpenDialog(frame);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        Path logPath = chooser.getSelectedFile().toPath();
        appContext.getTaskRunner().runWithProgress(
                frame,
                "Logs",
                "Разбираем локальный dossier-ko.log...",
                () -> {
                    LogIngestionResult ingestionResult = facade.ingestLocalLog(logPath);
                    SwingUtilities.invokeLater(() -> showIngestionResult(frame, ingestionResult));
                },
                this::refreshAll,
                "Не удалось разобрать локальный лог");
    }

    private void refreshAll() {
        refreshEvents();
        refreshGroups();
        refreshSchedulerEvents();
        refreshPresets();
    }

    private void refreshEvents() {
        if (eventsModel == null) {
            return;
        }
        eventsModel.setRowCount(0);
        LogLevel level = selectedLevel();
        Page<LogEventEntity> events = facade.searchEvents(
                level,
                attentionFilter.isSelected(),
                parseFromDate(),
                parseToDate(),
                eventsPagination.pageIndex(),
                eventsPagination.pageSize());
        eventsPagination.update(events);
        for (LogEventEntity event : events.getContent()) {
            eventsModel.addRow(new Object[]{
                    event.getId(),
                    LogTableFormat.timestamp(event.getEventTimestamp()),
                    event.getLevel(),
                    event.getLoggerName(),
                    event.getExecutorName(),
                    event.isAttention() || event.isTracked(),
                    trim(event.getMessage(), 220)
            });
        }
    }

    private void refreshGroups() {
        if (groupsModel == null) {
            return;
        }
        groupsModel.setRowCount(0);
        for (ErrorGroupEntity group : facade.errorGroups()) {
            groupsModel.addRow(new Object[]{
                    group.getId(),
                    group.getCount(),
                    LogTableFormat.timestamp(group.getFirstSeen()),
                    LogTableFormat.timestamp(group.getLastSeen()),
                    LogTableFormat.timestamp(group.getPreviousSeen()),
                    group.isAttention(),
                    group.getExceptionClass(),
                    source(group),
                    trim(group.getSampleMessage(), 220)
            });
        }
    }

    private void refreshSchedulerEvents() {
        if (schedulerModel == null) {
            return;
        }
        schedulerModel.setRowCount(0);
        Page<LogEventEntity> events = facade.schedulerEvents(
                schedulerLoggerField.getText(),
                schedulerExecutorField.getText(),
                schedulerPagination.pageIndex(),
                schedulerPagination.pageSize());
        schedulerPagination.update(events);
        for (LogEventEntity event : events.getContent()) {
            schedulerModel.addRow(new Object[]{
                    event.getId(),
                    LogTableFormat.timestamp(event.getEventTimestamp()),
                    event.getLoggerName(),
                    event.getExecutorName(),
                    trim(event.getMessage(), 260)
            });
        }
    }

    private void refreshPresets() {
        if (presetsModel == null) {
            return;
        }
        presetsModel.setRowCount(0);
        for (SchedulerPresetEntity preset : facade.schedulerPresets()) {
            presetsModel.addRow(new Object[]{
                    preset.getId(),
                    preset.getName(),
                    preset.getLoggerPattern(),
                    preset.getExecutorPattern(),
                    preset.getMessagePattern()
            });
        }
    }

    private void toggleSelectedGroupAttention(JFrame frame) {
        Long groupId = selectedGroupId();
        if (groupId == null) {
            JOptionPane.showMessageDialog(frame, "Выберите группу", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        }
        facade.toggleGroupAttention(groupId);
        refreshAll();
    }

    private Long selectedGroupId() {
        if (groupsModel == null) {
            return null;
        }
        JTable table = findTableForModel(groupsModel);
        if (table == null || table.getSelectedRow() < 0) {
            return null;
        }
        int modelRow = table.convertRowIndexToModel(table.getSelectedRow());
        return (Long) groupsModel.getValueAt(modelRow, 0);
    }

    private void cleanupBefore(JFrame frame) {
        String value = JOptionPane.showInputDialog(frame, "Удалить все события старше даты yyyy-MM-dd");
        if (value == null || value.isBlank()) {
            return;
        }
        try {
            facade.deleteEventsBefore(LocalDate.parse(value.trim()).atStartOfDay());
            refreshAll();
        } catch (DateTimeParseException exception) {
            appContext.getErrorHandler().handle(frame, "Некорректная дата", exception);
        }
    }

    private void showEventDetails(JFrame frame, Long id) {
        LogEventEntity event = facade.eventById(id);
        JTextArea textArea = new JTextArea(event.getMessage() + System.lineSeparator() + event.getStackTrace());
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JOptionPane.showMessageDialog(frame, new JScrollPane(textArea), "Log event " + id, JOptionPane.INFORMATION_MESSAGE);
    }

    private void showGroupMessage(JFrame frame, Long id) {
        ErrorGroupEntity group = facade.errorGroupById(id);
        JTextArea textArea = new JTextArea(detailsText(group.getSampleMessage(), group.getSampleStackTrace()));
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JOptionPane.showMessageDialog(frame, new JScrollPane(textArea), "Group " + id, JOptionPane.INFORMATION_MESSAGE);
    }

    private String detailsText(String message, String stackTrace) {
        String safeMessage = message == null ? "" : message;
        if (stackTrace == null || stackTrace.isBlank()) {
            return safeMessage;
        }
        return safeMessage + System.lineSeparator() + stackTrace;
    }

    private void showIngestionResult(JFrame frame, LogIngestionResult result) {
        JOptionPane.showMessageDialog(frame,
                "Добавлено: " + result.insertedEvents() + "\nДубликаты: " + result.skippedDuplicates(),
                "Logs",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void savePresetDialog() {
        JTextField name = new JTextField("Form303");
        JTextField logger = new JTextField("Form303Scheduler");
        JTextField executor = new JTextField("asyncForm303SchedulerExecutor");
        JTextField message = new JTextField();
        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));
        panel.add(new JLabel("Name"));
        panel.add(name);
        panel.add(new JLabel("Class"));
        panel.add(logger);
        panel.add(new JLabel("Executor"));
        panel.add(executor);
        panel.add(new JLabel("Message"));
        panel.add(message);
        int result = JOptionPane.showConfirmDialog(null, panel, "Scheduler preset", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            facade.savePreset(null, name.getText(), logger.getText(), executor.getText(), message.getText());
            refreshPresets();
        }
    }

    private void deleteSelectedPreset() {
        JTable table = findTableForModel(presetsModel);
        if (table == null || table.getSelectedRow() < 0) {
            return;
        }
        int modelRow = table.convertRowIndexToModel(table.getSelectedRow());
        facade.deletePreset((Long) presetsModel.getValueAt(modelRow, 0));
        refreshPresets();
    }

    private void applySelectedPreset() {
        JTable table = findTableForModel(presetsModel);
        if (table == null || table.getSelectedRow() < 0) {
            return;
        }
        int modelRow = table.convertRowIndexToModel(table.getSelectedRow());
        schedulerLoggerField.setText(String.valueOf(presetsModel.getValueAt(modelRow, 2)));
        schedulerExecutorField.setText(String.valueOf(presetsModel.getValueAt(modelRow, 3)));
        refreshSchedulerEvents();
    }

    private JTable findTableForModel(DefaultTableModel model) {
        for (Window window : Window.getWindows()) {
            JTable table = findTable(window, model);
            if (table != null) {
                return table;
            }
        }
        return null;
    }

    private JTable findTable(Component component, DefaultTableModel model) {
        if (component instanceof JTable table && table.getModel() == model) {
            return table;
        }
        if (component instanceof Container container) {
            for (Component child : container.getComponents()) {
                JTable table = findTable(child, model);
                if (table != null) {
                    return table;
                }
            }
        }
        return null;
    }

    private LogLevel selectedLevel() {
        String value = String.valueOf(levelFilter.getSelectedItem());
        if ("ERROR".equals(value)) {
            return LogLevel.ERROR;
        }
        if ("WARN".equals(value)) {
            return LogLevel.WARN;
        }
        return null;
    }

    private LocalDateTime parseFromDate() {
        if (fromDateField.getText().isBlank()) {
            return null;
        }
        return LocalDate.parse(fromDateField.getText().trim()).atStartOfDay();
    }

    private LocalDateTime parseToDate() {
        if (toDateField.getText().isBlank()) {
            return null;
        }
        return LocalDateTime.of(LocalDate.parse(toDateField.getText().trim()), LocalTime.MAX);
    }

    private String source(ErrorGroupEntity group) {
        if (group.getSourceFile() == null) {
            return "";
        }
        return group.getSourceFile() + ":" + group.getLineNumber();
    }

    private String trim(String value, int maxLength) {
        if (value == null) {
            return "";
        }
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength - 3) + "...";
    }

    private static final class PaginationControls extends JPanel {

        private final JComboBox<Integer> pageSizeCombo = new JComboBox<>(new Integer[]{20, 50, 100});
        private final JLabel pageLabel = new JLabel();
        private final JButton previousButton = new JButton("Назад");
        private final JButton nextButton = new JButton("Вперед");
        private final Runnable refreshAction;
        private int pageIndex;
        private int totalPages;

        private PaginationControls(Runnable refreshAction) {
            super(new FlowLayout(FlowLayout.RIGHT, 8, 4));
            this.refreshAction = refreshAction;
            setOpaque(false);
            pageSizeCombo.setSelectedItem(20);
            BusinessTheme.styleSecondaryButton(previousButton);
            BusinessTheme.styleSecondaryButton(nextButton);

            previousButton.addActionListener(e -> {
                if (pageIndex > 0) {
                    pageIndex--;
                    refreshAction.run();
                }
            });
            nextButton.addActionListener(e -> {
                if (pageIndex + 1 < totalPages) {
                    pageIndex++;
                    refreshAction.run();
                }
            });
            pageSizeCombo.addActionListener(e -> {
                reset();
                refreshAction.run();
            });

            add(new JLabel("Строк на странице"));
            add(pageSizeCombo);
            add(previousButton);
            add(pageLabel);
            add(nextButton);
            updateLabel(0, 0);
        }

        private int pageIndex() {
            return pageIndex;
        }

        private int pageSize() {
            return (Integer) pageSizeCombo.getSelectedItem();
        }

        private void reset() {
            pageIndex = 0;
        }

        private void update(Page<?> page) {
            totalPages = page.getTotalPages();
            if (totalPages > 0 && pageIndex >= totalPages) {
                pageIndex = totalPages - 1;
                refreshAction.run();
                return;
            }
            updateLabel(page.getTotalElements(), totalPages);
        }

        private void updateLabel(long totalElements, int pages) {
            int currentPage = pages == 0 ? 0 : pageIndex + 1;
            pageLabel.setText("Страница " + currentPage + " из " + pages + ", всего " + totalElements);
            previousButton.setEnabled(pageIndex > 0);
            nextButton.setEnabled(pageIndex + 1 < pages);
        }
    }
}
