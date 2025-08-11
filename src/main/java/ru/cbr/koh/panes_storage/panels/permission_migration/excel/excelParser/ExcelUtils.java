package ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser.FileReader.*;

/**
 * Утилитный класс для работы с Excel файлами
 */
public final class ExcelUtils {

    private static final Pattern POLITIC_NUMBER_PATTERN = Pattern.compile("\\b\\d+\\b");

    private ExcelUtils() {
        // Утилитный класс
    }

    /**
     * Получает значение ячейки как строку
     */
    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            default -> cell.getStringCellValue();
        };
    }

    /**
     * Находит последнее число в строке
     */
    public static NumberPosition findLastNumber(String value) {
        var matcher = POLITIC_NUMBER_PATTERN.matcher(value);

        String lastNumber = null;
        int lastIndex = -1;

        while (matcher.find()) {
            lastNumber = matcher.group();
            lastIndex = matcher.start();
        }

        return new NumberPosition(lastNumber, lastIndex);
    }

    /**
     * Кеширует политики из листа
     */
    public static Map<Integer, String> cachePolitics(Sheet politicsSheet) {
        Map<Integer, String> politicsCache = new HashMap<>();

        for (Row row : politicsSheet) {
            if (row.getRowNum() < 3) {
                continue;
            }
            var cellValue = getCellValue(row.getCell(0));
            if (cellValue != null && !cellValue.isBlank()) {
                Integer number = Integer.parseInt(cellValue.replace(".0", "").trim());
                String politic = getCellValue(row.getCell(1));

                if (!politic.isEmpty()) {
                    politicsCache.put(number, politic);
                }
            }
        }

        return politicsCache;
    }

    /**
     * Кеширует данные матрицы распределения прав
     */
    public static Map<Integer, TreeTypeData> cacheTreeTypes(Sheet matrixSheet) {
        Map<Integer, TreeTypeData> treeTypesCache = new HashMap<>();

        for (Row row : matrixSheet) {
            int rowNum = row.getRowNum();

            boolean hasKO = getCellValue(row.getCell(MATRIX_CO_COLUMN_NUMBER)).contains(MATRIX_TREE_SYMBOL);
            boolean hasGIBR = getCellValue(row.getCell(MATRIX_GIBR_COLUMN_NUMBER)).contains(MATRIX_TREE_SYMBOL);

            if ((!hasGIBR) && (!hasKO)) {
                hasKO = getCellValue(row.getCell(MATRIX_CO_COLUMN_NUMBER - 2)).contains(MATRIX_TREE_SYMBOL);
                hasGIBR = getCellValue(row.getCell(MATRIX_GIBR_COLUMN_NUMBER - 2)).contains(MATRIX_TREE_SYMBOL);
            }

            treeTypesCache.put(rowNum, new TreeTypeData(hasKO, hasGIBR));
        }

        return treeTypesCache;
    }

    /**
     * Очищает значение от служебных символов
     */
    public static String cleanValue(String value) {
        if (value == null) {
            return "";
        }

        String cleaned = value.trim();
        if (cleaned.startsWith("**")) {
            cleaned = cleaned.substring(2).trim();
        }

        return cleaned.replace("/n", "&#13;&#10;");
    }

    /**
     * Данные о позиции числа в строке
     */
    public record NumberPosition(String number, int position) {
    }

    /**
     * Данные о типах дерева для строки
     */
    public record TreeTypeData(boolean hasKO, boolean hasGIBR) {
    }
}
