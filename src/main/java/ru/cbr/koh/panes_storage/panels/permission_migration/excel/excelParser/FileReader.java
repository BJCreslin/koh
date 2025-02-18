package ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser;

import org.apache.commons.math3.util.Pair;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.dialog_objects.PermissionDialogObject;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.PermissionType;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.TreeType;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class FileReader {

    public static final int NAME_COLUMN_NUMBER = 10;
    public static final String BANK_DEPENDENT = "**";
    public static final String IGNORE_ROW_SYMBOL = "i";
    private static final String permissionsFileName = "permissions.txt";

    private final File file;

    List<ParserPermission> parserPermissions = new ArrayList<>();

    List<Permission> permissionDialogObjects = new ArrayList<>();

    public FileReader(File file) {
        this.file = file;
    }

    public List<Permission> read() {
        try (InputStream inputStream = new FileInputStream(file)) {

            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet("Дерево");

            var valueFinder = new ValueFinder();
            var keysStack = new KeysStack();
            for (Row row : sheet) {
                if (row.getRowNum() < 7) {
                    continue;
                }

                ValueShiftPair valueShiftPair = valueFinder.find(row);
                if (valueShiftPair == null) {
                    break;
                }

                var value = valueShiftPair.value();
                var bankDependent = value.startsWith(BANK_DEPENDENT);
                if (bankDependent) {
                    value = value.replace(BANK_DEPENDENT, "").trim();
                    valueShiftPair = new ValueShiftPair(valueShiftPair.shift(), value);
                }

                Pair<String, Integer> politicNumber = getPoliticNumber(value);

                if (politicNumber.getFirst() != null) {
                    value =
                            (value.substring(0, politicNumber.getSecond()) + value.substring(politicNumber.getSecond() + politicNumber.getFirst().length())).trim();
                    valueShiftPair = new ValueShiftPair(valueShiftPair.shift(), value);
                }

                keysStack.push(valueShiftPair);
                String key = keysStack.getKey();

                String relKey = valueShiftPair.value();

                System.out.println(key);

                if (!key.isBlank() && !key.isEmpty()) {
                    String politic = getPolitic(workbook, politicNumber);
                    List<Profile> profiles = getProfiles(row);
                    String name = getCellValue(row.getCell(NAME_COLUMN_NUMBER));
                    String description = getDescription(row);
                    List<TreeType> types = getTreeType(workbook, row.getRowNum());
                    var needSave = isNeedSave(row);

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
            return permissionDialogObjects;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getBankPolitic(String key) {
         return "ПРидумать политику";
    }

    private boolean isNeedSave(Row row) {
        Cell cell = row.getCell(28);
        String cellValue = getCellValue(cell);
        return cellValue == null || !cellValue.equalsIgnoreCase(IGNORE_ROW_SYMBOL);
    }

    private List<TreeType> getTreeType(Workbook workbook, int rowNumber) {
        Sheet sheet = workbook.getSheet("Матрица распределения прав АД");
        List<TreeType> types = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() < rowNumber) {
                continue;
            }
            String cellvalue = getCellValue(row.getCell(12));
            if (cellvalue.contains("+")) {
                types.add(TreeType.KO);
            }
            cellvalue = getCellValue(row.getCell(13));
            if (cellvalue.contains("+")) {
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
        Sheet sheet = workbook.getSheet("Политики");
        for (Row row : sheet) {
            if (row.getRowNum() < 3) {
                continue;
            }
            String cellvalue = getCellValue(row.getCell(0));
            if (politicNumber.getFirst() != null && cellvalue != null && Double.compare(Double.parseDouble(cellvalue),
                    (Double.parseDouble(politicNumber.getFirst()))) == 0) {
                return getCellValue(row.getCell(1));
            }
        }
        return "";
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            default -> cell.getStringCellValue();
        };
    }

    private Pair<String, Integer> getPoliticNumber(String value) {
        String regex = "\\b\\d+\\b";
        // Найти все числа
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(value);

        String lastNumber = null;
        int lastIndex = -1;

        // Итерация по всем найденным числам
        while (matcher.find()) {
            lastNumber = matcher.group();
            lastIndex = matcher.start();
        }
        return new Pair(lastNumber, lastIndex);
    }

    private List<Profile> getProfiles(Row row) {
        List<Profile> profiles = new ArrayList<>();
        int startColumn = 11;
        for (int i = 0; i < 17; i++) {
            String cell = getCellValue(row.getCell(startColumn + i));
            if (cell != null && cell.equals("+")) {
                profiles.add(Profile.getProfileById(i));
            }
        }
        return profiles;
    }

}
