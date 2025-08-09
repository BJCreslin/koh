# Permission Factories

Система фабрик для создания объектов Permission различными способами.

## Структура

### Основные компоненты

1. **PermissionFactory<T>** - базовый интерфейс фабрики
2. **BasicPermissionFactory** - создание из базовых параметров
3. **DialogPermissionFactory** - создание из PermissionDialogObject
4. **ParserPermissionFactory** - создание из ParserPermission
5. **PermissionFactoryRegistry** - реестр управления фабриками
6. **PermissionBuilder** - builder pattern для гибкого создания
7. **PermissionFactoryUtils** - утилиты для упрощения работы

## Примеры использования

### Создание через PermissionFactoryUtils (рекомендуется)

```java
// Минимальное создание
Permission permission = PermissionFactoryUtils.createMinimal(
    "system#users#view", 
    PermissionType.ACTION, 
    "Просмотр пользователей"
);

// Создание для KO департамента
Permission koPermission = PermissionFactoryUtils.createForKO(
    "system#users#edit",
    "Редактирование пользователей",
    "EDIT_USERS_GROUP",
    "EDIT_USERS_USER"
);

// Создание для GIBR департамента
Permission gibrPermission = PermissionFactoryUtils.createForGIBR(
    "system#reports#view",
    "Просмотр отчетов",
    "VIEW_REPORTS_GROUP",
    "VIEW_REPORTS_USER"
);
```

### Создание через Builder Pattern

```java
// Простое создание
Permission permission = PermissionFactoryUtils.builder()
    .key("system#admin#settings")
    .name("Настройки системы")
    .autoType()
    .groupAction("SETTINGS_GROUP")
    .userAction("SETTINGS_USER")
    .forKO()
    .addProfile(Profile.BUSINESS_ADMIN_CO)
    .description("Доступ к настройкам системы")
    .build();

// Создание с несколькими профилями
Permission multiProfilePermission = PermissionFactoryUtils.builderForAll(
    "system#audit#logs", 
    "Журналы аудита"
)
    .groupAction("AUDIT_GROUP")
    .userAction("AUDIT_USER")
    .addProfiles(List.of(
        Profile.AUDITOR,
        Profile.BUSINESS_ADMINISTRATOR,
        Profile.CURATOR_GIBR
    ))
    .description("Доступ к журналам аудита")
    .build();
```

### Создание из различных источников

```java
// Из PermissionDialogObject
PermissionDialogObject dialogObject = new PermissionDialogObject(/* параметры */);
Permission fromDialog = PermissionFactoryUtils.createFromDialog(
    dialogObject, 
    List.of(Profile.BUSINESS_ADMIN_CO)
);

// Из ParserPermission (только если нужно сохранение)
ParserPermission parserPermission = new ParserPermission(/* параметры */);
Permission fromParser = PermissionFactoryUtils.createFromParserIfNeeded(parserPermission);

// Универсальное создание (автоопределение типа источника)
Object source = getPermissionSource(); // может быть любым поддерживаемым типом
Permission universal = PermissionFactoryUtils.createPermission(source);
```

### Работа с реестром фабрик

```java
// Получение реестра
PermissionFactoryRegistry registry = PermissionFactoryUtils.getRegistry();

// Проверка возможности создания
if (PermissionFactoryUtils.canCreateFrom(someSource)) {
    Permission permission = PermissionFactoryUtils.createPermission(someSource);
}

// Регистрация кастомной фабрики
PermissionFactory<CustomType> customFactory = new CustomPermissionFactory();
PermissionFactoryUtils.registerCustomFactory(customFactory);
```

### Использование конкретных фабрик

```java
// BasicPermissionFactory
BasicPermissionFactory basicFactory = new BasicPermissionFactory();
BasicPermissionFactory.PermissionData data = new BasicPermissionFactory.PermissionData(
    "system#users#create",
    PermissionType.ACTION,
    "CREATE_USERS_GROUP",
    "CREATE_USERS_USER",
    "Создание пользователей",
    List.of(Profile.BUSINESS_ADMINISTRATOR),
    "Разрешение на создание новых пользователей",
    List.of(TreeType.KO)
);
Permission permission = basicFactory.create(data);

// DialogPermissionFactory
DialogPermissionFactory dialogFactory = new DialogPermissionFactory();
Permission dialogPermission = dialogFactory.createWithProfiles(
    dialogObject, 
    List.of(Profile.CURATOR_GIBR, Profile.ANALYST_STBN)
);

// ParserPermissionFactory
ParserPermissionFactory parserFactory = new ParserPermissionFactory();
Permission bankDependentPermission = parserFactory.createForBankDependent(parserPermission);
```

## Расширение системы

### Создание кастомной фабрики

```java
public class CustomPermissionFactory implements PermissionFactory<CustomSource> {
    
    @Override
    public Permission create(CustomSource source) {
        // Логика создания Permission из CustomSource
        return new Permission(/* параметры на основе source */);
    }
    
    @Override
    public boolean canHandle(Object source) {
        return source instanceof CustomSource;
    }
}

// Регистрация
PermissionFactoryUtils.registerCustomFactory(new CustomPermissionFactory());
```

## Преимущества использования фабрик

1. **Единообразие** - все Permission создаются через стандартизированный интерфейс
2. **Гибкость** - поддержка различных источников данных
3. **Расширяемость** - легко добавлять новые типы фабрик
4. **Удобство** - Builder pattern и утилиты упрощают создание
5. **Валидация** - автоматическая проверка обязательных полей
6. **Типизация** - строгая типизация предотвращает ошибки

## Рекомендации

1. Используйте `PermissionFactoryUtils` для большинства случаев
2. Используйте `PermissionBuilder` для сложных объектов с множеством параметров
3. Создавайте кастомные фабрики для специфичных источников данных
4. Всегда проверяйте возможность создания через `canCreateFrom()` перед созданием
5. Используйте автоопределение типа через `autoType()` в Builder
