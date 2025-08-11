package ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.PermissionType;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.TreeType;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;
import ru.cbr.koh.exceptions.ExcelParsingException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Оптимизированная версия FileReader с поддержкой многопоточности и улучшенным кешированием
 */
public class OptimizedFileReader {

    private static final Logger logger = LogManager.getLogger(OptimizedFileReader.class);
    private static final int TOP_SPACE = 6;


    public static final int NAME_COLUMN_NUMBER = 10;
    public static final int NEED_SAVE_COLUMN_NUMBER = 28;
    public static final String BANK_DEPENDENT = "**";

    private final File file;
    private final char rowSelector;
    private final int profileStartColumn;
    private final boolean enableParallelProcessing;
    
    private Map<Integer, String> politicsCache;
    private Map<Integer, ExcelUtils.TreeTypeData> treeTypesCache;

    public OptimizedFileReader(File file, char rowSelector, int profileStartColumn) {
        this(file, rowSelector, profileStartColumn, true);
    }

    public OptimizedFileReader(File file, char rowSelector, int profileStartColumn, boolean enableParallelProcessing) {
        this.file = file;
        this.rowSelector = rowSelector;
        this.profileStartColumn = profileStartColumn;
        this.enableParallelProcessing = enableParallelProcessing;
    }

    public List<Permission> read() throws ExcelParsingException {
        if (file == null || !file.exists()) {
            throw new ExcelParsingException("Файл не существует или не указан: " + (file != null ? file.getPath() : "null"));
        }

        long startTime = System.currentTimeMillis();
        
        try (InputStream inputStream = new FileInputStream(file)) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet treeSheet = workbook.getSheet("Дерево");
            
            if (treeSheet == null) {
                throw new ExcelParsingException("Лист 'Дерево' не найден в файле: " + file.getPath());
            }
            
            initializeCaches(workbook);
            
            var profileHeaderManager = new ProfileHeaderManager(treeSheet, profileStartColumn);
            
            List<Permission> permissions = enableParallelProcessing ? 
                processRowsParallel(treeSheet, profileHeaderManager) :
                processRowsSequential(treeSheet, profileHeaderManager);
            
            long endTime = System.currentTimeMillis();
            logger.info("Обработано {} разрешений за {} мс", permissions.size(), endTime - startTime);
            
            return permissions;

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
    
    private List<Permission> processRowsSequential(Sheet treeSheet, ProfileHeaderManager profileHeaderManager) {
        List<Permission> permissions = new ArrayList<>();
        var valueFinder = new ValueFinder();
        var keysStack = new KeysStack();
        
        for (Row row : treeSheet) {
            if (row.getRowNum() < TOP_SPACE) {
                continue;
            }
            
            Permission permission = processRow(row, valueFinder, keysStack, profileHeaderManager);
            if (permission != null) {
                permissions.add(permission);
            }
        }
        
        return permissions;
    }
    
    private List<Permission> processRowsParallel(Sheet treeSheet, ProfileHeaderManager profileHeaderManager) {
        List<Row> rowsToProcess = new ArrayList<>();
        
        // Собираем строки для обработки
        for (Row row : treeSheet) {
            if (row.getRowNum() >= TOP_SPACE) {
                rowsToProcess.add(row);
            }
        }
        
        int numThreads = Math.min(Runtime.getRuntime().availableProcessors(), 4);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        
        try {
            List<CompletableFuture<List<Permission>>> futures = IntStream.range(0, numThreads)
                .mapToObj(threadIndex -> CompletableFuture.supplyAsync(() -> {
                    List<Permission> threadPermissions = new ArrayList<>();
                    var valueFinder = new ValueFinder();
                    var keysStack = new KeysStack();
                    
                    for (int i = threadIndex; i < rowsToProcess.size(); i += numThreads) {
                        Row row = rowsToProcess.get(i);
                        Permission permission = processRow(row, valueFinder, keysStack, profileHeaderManager);
                        if (permission != null) {
                            threadPermissions.add(permission);
                        }
                    }
                    
                    return threadPermissions;
                }, executor))
                .toList();
            
            // Собираем результаты
            List<Permission> allPermissions = new ArrayList<>();
            for (CompletableFuture<List<Permission>> future : futures) {
                allPermissions.addAll(future.get());
            }
            
            return allPermissions;
            
        } catch (Exception e) {
            logger.error("Ошибка при параллельной обработке", e);
            // Fallback к последовательной обработке
            return processRowsSequential(treeSheet, profileHeaderManager);
        } finally {
            executor.shutdown();
        }
    }
    
    private Permission processRow(Row row, ValueFinder valueFinder, KeysStack keysStack, ProfileHeaderManager profileHeaderManager) {
        try {
            ValueShiftPair valueShiftPair = valueFinder.find(row);
            if (valueShiftPair == null) {
                return null;
            }

            var value = valueShiftPair.value();
            var bankDependent = value.startsWith(BANK_DEPENDENT);
            if (bankDependent) {
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

            if (!key.isBlank() && !key.isEmpty() && isNeedSave(row)) {
                String politic = getPolitic(politicNumber);
                List<Profile> profiles = getProfiles(profileHeaderManager, row);
                String name = ExcelUtils.getCellValue(row.getCell(NAME_COLUMN_NUMBER));
                String description = getDescription(row);
                List<TreeType> types = getTreeType(row.getRowNum());
                
                logger.debug("Saving permission with key: {}", key);
                return new Permission(
                        key,
                        PermissionType.getPermissionType(relKey),
                        politic,
                        bankDependent ? getBankPolitic(key) : "userAction",
                        name,
                        profiles,
                        description,
                        types);
            }
            
            return null;
            
        } catch (Exception e) {
            logger.error("Ошибка обработки строки {}: {}", row.getRowNum(), e.getMessage(), e);
            return null;
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
        String cellValue = ExcelUtils.getCellValue(row.getCell(NEED_SAVE_COLUMN_NUMBER));
        return !cellValue.isEmpty() && cellValue.equalsIgnoreCase(String.valueOf(rowSelector));
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
