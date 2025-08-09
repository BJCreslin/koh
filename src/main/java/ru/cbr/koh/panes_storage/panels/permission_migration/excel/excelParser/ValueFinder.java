package ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ValueFinder {

    ValueShiftPair find(Row row) {
        for (Cell cell : row) {
            String value = ExcelUtils.getCellValue(cell);
            if (!value.isEmpty()) {
                return new ValueShiftPair(cell.getColumnIndex(), value);
            }
        }
        return null;
    }
}
