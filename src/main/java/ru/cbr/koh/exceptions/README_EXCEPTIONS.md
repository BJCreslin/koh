# Система обработки исключений KOH

## Структура исключений

### Базовые классы
- `KohException` - базовое исключение для всего приложения
- `ExceptionHandler` - централизованный обработчик исключений

### Специализированные исключения
- `ConfigurationException` - ошибки конфигурации
- `FileProcessingException` - ошибки обработки файлов  
- `ExcelParsingException` - ошибки парсинга Excel (наследует от FileProcessingException)
- `SerializationException` - ошибки сериализации/десериализации

## Использование

### 1. Бросание исключений
```java
// В сервисных методах
public void loadConfig() throws ConfigurationException {
    try {
        // код загрузки
    } catch (IOException e) {
        throw new ConfigurationException("Не удалось загрузить конфигурацию", e);
    }
}
```

### 2. Обработка исключений
```java
// С показом пользователю
try {
    service.doSomething();
} catch (KohException e) {
    ExceptionHandler.handleException("Операция не выполнена", e, parentComponent);
}

// Только логирование
try {
    service.doSomething();
} catch (KohException e) {
    ExceptionHandler.logException("Фоновая операция", e);
}

// Критическая ошибка
try {
    service.initialize();
} catch (KohException e) {
    ExceptionHandler.handleCriticalException("Инициализация приложения", e, null);
}
```

### 3. Логирование
Все исключения автоматически логируются с полным stack trace через Log4j2.

## Принципы

1. **Fail Fast** - проверяйте входные параметры и состояние в начале методов
2. **Meaningful Messages** - используйте понятные сообщения об ошибках
3. **Context Preservation** - сохраняйте исходное исключение через `cause`
4. **User-Friendly** - показывайте пользователю понятные сообщения через ExceptionHandler
