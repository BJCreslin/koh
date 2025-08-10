# Руководство по миграции на паттерн Strategy

## Обзор изменений

Система панелей была рефакторирована для использования паттерна Strategy. Это обеспечивает:
- Лучшую расширяемость
- Упрощенное тестирование
- Четкое разделение ответственности

## Что изменилось

### До (старая система)
```java
// Прямое создание панелей
LoggerProxyPanel loggerPanel = new LoggerProxyPanel();
PermissionMigrationPanel permissionPanel = new PermissionMigrationPanel();

// Ручное управление в PanelsHolder
public class PanelsHolder {
    public PanelsHolder() {
        panels.add(new PermissionMigrationPanel());
        panels.add(new LoggerProxyPanel());
    }
}
```

### После (новая система)
```java
// Создание через стратегии
PanelContext loggerPanel = new PanelContext(
    PanelStrategyFactory.createStrategy(PanelType.LOGGER_PROXY)
);

// Автоматическое управление в PanelsHolder
public class PanelsHolder {
    public PanelsHolder() {
        panels = createPanelsUsingStrategy(); // Автоматически создает все панели
    }
}
```

## Пошаговая миграция

### Шаг 1: Обновите импорты

```java
// Добавьте новые импорты
import ru.cbr.koh.panes_storage.strategy.*;
import ru.cbr.koh.panes_storage.strategy.impl.*;
```

### Шаг 2: Замените прямое создание панелей

#### Было:
```java
LoggerProxyPanel panel = new LoggerProxyPanel();
String title = panel.getTitle();
JComponent component = panel.createPanel(frame);
```

#### Стало:
```java
PanelContext panel = new PanelContext(
    PanelStrategyFactory.createStrategy(PanelType.LOGGER_PROXY)
);
String title = panel.getTitle();
JComponent component = panel.createPanel(frame);
```

### Шаг 3: Обновите работу с PanelsHolder

#### Было:
```java
PanelsHolder holder = new PanelsHolder();
List<PaneInterface> panels = holder.getPanels();
// Нет возможности найти панель по типу
```

#### Стало:
```java
PanelsHolder holder = new PanelsHolder();
List<PaneInterface> panels = holder.getPanels();

// Новая функциональность
PaneInterface loggerPanel = holder.getPanelByType(PanelType.LOGGER_PROXY);
holder.addPanel(PanelType.PERMISSION_MIGRATION);
holder.removePanel(PanelType.LOGGER_PROXY);
```

### Шаг 4: Обновите сохранение данных

#### Было:
```java
// Ручное сохранение каждой панели
for (PaneInterface panel : panels) {
    if (panel instanceof SaveablePanel) {
        try {
            ((SaveablePanel) panel).saveData();
        } catch (SerializationException e) {
            // Обработка ошибки
        }
    }
}
```

#### Стало:
```java
// Автоматическое сохранение всех панелей
PanelManager.saveAllPanels(panels);
```

## Совместимость

### Существующий код продолжает работать

Старые классы `LoggerProxyPanel` и `PermissionMigrationPanel` помечены как `@Deprecated`, но продолжают работать:

```java
// Этот код все еще работает, но устарел
LoggerProxyPanel panel = new LoggerProxyPanel(); // ⚠️ Deprecated
String title = panel.getTitle(); // Работает
JComponent component = panel.createPanel(frame); // Работает
```

### Рекомендуемый подход к миграции

1. **Постепенная миграция**: Не нужно менять весь код сразу
2. **Тестирование**: Тестируйте каждый измененный компонент
3. **Удаление deprecated**: Планируйте удаление устаревшего кода в будущих версиях

## Примеры миграции

### Пример 1: Простое использование панели

#### До:
```java
public class MainWindow {
    private void initializePanels() {
        LoggerProxyPanel loggerPanel = new LoggerProxyPanel();
        tabbedPane.addTab(loggerPanel.getTitle(), loggerPanel.createPanel(this));
    }
}
```

#### После:
```java
public class MainWindow {
    private void initializePanels() {
        PanelContext loggerPanel = new PanelContext(
            PanelStrategyFactory.createStrategy(PanelType.LOGGER_PROXY)
        );
        tabbedPane.addTab(loggerPanel.getTitle(), loggerPanel.createPanel(this));
    }
}
```

### Пример 2: Работа с коллекцией панелей

#### До:
```java
public class PanelManager {
    private List<PaneInterface> panels;
    
    public void initializePanels() {
        panels = new ArrayList<>();
        panels.add(new LoggerProxyPanel());
        panels.add(new PermissionMigrationPanel());
    }
    
    public void saveAllPanels() {
        for (PaneInterface panel : panels) {
            if (panel instanceof SaveablePanel) {
                try {
                    ((SaveablePanel) panel).saveData();
                } catch (SerializationException e) {
                    logger.error("Error saving panel", e);
                }
            }
        }
    }
}
```

#### После:
```java
public class PanelManager {
    private PanelsHolder panelsHolder;
    
    public void initializePanels() {
        panelsHolder = new PanelsHolder(); // Автоматически создает все панели
    }
    
    public void saveAllPanels() {
        PanelManager.saveAllPanels(panelsHolder.getPanels()); // Один вызов
    }
    
    // Новые возможности
    public void addCustomPanel() {
        panelsHolder.addPanel(PanelType.LOGGER_PROXY);
    }
    
    public PaneInterface findPanel(PanelType type) {
        return panelsHolder.getPanelByType(type);
    }
}
```

### Пример 3: Доступ к специфичным методам панели

#### До:
```java
PermissionMigrationPanel permissionPanel = new PermissionMigrationPanel();
ProfilePanel profilePanel = permissionPanel.getProfilePanel(); // Прямой доступ
```

#### После:
```java
PanelContext context = new PanelContext(
    PanelStrategyFactory.createStrategy(PanelType.PERMISSION_MIGRATION)
);

// Получаем доступ к стратегии для специфичных методов
if (context.getStrategy() instanceof PermissionMigrationPanelStrategy) {
    PermissionMigrationPanelStrategy strategy = 
        (PermissionMigrationPanelStrategy) context.getStrategy();
    ProfilePanel profilePanel = strategy.getProfilePanel();
}
```

## Тестирование после миграции

### Базовые тесты

```java
@Test
void testPanelMigration() {
    // Старый способ (deprecated, но работает)
    LoggerProxyPanel oldPanel = new LoggerProxyPanel();
    
    // Новый способ
    PanelContext newPanel = new PanelContext(
        PanelStrategyFactory.createStrategy(PanelType.LOGGER_PROXY)
    );
    
    // Проверяем, что результат одинаковый
    assertEquals(oldPanel.getTitle(), newPanel.getTitle());
    
    JFrame testFrame = new JFrame();
    JComponent oldComponent = oldPanel.createPanel(testFrame);
    JComponent newComponent = newPanel.createPanel(testFrame);
    
    assertNotNull(oldComponent);
    assertNotNull(newComponent);
    assertEquals(oldComponent.getClass(), newComponent.getClass());
}
```

### Интеграционные тесты

```java
@Test
void testPanelsHolderMigration() {
    PanelsHolder holder = new PanelsHolder();
    List<PaneInterface> panels = holder.getPanels();
    
    // Проверяем, что все ожидаемые панели созданы
    assertTrue(panels.size() > 0);
    
    // Проверяем новую функциональность
    PaneInterface loggerPanel = holder.getPanelByType(PanelType.LOGGER_PROXY);
    assertNotNull(loggerPanel);
    assertEquals("Logger Proxy", loggerPanel.getTitle());
}
```

## Часто задаваемые вопросы

### Q: Нужно ли менять весь код сразу?
A: Нет, миграция может быть постепенной. Старые классы продолжают работать.

### Q: Как получить доступ к специфичным методам панели?
A: Используйте приведение типа стратегии или создайте адаптер.

### Q: Что делать с кастомными панелями?
A: Создайте новый тип в `PanelType` и реализуйте соответствующую стратегию.

### Q: Как тестировать новые панели?
A: Используйте mock-стратегии или тестируйте стратегии отдельно от контекста.

### Q: Влияет ли миграция на производительность?
A: Минимально. Новая система может быть даже быстрее благодаря оптимизациям.

## Чек-лист миграции

- [ ] Обновлены импорты
- [ ] Заменено прямое создание панелей на фабричный метод
- [ ] Обновлена работа с PanelsHolder
- [ ] Заменено ручное сохранение на PanelManager.saveAllPanels()
- [ ] Добавлены тесты для измененного кода
- [ ] Проведено интеграционное тестирование
- [ ] Обновлена документация (если есть)
- [ ] Запланировано удаление deprecated кода

## Поддержка

Если у вас возникли проблемы с миграцией:

1. Проверьте логи на наличие ошибок
2. Убедитесь, что все новые типы панелей зарегистрированы в фабрике
3. Проверьте, что стратегии правильно реализуют интерфейсы
4. Используйте отладчик для пошагового выполнения

## Планы по удалению deprecated кода

- **Версия 2.0**: Удаление старых классов панелей
- **Версия 1.5**: Предупреждения компилятора о deprecated коде
- **Версия 1.2**: Текущая версия с поддержкой обратной совместимости
