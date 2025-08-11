package ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.cbr.koh.exceptions.ExcelParsingException;
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
import java.util.Map;


public class FileReader {

    private static final Logger logger = LogManager.getLogger(FileReader.class);
    private static final int TOP_SPACE = 6;

    public static final int NAME_COLUMN_NUMBER = 10;
    public static final int NEED_SAVE_COLUMN_NUMBER = 30;
    public static final Integer ORDER_COLUMN_NUMBER = 12;
    public static final int PROFILE_COLUMN_NUMBER = 14;
    public static final int PROFILE_ROW_NUMBER = 3;

    public static final int MATRIX_CO_COLUMN_NUMBER = 14;
    public static final int MATRIX_GIBR_COLUMN_NUMBER = 15;
    public static final String MATRIX_TREE_SYMBOL = "+";


    public static final String BANK_DEPENDENT_SYMBOLS = "**";
    public static final String INCLUDE_ROW_SYMBOL = "i";

    private final File file;

    private final char rowSelector;
    private final int profileStartColumn;

    // Кеши для оптимизации производительности
    private Map<Integer, String> politicsCache;
    private Map<Integer, ExcelUtils.TreeTypeData> treeTypesCache;

    private final List<Permission> permissionDialogObjects = new ArrayList<>();

    public FileReader(File file, char rowSelector, int profileStartColumn) {
        this.file = file;
        this.rowSelector = rowSelector;
        this.profileStartColumn = profileStartColumn;
    }

    public List<Permission> read() throws ExcelParsingException {
        if (file == null || !file.exists()) {
            throw new ExcelParsingException("Файл не существует или не указан: " + (file != null ? file.getPath() : "null"));
        }

        try (InputStream inputStream = new FileInputStream(file)) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet treeSheet = workbook.getSheet("Дерево");

            if (treeSheet == null) {
                throw new ExcelParsingException("Лист 'Дерево' не найден в файле: " + file.getPath());
            }

            // Инициализация кешей для оптимизации
            initializeCaches(workbook);

            var valueFinder = new ValueFinder();
            var keysStack = new KeysStack();
            var profileHeaderManager = new ProfileHeaderManager(treeSheet, PROFILE_COLUMN_NUMBER, PROFILE_ROW_NUMBER);

            for (Row row : treeSheet) {
                if (row.getRowNum() < TOP_SPACE) {
                    continue;
                }

                try {
                    ValueShiftPair valueShiftPair = valueFinder.find(row);
                    if (valueShiftPair == null) {
                        continue;
                    }

                    var value = valueShiftPair.value();
                    var isBankDependent = value.startsWith(BANK_DEPENDENT_SYMBOLS);
                    if (isBankDependent) {
                        value = value.substring(2).trim();
                        valueShiftPair = new ValueShiftPair(valueShiftPair.shift(), value);
                    }

                    ExcelUtils.NumberPosition politicNumber = ExcelUtils.findLastNumber(value);

                    if (politicNumber.number() != null) {
                        value = (value.substring(0, politicNumber.position()) +
                                value.substring(politicNumber.position() + politicNumber.number().length())).trim();
                        valueShiftPair = new ValueShiftPair(valueShiftPair.shift(), value);
                    }

                    keysStack.push(valueShiftPair);
                    String key = keysStack.getKey();
                    logger.debug("Processing key: {}", key);

                    String relKey = valueShiftPair.value();

                    if (!key.isBlank() && isNeedSave(row)) {
                        String politic = getPolitic(politicNumber);
                        List<Profile> profiles = getProfiles(profileHeaderManager, row);
                        String name = ExcelUtils.getCellValue(row.getCell(NAME_COLUMN_NUMBER));
                        String description = getDescription(row);
                        List<TreeType> types = getTreeType(row.getRowNum());
                        int order = getOrder(row);
                        logger.info("Saving permission with key: {}", key);
                        permissionDialogObjects.add(
                                new Permission(
                                        key,
                                        PermissionType.getPermissionType(relKey),
                                        politic,
                                        isBankDependent ? getBankPolitic(key) : "userAction",
                                        name,
                                        profiles,
                                        description,
                                        types,
                                        order));
                    }
                } catch (Exception e) {
                    logger.error("Ошибка обработки строки {}: {}", row.getRowNum(), e.getMessage(), e);
                    throw new ExcelParsingException("Ошибка обработки строки " + row.getRowNum() + " в файле " + file.getPath(), e);
                }
            }
            return permissionDialogObjects;

        } catch (IOException e) {
            logger.error("Ошибка чтения файла: {}", file.getPath(), e);
            throw new ExcelParsingException("Не удалось прочитать Excel файл: " + file.getPath(), e);
        } catch (Exception e) {
            if (e instanceof ExcelParsingException) {
                throw e;
            }
            logger.error("Неожиданная ошибка при парсинге файла: {}", file.getPath(), e);
            throw new ExcelParsingException("Неожиданная ошибка при парсинге файла: " + file.getPath(), e);
        }
    }

    private int getOrder(Row row) {
        if (ORDER_COLUMN_NUMBER == null) {
            return 10;
        }
        if (row.getCell(ORDER_COLUMN_NUMBER) == null) {
            return 10;
        } else {
            return ExcelUtils.getCellValueAsInt(row.getCell(ORDER_COLUMN_NUMBER), 10);
        }
    }

    /**
     * Инициализирует кеши для оптимизации производительности
     */
    private void initializeCaches(Workbook workbook) {
        // Кеширование политик
        Sheet politicsSheet = workbook.getSheet("Политики");
        if (politicsSheet != null) {
            politicsCache = ExcelUtils.cachePolitics(politicsSheet);
        } else {
            politicsCache = Map.of();
        }

        // Кеширование типов дерева
        Sheet matrixSheet = workbook.getSheet("Матрица распределения прав АД");
        if (matrixSheet != null) {
            treeTypesCache = ExcelUtils.cacheTreeTypes(matrixSheet);
        } else {
            treeTypesCache = Map.of();
        }
    }

    private String getBankPolitic(String key) {
        return "GET_KO_LIST_" + key.replaceAll("[#-]", "_").toUpperCase(Locale.ROOT);
    }

    private boolean isNeedSave(Row row) {
        Cell cell = row.getCell(NEED_SAVE_COLUMN_NUMBER);
        String cellValue = ExcelUtils.getCellValue(cell);
        return !cellValue.isEmpty() && (cellValue.equalsIgnoreCase(String.valueOf(rowSelector)) || cellValue.equalsIgnoreCase(INCLUDE_ROW_SYMBOL));
    }

    private List<TreeType> getTreeType(int rowNumber) {
        List<TreeType> types = new ArrayList<>();

        ExcelUtils.TreeTypeData data = treeTypesCache.get(rowNumber);
        if (data != null) {
            if (data.hasKO()) {
                types.add(TreeType.KO);
            }
            if (data.hasGIBR()) {
                types.add(TreeType.GIBR);
            }
        }

        return types;
    }


    private String getDescription(Row row) {
        String description = ExcelUtils.getCellValue(row.getCell(9));
        if (description.isBlank()) {
            return null;
        }
        return description.replace("\n", " &#13;&#10;");
    }

    private String getPolitic(ExcelUtils.NumberPosition politicNumber) {
        if (politicNumber.number() == null) {
            return "";
        }

        return politicsCache.getOrDefault(politicNumber.number(), "");
    }

    private List<Profile> getProfiles(ProfileHeaderManager profileHeaderManager, Row row) {
        List<Profile> profiles = new ArrayList<>();
        for (int i = 0; i < Profile.values().length; i++) {
            String cell = ExcelUtils.getCellValue(row.getCell(profileStartColumn + i));
            if ("+".equals(cell)) {
                Profile profile = profileHeaderManager.getProfile(i);
                if (profile != null) {
                    profiles.add(profile);
                }
            }
        }
        return profiles;
    }

}
