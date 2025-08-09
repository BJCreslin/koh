package ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.exceptions.ExcelParsingException;

import java.io.File;
import java.util.List;

/**
 * Класс для сравнения производительности оригинального и оптимизированного парсеров
 */
public class PerformanceBenchmark {
    
    private static final Logger logger = LogManager.getLogger(PerformanceBenchmark.class);
    
    public static class BenchmarkResult {
        private final String parserName;
        private final long executionTime;
        private final int permissionsCount;
        private final boolean success;
        private final String errorMessage;
        
        public BenchmarkResult(String parserName, long executionTime, int permissionsCount, boolean success, String errorMessage) {
            this.parserName = parserName;
            this.executionTime = executionTime;
            this.permissionsCount = permissionsCount;
            this.success = success;
            this.errorMessage = errorMessage;
        }
        
        public String getParserName() { return parserName; }
        public long getExecutionTime() { return executionTime; }
        public int getPermissionsCount() { return permissionsCount; }
        public boolean isSuccess() { return success; }
        public String getErrorMessage() { return errorMessage; }
        
        @Override
        public String toString() {
            if (!success) {
                return String.format("%s: FAILED - %s", parserName, errorMessage);
            }
            return String.format("%s: %d мс, %d разрешений (%.2f мс/разрешение)", 
                    parserName, executionTime, permissionsCount, 
                    permissionsCount > 0 ? (double) executionTime / permissionsCount : 0);
        }
    }
    
    /**
     * Запускает бенчмарк для сравнения производительности парсеров
     */
    public static void runBenchmark(File excelFile, char rowSelector, int profileStartColumn, int iterations) {
        logger.info("Запуск бенчмарка для файла: {}", excelFile.getPath());
        logger.info("Количество итераций: {}", iterations);
        
        BenchmarkResult originalResult = benchmarkParser("Оригинальный FileReader", () -> {
            FileReader reader = new FileReader(excelFile, rowSelector, profileStartColumn);
            return reader.read();
        }, iterations);
        
        BenchmarkResult optimizedSequentialResult = benchmarkParser("Оптимизированный (последовательный)", () -> {
            OptimizedFileReader reader = new OptimizedFileReader(excelFile, rowSelector, profileStartColumn, false);
            return reader.read();
        }, iterations);
        
        BenchmarkResult optimizedParallelResult = benchmarkParser("Оптимизированный (параллельный)", () -> {
            OptimizedFileReader reader = new OptimizedFileReader(excelFile, rowSelector, profileStartColumn, true);
            return reader.read();
        }, iterations);
        
        // Вывод результатов
        logger.info("=== РЕЗУЛЬТАТЫ БЕНЧМАРКА ===");
        logger.info(originalResult.toString());
        logger.info(optimizedSequentialResult.toString());
        logger.info(optimizedParallelResult.toString());
        
        // Расчет улучшений
        if (originalResult.isSuccess() && optimizedSequentialResult.isSuccess()) {
            double sequentialImprovement = (double) originalResult.getExecutionTime() / optimizedSequentialResult.getExecutionTime();
            logger.info("Улучшение производительности (последовательный): {:.2f}x", sequentialImprovement);
        }
        
        if (originalResult.isSuccess() && optimizedParallelResult.isSuccess()) {
            double parallelImprovement = (double) originalResult.getExecutionTime() / optimizedParallelResult.getExecutionTime();
            logger.info("Улучшение производительности (параллельный): {:.2f}x", parallelImprovement);
        }
    }
    
    private static BenchmarkResult benchmarkParser(String parserName, ParserFunction parser, int iterations) {
        logger.info("Тестирование: {}", parserName);
        
        long totalTime = 0;
        int totalPermissions = 0;
        boolean success = true;
        String errorMessage = null;
        
        for (int i = 0; i < iterations; i++) {
            try {
                long startTime = System.currentTimeMillis();
                List<Permission> permissions = parser.parse();
                long endTime = System.currentTimeMillis();
                
                totalTime += (endTime - startTime);
                totalPermissions = permissions.size(); // Берем размер из последней итерации
                
                logger.debug("Итерация {}: {} мс, {} разрешений", i + 1, endTime - startTime, permissions.size());
                
            } catch (Exception e) {
                logger.error("Ошибка в {} на итерации {}: {}", parserName, i + 1, e.getMessage(), e);
                success = false;
                errorMessage = e.getMessage();
                break;
            }
        }
        
        long averageTime = success ? totalTime / iterations : 0;
        return new BenchmarkResult(parserName, averageTime, totalPermissions, success, errorMessage);
    }
    
    @FunctionalInterface
    private interface ParserFunction {
        List<Permission> parse() throws ExcelParsingException;
    }
    
    /**
     * Простой тест для проверки корректности оптимизаций
     */
    public static boolean validateOptimizations(File excelFile, char rowSelector, int profileStartColumn) {
        try {
            FileReader originalReader = new FileReader(excelFile, rowSelector, profileStartColumn);
            List<Permission> originalResults = originalReader.read();
            
            OptimizedFileReader optimizedReader = new OptimizedFileReader(excelFile, rowSelector, profileStartColumn, false);
            List<Permission> optimizedResults = optimizedReader.read();
            
            if (originalResults.size() != optimizedResults.size()) {
                logger.error("Количество разрешений не совпадает: {} vs {}", 
                        originalResults.size(), optimizedResults.size());
                return false;
            }
            
            // Простая проверка - сравниваем ключи первых нескольких разрешений
            int checkCount = Math.min(10, originalResults.size());
            for (int i = 0; i < checkCount; i++) {
                Permission original = originalResults.get(i);
                Permission optimized = optimizedResults.get(i);
                
                if (!original.getKey().equals(optimized.getKey())) {
                    logger.error("Ключи не совпадают на позиции {}: '{}' vs '{}'", 
                            i, original.getKey(), optimized.getKey());
                    return false;
                }
            }
            
            logger.info("Валидация прошла успешно. Количество разрешений: {}", originalResults.size());
            return true;
            
        } catch (Exception e) {
            logger.error("Ошибка при валидации: {}", e.getMessage(), e);
            return false;
        }
    }
}
