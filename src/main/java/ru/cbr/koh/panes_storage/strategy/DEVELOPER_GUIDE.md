# Руководство разработчика: Паттерн Strategy для панелей

## Содержание
1. [Архитектура](#архитектура)
2. [Быстрый старт](#быстрый-старт)
3. [Создание новой панели](#создание-новой-панели)
4. [Примеры реализации](#примеры-реализации)
5. [Тестирование](#тестирование)
6. [Best Practices](#best-practices)
7. [Troubleshooting](#troubleshooting)

## Архитектура

### Компоненты паттерна Strategy

```
┌─────────────────┐    ┌─────────────────┐
│   PanelContext  │───▶│  PanelStrategy  │
│   (Context)     │    │  (Strategy)     │
└─────────────────┘    └─────────────────┘
                              ▲
                              │
                    ┌─────────┼─────────┐
                    │                   │
        ┌───────────────────┐  ┌─────────────────────┐
        │ LoggerProxyPanel  │  │ PermissionMigration │
        │    Strategy       │  │     Strategy        │
        └───────────────────┘  └─────────────────────┘
```

### Основные интерфейсы

- **PanelStrategy** - базовый интерфейс стратегии
- **SaveablePanelStrategy** - для панелей с сохранением данных
- **PanelContext** - контекст, использующий стратегию
- **PanelType** - типы панелей
- **PanelStrategyFactory** - фабрика стратегий

## Быстрый старт

### 1. Использование существующих панелей

```java
import ru.cbr.koh.panes_storage.strategy.*;

// Создание панели через фабрику
PanelStrategy strategy = PanelStrategyFactory.createStrategy(PanelType.LOGGER_PROXY);
PanelContext context = new PanelContext(strategy);

// Использование панели
JFrame frame = new JFrame();
String title = context.getTitle();
JComponent panel = context.createPanel(frame);

// Сохранение данных (если поддерживается)
if (context.getStrategy() instanceof SaveablePanelStrategy) {
    try {
        context.saveData();
    } catch (SerializationException e) {
        logger.error("Ошибка сохранения", e);
    }
}
```

### 2. Работа с PanelsHolder

```java
import ru.cbr.koh.panes_storage.PanelsHolder;

PanelsHolder holder = new PanelsHolder();

// Получить все панели
List<PaneInterface> allPanels = holder.getPanels();

// Найти конкретную панель
PaneInterface loggerPanel = holder.getPanelByType(PanelType.LOGGER_PROXY);

// Динамическое управление панелями
holder.addPanel(PanelType.PERMISSION_MIGRATION);
holder.removePanel(PanelType.LOGGER_PROXY);
```

## Создание новой панели

### Шаг 1: Добавить тип панели

```java
// В файле PanelType.java
public enum PanelType {
    LOGGER_PROXY("Logger Proxy", "Панель для настройки логгирования через прокси"),
    PERMISSION_MIGRATION("Permission Migration", "Панель для миграции разрешений"),
    
    // Добавляем новый тип
    DATA_EXPORT("Data Export", "Панель для экспорта данных"),
    USER_MANAGEMENT("User Management", "Панель управления пользователями");
    
    // ... остальной код
}
```

### Шаг 2: Создать стратегию

#### Простая панель (только отображение)

```java
package ru.cbr.koh.panes_storage.strategy.impl;

import ru.cbr.koh.panes_storage.strategy.PanelStrategy;
import ru.cbr.koh.panes_storage.strategy.PanelType;

import javax.swing.*;
import java.awt.*;

/**
 * Стратегия для панели экспорта данных
 */
public class DataExportPanelStrategy implements PanelStrategy {

    @Override
    public String getTitle() {
        return "Data Export";
    }

    @Override
    public PanelType getPanelType() {
        return PanelType.DATA_EXPORT;
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Создание UI компонентов
        JLabel titleLabel = new JLabel("Export Data", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        JButton exportButton = new JButton("Start Export");
        exportButton.addActionListener(e -> performExport());
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(exportButton, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void performExport() {
        // Логика экспорта
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, "Export completed!");
        });
    }
}
```

#### Панель с сохранением данных

```java
package ru.cbr.koh.panes_storage.strategy.impl;

import ru.cbr.koh.exceptions.SerializationException;
import ru.cbr.koh.panes_storage.strategy.SaveablePanelStrategy;
import ru.cbr.koh.panes_storage.strategy.PanelType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;

/**
 * Стратегия для панели управления пользователями с сохранением настроек
 */
public class UserManagementPanelStrategy implements SaveablePanelStrategy {
    
    private static final Logger logger = LogManager.getLogger(UserManagementPanelStrategy.class);
    private static final String CONFIG_FILE = "user_management.properties";
    
    private JTextField maxUsersField;
    private JCheckBox enableLoggingCheckBox;
    private Properties settings;

    public UserManagementPanelStrategy() {
        settings = new Properties();
        loadSettings();
    }

    @Override
    public String getTitle() {
        return "User Management";
    }

    @Override
    public PanelType getPanelType() {
        return PanelType.USER_MANAGEMENT;
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Заголовок
        JLabel titleLabel = new JLabel("User Management Settings");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        panel.add(titleLabel, gbc);
        
        // Максимальное количество пользователей
        gbc.gridwidth = 1; gbc.gridy = 1;
        panel.add(new JLabel("Max Users:"), gbc);
        
        maxUsersField = new JTextField(settings.getProperty("max.users", "100"), 10);
        gbc.gridx = 1;
        panel.add(maxUsersField, gbc);
        
        // Включить логирование
        enableLoggingCheckBox = new JCheckBox("Enable Logging", 
            Boolean.parseBoolean(settings.getProperty("enable.logging", "true")));
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(enableLoggingCheckBox, gbc);
        
        // Кнопки управления
        JPanel buttonPanel = createButtonPanel();
        gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonPanel, gbc);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton saveButton = new JButton("Save Settings");
        saveButton.addActionListener(e -> {
            try {
                saveData();
                JOptionPane.showMessageDialog(null, "Settings saved successfully!");
            } catch (SerializationException ex) {
                JOptionPane.showMessageDialog(null, "Error saving settings: " + ex.getMessage());
            }
        });
        
        JButton resetButton = new JButton("Reset to Defaults");
        resetButton.addActionListener(e -> resetToDefaults());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);
        
        return buttonPanel;
    }

    @Override
    public void saveData() throws SerializationException {
        try {
            settings.setProperty("max.users", maxUsersField.getText());
            settings.setProperty("enable.logging", String.valueOf(enableLoggingCheckBox.isSelected()));
            
            try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
                settings.store(fos, "User Management Settings");
            }
            
            logger.info("User management settings saved successfully");
            
        } catch (IOException e) {
            throw new SerializationException("Failed to save user management settings", e);
        }
    }
    
    private void loadSettings() {
        File configFile = new File(CONFIG_FILE);
        if (configFile.exists()) {
            try (FileInputStream fis = new FileInputStream(configFile)) {
                settings.load(fis);
            } catch (IOException e) {
                logger.warn("Failed to load settings, using defaults", e);
            }
        }
    }
    
    private void resetToDefaults() {
        maxUsersField.setText("100");
        enableLoggingCheckBox.setSelected(true);
    }
    
    /**
     * Получить текущие настройки
     * @return объект Properties с настройками
     */
    public Properties getSettings() {
        return new Properties(settings);
    }
}
```

### Шаг 3: Зарегистрировать в фабрике

```java
// В файле PanelStrategyFactory.java
static {
    strategySuppliers = new EnumMap<>(PanelType.class);
    strategySuppliers.put(PanelType.LOGGER_PROXY, LoggerProxyPanelStrategy::new);
    strategySuppliers.put(PanelType.PERMISSION_MIGRATION, PermissionMigrationPanelStrategy::new);
    
    // Добавляем новые стратегии
    strategySuppliers.put(PanelType.DATA_EXPORT, DataExportPanelStrategy::new);
    strategySuppliers.put(PanelType.USER_MANAGEMENT, UserManagementPanelStrategy::new);
}
```

### Шаг 4: Использование

```java
// Создание и использование новых панелей
PanelContext dataExportPanel = new PanelContext(
    PanelStrategyFactory.createStrategy(PanelType.DATA_EXPORT)
);

PanelContext userMgmtPanel = new PanelContext(
    PanelStrategyFactory.createStrategy(PanelType.USER_MANAGEMENT)
);

// Добавление в holder
PanelsHolder holder = new PanelsHolder();
holder.addPanel(PanelType.DATA_EXPORT);
holder.addPanel(PanelType.USER_MANAGEMENT);
```

## Примеры реализации

### Панель с асинхронными операциями

```java
public class AsyncDataPanelStrategy implements SaveablePanelStrategy {
    
    private JProgressBar progressBar;
    private JButton processButton;
    private SwingWorker<Void, Integer> currentWorker;

    @Override
    public JComponent createPanel(JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout());
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        
        processButton = new JButton("Start Processing");
        processButton.addActionListener(e -> startAsyncOperation());
        
        panel.add(progressBar, BorderLayout.NORTH);
        panel.add(processButton, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void startAsyncOperation() {
        if (currentWorker != null && !currentWorker.isDone()) {
            currentWorker.cancel(true);
        }
        
        currentWorker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                processButton.setEnabled(false);
                
                for (int i = 0; i <= 100; i++) {
                    if (isCancelled()) break;
                    
                    Thread.sleep(50); // Имитация работы
                    publish(i);
                }
                return null;
            }
            
            @Override
            protected void process(List<Integer> chunks) {
                int progress = chunks.get(chunks.size() - 1);
                progressBar.setValue(progress);
                progressBar.setString(progress + "%");
            }
            
            @Override
            protected void done() {
                processButton.setEnabled(true);
                progressBar.setString("Completed");
            }
        };
        
        currentWorker.execute();
    }
}
```

### Панель с валидацией

```java
public class FormPanelStrategy implements SaveablePanelStrategy {
    
    private JTextField nameField;
    private JTextField emailField;
    private Map<JTextField, String> validationErrors;

    public FormPanelStrategy() {
        validationErrors = new HashMap<>();
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Поля формы
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        
        // Добавление валидаторов
        addValidation(nameField, "Name", this::validateName);
        addValidation(emailField, "Email", this::validateEmail);
        
        // Layout
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);
        
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            if (validateForm()) {
                try {
                    saveData();
                    JOptionPane.showMessageDialog(frame, "Data saved successfully!");
                } catch (SerializationException ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
            }
        });
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(saveButton, gbc);
        
        return panel;
    }
    
    private void addValidation(JTextField field, String fieldName, Function<String, String> validator) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { validate(); }
            @Override
            public void removeUpdate(DocumentEvent e) { validate(); }
            @Override
            public void changedUpdate(DocumentEvent e) { validate(); }
            
            private void validate() {
                String error = validator.apply(field.getText());
                if (error != null) {
                    validationErrors.put(field, error);
                    field.setBorder(BorderFactory.createLineBorder(Color.RED));
                    field.setToolTipText(error);
                } else {
                    validationErrors.remove(field);
                    field.setBorder(UIManager.getBorder("TextField.border"));
                    field.setToolTipText(null);
                }
            }
        });
    }
    
    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Name is required";
        }
        if (name.length() < 2) {
            return "Name must be at least 2 characters";
        }
        return null;
    }
    
    private String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Email is required";
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return "Invalid email format";
        }
        return null;
    }
    
    private boolean validateForm() {
        // Принудительная валидация всех полей
        validateName(nameField.getText());
        validateEmail(emailField.getText());
        
        if (!validationErrors.isEmpty()) {
            StringBuilder errors = new StringBuilder("Please fix the following errors:\n");
            validationErrors.values().forEach(error -> errors.append("• ").append(error).append("\n"));
            JOptionPane.showMessageDialog(null, errors.toString(), "Validation Errors", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    @Override
    public void saveData() throws SerializationException {
        // Логика сохранения
        Properties data = new Properties();
        data.setProperty("name", nameField.getText());
        data.setProperty("email", emailField.getText());
        
        try (FileOutputStream fos = new FileOutputStream("form_data.properties")) {
            data.store(fos, "Form Data");
        } catch (IOException e) {
            throw new SerializationException("Failed to save form data", e);
        }
    }
}
```

## Тестирование

### Unit тесты для стратегий

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class DataExportPanelStrategyTest {
    
    private DataExportPanelStrategy strategy;
    
    @BeforeEach
    void setUp() {
        strategy = new DataExportPanelStrategy();
    }
    
    @Test
    void testGetTitle() {
        assertEquals("Data Export", strategy.getTitle());
    }
    
    @Test
    void testGetPanelType() {
        assertEquals(PanelType.DATA_EXPORT, strategy.getPanelType());
    }
    
    @Test
    void testCreatePanel() {
        JFrame frame = new JFrame();
        JComponent panel = strategy.createPanel(frame);
        
        assertNotNull(panel);
        assertTrue(panel instanceof JPanel);
    }
}
```

### Mock стратегии для тестирования

```java
public class MockPanelStrategy implements SaveablePanelStrategy {
    
    private boolean saveDataCalled = false;
    private SerializationException exceptionToThrow;
    
    public MockPanelStrategy() {}
    
    public MockPanelStrategy(SerializationException exceptionToThrow) {
        this.exceptionToThrow = exceptionToThrow;
    }
    
    @Override
    public String getTitle() {
        return "Mock Panel";
    }
    
    @Override
    public PanelType getPanelType() {
        return PanelType.LOGGER_PROXY; // Используем существующий тип для тестов
    }
    
    @Override
    public JComponent createPanel(JFrame frame) {
        return new JPanel();
    }
    
    @Override
    public void saveData() throws SerializationException {
        saveDataCalled = true;
        if (exceptionToThrow != null) {
            throw exceptionToThrow;
        }
    }
    
    public boolean wasSaveDataCalled() {
        return saveDataCalled;
    }
}

// Использование в тестах
@Test
void testPanelContextSaveData() throws SerializationException {
    MockPanelStrategy mockStrategy = new MockPanelStrategy();
    PanelContext context = new PanelContext(mockStrategy);
    
    context.saveData();
    
    assertTrue(mockStrategy.wasSaveDataCalled());
}
```

### Интеграционные тесты

```java
class PanelsIntegrationTest {
    
    @Test
    void testPanelsHolderWithAllPanelTypes() {
        PanelsHolder holder = new PanelsHolder();
        List<PaneInterface> panels = holder.getPanels();
        
        // Проверяем, что все типы панелей созданы
        assertEquals(PanelType.values().length, panels.size());
        
        for (PanelType type : PanelType.values()) {
            PaneInterface panel = holder.getPanelByType(type);
            assertNotNull(panel, "Panel for type " + type + " should exist");
            assertTrue(panel instanceof PanelContext);
            
            PanelContext context = (PanelContext) panel;
            assertEquals(type, context.getPanelType());
        }
    }
    
    @Test
    void testPanelManagerSaveAll() {
        List<PaneInterface> panels = Arrays.asList(
            new PanelContext(new MockPanelStrategy()),
            new PanelContext(new MockPanelStrategy())
        );
        
        // Не должно выбрасывать исключений
        assertDoesNotThrow(() -> PanelManager.saveAllPanels(panels));
    }
}
```

## Best Practices

### 1. Разделение ответственности

```java
// ✅ Хорошо - стратегия фокусируется только на создании UI
public class ReportPanelStrategy implements PanelStrategy {
    private final ReportService reportService; // Инжектируем сервис
    
    public ReportPanelStrategy(ReportService reportService) {
        this.reportService = reportService;
    }
    
    @Override
    public JComponent createPanel(JFrame frame) {
        // Только UI логика
        return createReportUI();
    }
    
    private void generateReport() {
        // Делегируем бизнес-логику сервису
        reportService.generateReport();
    }
}

// ❌ Плохо - смешивание UI и бизнес-логики
public class BadReportPanelStrategy implements PanelStrategy {
    @Override
    public JComponent createPanel(JFrame frame) {
        // UI + бизнес-логика в одном месте
        JButton button = new JButton("Generate");
        button.addActionListener(e -> {
            // Сложная бизнес-логика прямо в UI
            Connection conn = DriverManager.getConnection(...);
            // ... много кода
        });
        return button;
    }
}
```

### 2. Обработка ошибок

```java
public class RobustPanelStrategy implements SaveablePanelStrategy {
    
    @Override
    public JComponent createPanel(JFrame frame) {
        try {
            return createPanelInternal(frame);
        } catch (Exception e) {
            logger.error("Error creating panel", e);
            return createErrorPanel(e);
        }
    }
    
    private JComponent createErrorPanel(Exception e) {
        JPanel errorPanel = new JPanel(new BorderLayout());
        JLabel errorLabel = new JLabel("Error: " + e.getMessage());
        errorLabel.setForeground(Color.RED);
        errorPanel.add(errorLabel, BorderLayout.CENTER);
        return errorPanel;
    }
    
    @Override
    public void saveData() throws SerializationException {
        try {
            performSave();
        } catch (IOException e) {
            throw new SerializationException("Failed to save panel data", e);
        } catch (Exception e) {
            logger.error("Unexpected error during save", e);
            throw new SerializationException("Unexpected error occurred", e);
        }
    }
}
```

### 3. Ленивая инициализация

```java
public class LazyInitPanelStrategy implements PanelStrategy {
    
    private JComponent cachedPanel;
    private volatile boolean initialized = false;
    
    @Override
    public JComponent createPanel(JFrame frame) {
        if (!initialized) {
            synchronized (this) {
                if (!initialized) {
                    cachedPanel = createPanelInternal(frame);
                    initialized = true;
                }
            }
        }
        return cachedPanel;
    }
    
    private JComponent createPanelInternal(JFrame frame) {
        // Тяжелая инициализация
        return new ComplexPanel();
    }
}
```

### 4. Конфигурируемые стратегии

```java
public class ConfigurablePanelStrategy implements PanelStrategy {
    
    private final PanelConfig config;
    
    public ConfigurablePanelStrategy(PanelConfig config) {
        this.config = config;
    }
    
    @Override
    public JComponent createPanel(JFrame frame) {
        JPanel panel = new JPanel();
        
        if (config.isShowTitle()) {
            panel.add(new JLabel(getTitle()));
        }
        
        if (config.isShowButtons()) {
            panel.add(createButtonPanel());
        }
        
        return panel;
    }
    
    public static class PanelConfig {
        private boolean showTitle = true;
        private boolean showButtons = true;
        
        // getters and setters
    }
}
```

## Troubleshooting

### Частые проблемы и решения

#### 1. Панель не отображается

```java
// Проблема: забыли зарегистрировать в фабрике
// Решение: добавить в PanelStrategyFactory
strategySuppliers.put(PanelType.NEW_PANEL, NewPanelStrategy::new);
```

#### 2. Ошибка при сохранении

```java
// Проблема: стратегия не реализует SaveablePanelStrategy
// Решение: реализовать интерфейс
public class MyStrategy implements SaveablePanelStrategy {
    @Override
    public void saveData() throws SerializationException {
        // Реализация сохранения
    }
}
```

#### 3. NullPointerException при создании панели

```java
// Проблема: не инициализированы поля
public class FixedStrategy implements PanelStrategy {
    
    public FixedStrategy() {
        // Инициализация в конструкторе
        initializeComponents();
    }
    
    private void initializeComponents() {
        // Создание необходимых компонентов
    }
}
```

#### 4. Проблемы с потокобезопасностью

```java
// Проблема: изменение UI из фонового потока
// Решение: использовать SwingUtilities
private void updateUI() {
    SwingUtilities.invokeLater(() -> {
        // Обновление UI компонентов
        label.setText("Updated");
    });
}
```

### Отладка

```java
// Включить подробное логирование
private static final Logger logger = LogManager.getLogger(MyPanelStrategy.class);

@Override
public JComponent createPanel(JFrame frame) {
    logger.debug("Creating panel for type: {}", getPanelType());
    
    try {
        JComponent panel = createPanelInternal(frame);
        logger.debug("Panel created successfully");
        return panel;
    } catch (Exception e) {
        logger.error("Error creating panel", e);
        throw e;
    }
}
```

## Заключение

Паттерн Strategy для панелей обеспечивает:

- ✅ **Гибкость**: легко добавлять новые типы панелей
- ✅ **Тестируемость**: каждая стратегия тестируется отдельно
- ✅ **Переиспользование**: стратегии можно использовать в разных контекстах
- ✅ **Расширяемость**: простое добавление новой функциональности
- ✅ **Поддерживаемость**: четкое разделение ответственности

Следуя этому руководству, вы сможете эффективно работать с паттерном Strategy и создавать качественные, поддерживаемые панели для вашего приложения.
