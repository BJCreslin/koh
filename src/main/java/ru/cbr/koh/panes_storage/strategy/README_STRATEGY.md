# Паттерн Strategy для панелей

## Описание

Реализован паттерн Strategy для управления панелями приложения. Это позволяет легко добавлять новые типы панелей и изменять их поведение без модификации существующего кода.

## Структура

### Основные компоненты

1. **PanelStrategy** - интерфейс стратегии для создания панелей
2. **SaveablePanelStrategy** - расширенный интерфейс для панелей с возможностью сохранения
3. **PanelContext** - контекст, который использует стратегию
4. **PanelType** - перечисление типов панелей
5. **PanelStrategyFactory** - фабрика для создания стратегий
6. **PanelManager** - утилитарный класс для управления панелями

### Конкретные стратегии

- **LoggerProxyPanelStrategy** - стратегия для панели Logger Proxy
- **PermissionMigrationPanelStrategy** - стратегия для панели миграции разрешений

## Использование

### Создание новой панели

```java
// Создание через фабрику
PanelStrategy strategy = PanelStrategyFactory.createStrategy(PanelType.LOGGER_PROXY);
PanelContext context = new PanelContext(strategy);

// Использование
String title = context.getTitle();
JComponent panel = context.createPanel(frame);
```

### Добавление нового типа панели

1. Добавить новый тип в enum `PanelType`:
```java
NEW_PANEL_TYPE("Display Name", "Description")
```

2. Создать новую стратегию, реализующую `PanelStrategy` или `SaveablePanelStrategy`

3. Зарегистрировать в `PanelStrategyFactory`:
```java
strategySuppliers.put(PanelType.NEW_PANEL_TYPE, NewPanelStrategy::new);
```

### Работа с PanelsHolder

```java
PanelsHolder holder = new PanelsHolder();

// Получить все панели
List<PaneInterface> panels = holder.getPanels();

// Найти панель по типу
PaneInterface panel = holder.getPanelByType(PanelType.LOGGER_PROXY);

// Добавить панель
holder.addPanel(PanelType.PERMISSION_MIGRATION);

// Удалить панель
holder.removePanel(PanelType.LOGGER_PROXY);
```

### Сохранение данных

```java
// Сохранить данные всех панелей
PanelManager.saveAllPanels(panels);

// Сохранить данные конкретной панели
if (panel instanceof SaveablePanel) {
    ((SaveablePanel) panel).saveData();
}
```

## Преимущества

1. **Расширяемость** - легко добавлять новые типы панелей
2. **Разделение ответственности** - логика создания отделена от использования
3. **Тестируемость** - можно легко создавать mock-стратегии для тестов
4. **Конфигурируемость** - можно динамически менять стратегии
5. **Переиспользование** - стратегии можно использовать в разных контекстах

## Миграция с существующего кода

Существующие классы `LoggerProxyPanel` и `PermissionMigrationPanel` помечены как `@Deprecated` и теперь используют новый паттерн внутри себя. Это обеспечивает обратную совместимость.

Рекомендуется постепенно переходить на использование `PanelContext` напрямую:

```java
// Старый способ (deprecated)
LoggerProxyPanel panel = new LoggerProxyPanel();

// Новый способ
PanelContext context = new PanelContext(
    PanelStrategyFactory.createStrategy(PanelType.LOGGER_PROXY)
);
```
