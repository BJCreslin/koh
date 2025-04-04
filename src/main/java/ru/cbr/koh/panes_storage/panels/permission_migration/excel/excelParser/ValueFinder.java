package ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ValueFinder {

    ValueShiftPair find(Row row) {
        for (Cell cell : row) {
            String value = getCellValue(cell);
            if (value != null && !value.isEmpty()) {
                return new ValueShiftPair(cell.getColumnIndex(), value);
            }
        }
        return null;
    }

    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            default -> cell.getStringCellValue();
        };
    }
}
