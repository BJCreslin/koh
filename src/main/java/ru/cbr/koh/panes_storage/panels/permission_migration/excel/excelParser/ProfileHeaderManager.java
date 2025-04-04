package ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

import java.util.HashMap;
import java.util.Map;

public class ProfileHeaderManager {

    private final Map<Integer, Profile> headerMap = new HashMap<>();


    private final int profileRowNumber = 3;

    ProfileHeaderManager(Sheet treeSheet, int profileStartColumn) {
        Row row = treeSheet.getRow(profileRowNumber);
        int shift = 0;
        while (true) {
            var cellValue = ValueFinder.getCellValue(row.getCell(profileStartColumn + shift));
            if (cellValue == null || cellValue.isBlank()) {
                break;
            }

            if (Profile.getProfileByName(cellValue) != null) {
                headerMap.put(shift, Profile.getProfileByName(cellValue));
            }
            shift++;
        }
    }

    public Profile getProfile(int shift) {
        return headerMap.get(shift);
    }
}

