package ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import java.util.Locale;

public class ValueFinder {

    private static final DataFormatter DATA_FORMATTER = new DataFormatter(Locale.ROOT);

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
        return DATA_FORMATTER.formatCellValue(cell).trim();
    }
}
