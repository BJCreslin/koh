package ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

import java.util.HashMap;
import java.util.Map;

public class ProfileHeaderManager {

    private final Map<Integer, Profile> headerMap = new HashMap<>();

    ProfileHeaderManager(Sheet treeSheet, int profileStartColumn, int profileRowNumber) {
        Row row = treeSheet.getRow(profileRowNumber);
        int shift = 0;
        while (true) {
            var cellValue = ExcelUtils.getCellValue(row.getCell(profileStartColumn + shift));
            if (cellValue.isBlank()) {
                break;
            }

            Profile profile = Profile.getProfileByName(cellValue);
            if (profile != null) {
                headerMap.put(shift, profile);
            }
            shift++;
        }
    }

    public Profile getProfile(int shift) {
        return headerMap.get(shift);
    }
}

