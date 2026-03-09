package ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser;

import org.apache.commons.math3.util.Pair;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.PermissionType;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.TreeType;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FileReader {

    private static final int TOP_SPACE = 6;

    public static final int NAME_COLUMN_NUMBER = 10;
    public static final int NEED_SAVE_COLUMN_NUMBER = 28;
    public static final String BANK_DEPENDENT = "**";
    public static final String INCLUDE_ROW_SYMBOL = "i";

    private final File file;
    private final char rowSelector;
    private final int profileStartColumn;
    private final DataFormatter dataFormatter = new DataFormatter(Locale.ROOT);

    public FileReader(File file, char rowSelector, int profileStartColumn) {
        this.file = file;
        this.rowSelector = rowSelector;
        this.profileStartColumn = profileStartColumn;
    }

    public List<Permission> read() {
        List<Permission> permissionDialogObjects = new ArrayList<>();

        try (InputStream inputStream = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet treeSheet = workbook.getSheet("Дерево");
            if (treeSheet == null) {
                throw new IllegalArgumentException("Лист \"Дерево\" не найден в выбранном Excel-файле");
            }

            var valueFinder = new ValueFinder();
            var keysStack = new KeysStack();
            var profileHeaderManager = new ProfileHeaderManager(treeSheet, profileStartColumn);
            for (Row row : treeSheet) {
                if (row.getRowNum() < TOP_SPACE) {
                    continue;
                }

                ValueShiftPair valueShiftPair = valueFinder.find(row);
                if (valueShiftPair == null) {
                    continue;
                }

                var value = valueShiftPair.value();
                var bankDependent = value.startsWith(BANK_DEPENDENT);
                if (bankDependent) {
                    value = value.replace(BANK_DEPENDENT, "").trim();
                    valueShiftPair = new ValueShiftPair(valueShiftPair.shift(), value);
                }

                Pair<String, Integer> politicNumber = getPoliticNumber(value);

                if (politicNumber.getFirst() != null) {
                    value = (value.substring(0, politicNumber.getSecond())
                            + value.substring(politicNumber.getSecond() + politicNumber.getFirst().length())).trim();
                    valueShiftPair = new ValueShiftPair(valueShiftPair.shift(), value);
                }

                keysStack.push(valueShiftPair);
                String key = keysStack.getKey();
                String relKey = valueShiftPair.value();

                if (!key.isBlank()) {
                    String politic = getPolitic(workbook, politicNumber);
                    List<Profile> profiles = getProfiles(profileHeaderManager, row);
                    String name = getCellValue(row.getCell(NAME_COLUMN_NUMBER));
                    String description = getDescription(row);
                    List<TreeType> types = getTreeType(workbook, row.getRowNum());
                    if (isNeedSave(row)) {
                        permissionDialogObjects.add(
                                new Permission(
                                        key,
                                        PermissionType.getPermissionType(relKey),
                                        politic,
                                        bankDependent ? getBankPolitic(key) : "userAction",
                                        name,
                                        profiles,
                                        description,
                                        types));
                    }
                }
            }
            return permissionDialogObjects;

        } catch (IOException e) {
            throw new IllegalStateException("Не удалось прочитать Excel-файл: " + file.getAbsolutePath(), e);
        }
    }

    private String getBankPolitic(String key) {
        return "GET_KO_LIST_" + key.replaceAll("[#-]", "_").toUpperCase(Locale.ROOT);
    }

    private boolean isNeedSave(Row row) {
        Cell cell = row.getCell(NEED_SAVE_COLUMN_NUMBER);
        String cellValue = getCellValue(cell);
        return cellValue.equalsIgnoreCase(String.valueOf(rowSelector));
    }

    private List<TreeType> getTreeType(Workbook workbook, int rowNumber) {
        Sheet sheet = workbook.getSheet("Матрица распределения прав АД");
        List<TreeType> types = new ArrayList<>();
        if (sheet == null) {
            return types;
        }
        for (Row row : sheet) {
            if (row.getRowNum() < rowNumber) {
                continue;
            }
            String cellValue = getCellValue(row.getCell(12));
            if (cellValue.contains("+")) {
                types.add(TreeType.KO);
            }
            cellValue = getCellValue(row.getCell(13));
            if (cellValue.contains("+")) {
                types.add(TreeType.GIBR);
            }
            break;
        }
        return types;
    }

    private String getDescription(Row row) {
        String description = getCellValue(row.getCell(9));
        if (description == null || description.isBlank()) {
            return null;
        }
        return description.replace("\n", " &#13;&#10;");
    }

    private String getPolitic(Workbook workbook, Pair<String, Integer> politicNumber) {
        if (politicNumber.getFirst() == null) {
            return "";
        }

        Double policyNumber = tryParseDouble(politicNumber.getFirst());
        if (policyNumber == null) {
            return "";
        }

        Sheet sheet = workbook.getSheet("Политики");
        if (sheet == null) {
            return "";
        }
        for (Row row : sheet) {
            if (row.getRowNum() < 3) {
                continue;
            }
            String cellValue = getCellValue(row.getCell(0));
            Double currentPolicyNumber = tryParseDouble(cellValue);
            if (currentPolicyNumber != null && Double.compare(currentPolicyNumber, policyNumber) == 0) {
                return getCellValue(row.getCell(1));
            }
        }
        return "";
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        return dataFormatter.formatCellValue(cell).trim();
    }

    private Pair<String, Integer> getPoliticNumber(String value) {
        String regex = "\\b\\d+\\b";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(value);

        String lastNumber = null;
        int lastIndex = -1;

        while (matcher.find()) {
            lastNumber = matcher.group();
            lastIndex = matcher.start();
        }
        return new Pair(lastNumber, lastIndex);
    }

    private List<Profile> getProfiles(ProfileHeaderManager profileHeaderManager, Row row) {
        List<Profile> profiles = new ArrayList<>();
        for (int i = 0; i < Profile.values().length; i++) {
            String cell = getCellValue(row.getCell(profileStartColumn + i));
            if ("+".equals(cell)) {
                Profile profile = profileHeaderManager.getProfile(i);
                if (profile != null) {
                    profiles.add(profile);
                }
            }
        }
        return profiles;
    }

    private Double tryParseDouble(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }
}
