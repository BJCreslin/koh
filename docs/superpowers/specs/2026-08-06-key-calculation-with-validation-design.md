# Алгоритм расчёта ключа элемента с валидацией

**Дата:** 2026-08-06
**Статус:** Утверждён к реализации

## Цель

Изменить алгоритм расчёта ключа элемента (`secur_elem.key`):
1. **Всегда** вычислять ключ на основе позиции в дереве через `KeysStack`.
2. Сравнивать вычисленный ключ с ключом из столбца Excel (столбец L).
3. При обнаружении расхождений запрашивать у пользователя выбор через модальный диалог.

## Текущая проблема

`FileReader` в текущей реализации:
- Если ключ в столбце L задан → использует его напрямую
- Если ключ пуст → вычисляет через `KeysStack`

Это приводит к тому, что ручные ошибки в Excel (опечатки, расхождения с деревом) проходят незамеченными в итоговый XML.

## Требования

### Функциональные требования

1. **FR-1**: `KeysStack` всегда вычисляет ключ для каждой строки дерева.
2. **FR-2**: `KeyCandidate` хранит три значения: `excelKey` (опционально), `computedKey` (обязательно), `selectedKey` (результат выбора).
3. **FR-3**: Если `excelKey` отсутствует — автоматически использовать `computedKey` без диалога.
4. **FR-4**: Если `excelKey == computedKey` — использовать общий ключ без диалога.
5. **FR-5**: Если `excelKey != computedKey` — показать модальный диалог `KeyConflictDialog` с выбором.
6. **FR-6**: Диалог должен отображать:
   - Имя элемента (для контекста)
   - Вычисленный ключ (как рекомендация)
   - Ключ из Excel
   - Кнопки: "Использовать из Excel", "Вычисленный", "Отмена"
7. **FR-7**: При нажатии "Отмена" обработка файла прерывается полностью.
8. **FR-8**: После выбора пользователя `selectedKey` используется во всём дальнейшем pipeline (XML, ChangeLog).

### Нефункциональные требования

1. **NFR-1**: Диалог должен быть модальным (`APPLICATION_MODAL`), блокировать родительское окно.
2. **NFR-2**: Изменения минимально инвазивны — не ломают существующих потребителей `Permission`.
3. **NFR-3**: Код парсинга Excel остаётся независимым от UI (Swing).
4. **NFR-4**: Логика резолвинга покрыта юнит-тестами.

## Архитектура

### Изменённый поток данных

```
Excel ──> FileReader.read() ──> List<Permission { KeyCandidate }> ──> MigrationService
                                                                            │
                                                                            ├─ validateKeys()
                                                                            │     │
                                                                            │     └─> KeyConflictDialog (Swing, EDT)
                                                                            │            │
                                                                            │            └─ выбор пользователя
                                                                            │
                                                                            ▼
                                                                       Готовый List<Permission>
                                                                            │
                                                                            ▼
                                                                       ChangeLog ──> XML
```

### Новые компоненты

#### 1. `KeyCandidate` (новый класс)

```java
package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.key;

public class KeyCandidate {
    private final String excelKey;       // из столбца L Excel (может быть null)
    private final String computedKey;    // из KeysStack (всегда не null)
    private String selectedKey;          // результат выбора пользователя
    private boolean userApproved;        // флаг "выбрано пользователем"

    public KeyCandidate(String excelKey, String computedKey);

    public String resolveKey();
    public boolean hasConflict();
    public void setSelectedKey(String key);
    public String getSelectedKey();
    public String getComputedKey();
    public String getExcelKey();
}
```

**Логика `resolveKey()`:**
- Если `selectedKey != null` → вернуть `selectedKey`
- Иначе если `excelKey == null` → вернуть `computedKey`
- Иначе если `excelKey.equals(computedKey)` → вернуть `excelKey`
- Иначе → вернуть `computedKey` (fallback до диалога)

**Логика `hasConflict()`:**
- Вернуть `true` если `excelKey != null && !excelKey.equals(computedKey)`
- Иначе `false`

#### 2. `KeyConflictDialog` (новый класс)

```java
package ru.cbr.koh.panes_storage.panels.permission_migration.permission.dialog;

public final class KeyConflictDialog {
    public enum Result { USE_EXCEL, USE_COMPUTED, CANCEL }

    public static Result show(Window parent, Permission permission);
}
```

**UI:**
- `JDialog` с `ModalityType.APPLICATION_MODAL`
- Заголовок: "Конфликт ключа элемента"
- Текстовые поля (нередактируемые): вычисленный ключ, ключ из Excel
- Метка с именем элемента и номером строки
- Кнопки: "Использовать из Excel", "Вычисленный", "Отмена"

### Изменённые компоненты

#### `Permission` (модификация)

- Поле `key` заменяется на `KeyCandidate keyCandidate`
- Все геттеры/конструкторы обновляются
- Метод `getKey()` возвращает `keyCandidate.resolveKey()` для обратной совместимости
- Метод `getKeyCandidate()` для доступа к новой логике

#### `FileReader` (модификация)

- Удалить условное использование ключа из Excel
- Всегда вычислять через `KeysStack` (`keysStack.getKey()`)
- Сохранять `excelKey` и `computedKey` в `KeyCandidate`
- Использовать `KeyCandidate` в новом `Permission`

#### `MigrationService` (модификация)

- После `fileReader.read()` вызывать `validateKeys()`
- Метод `validateKeys()` проходит по списку `Permission`, для каждого с конфликтом показывает `KeyConflictDialog`
- Если `CANCEL` — прерывает обработку
- Если выбор сделан — обновляет `KeyCandidate.selectedKey` и `userApproved = true`

#### Потребители `Permission.getKey()`

Все места, где используется `permission.getKey()` (например, `SecureElemMigration`, `TreeMigration`), продолжают работать без изменений благодаря обратной совместимости через `resolveKey()`.

## Тестирование

### Юнит-тесты `KeyCandidate`

```java
class KeyCandidateTest {
    @Test void resolveKey_whenExcelKeyIsNull_returnsComputedKey();
    @Test void resolveKey_whenKeysMatch_returnsCommonKey();
    @Test void resolveKey_whenKeysDifferAndSelected_returnsSelected();
    @Test void resolveKey_whenKeysDifferAndNotSelected_returnsComputedKey();
    @Test void hasConflict_returnsTrue_whenBothKeysPresentAndDifferent();
    @Test void hasConflict_returnsFalse_whenExcelKeyIsNull();
    @Test void hasConflict_returnsFalse_whenKeysMatch();
}
```

### Ручная проверка

- Загрузить реальный Excel-файл с конфликтами ключей
- Убедиться, что диалог показывается для каждого конфликта
- Проверить корректность XML-вывода после выбора

## Файлы, которые будут изменены/созданы

**Создать:**
- `src/main/java/.../permission/domain/key/KeyCandidate.java`
- `src/main/java/.../permission/dialog/KeyConflictDialog.java`
- `src/test/java/.../permission/domain/key/KeyCandidateTest.java`

**Изменить:**
- `src/main/java/.../permission/domain/Permission.java`
- `src/main/java/.../excel/excelParser/FileReader.java`
- `src/main/java/.../MigrationService.java` (найти существующий)
- `src/main/java/.../OnlyMigrationApplication.java`

**Не требуют изменений (обратная совместимость):**
- `SecureElemMigration.java`
- `TreeMigration.java`
- `ChangeLog.java`

## Зависимости

- Использует существующий `KeysStack` без изменений.
- Использует существующий `PermissionDialogObject` без изменений.
- Не вводит новых сторонних библиотек.

## Ограничения

- Диалог блокирует EDT — при очень большом количестве конфликтов (>100) обработка будет долгой. Это приемлемо для текущих Excel-файлов (десятки-сотни строк).
- Решение пользователя не сохраняется между сессиями — при каждой загрузке Excel диалоги показываются заново (если есть конфликты).
