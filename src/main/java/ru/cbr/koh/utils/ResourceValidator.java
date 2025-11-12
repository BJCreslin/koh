package ru.cbr.koh.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Утилитный класс для валидации существования ресурсов
 */
public final class ResourceValidator {

    private static final Logger logger = LogManager.getLogger(ResourceValidator.class);

    /**
     * Список ресурсов обязательных для старта приложения
     **/
    private static final List<String> REQUIRED_RESOURCES = Arrays.asList(
            "config.properties",
            "changeLogTemplate.xml",
            "changeSetTemplate.xml",
            "template.xml",
            "spy.properties",
            "original_pom.txt",
            "replacement_pom.txt",
            "original_yaml.txt",
            "replacement_yaml.txt",
            "log4j2.xml"
    );

    /**
     * Валидация всех обязательных ресурсов при старте приложения
     *
     * @return true если все ресурсы найдены, false иначе
     */
    public static boolean validateAllResources() {
        boolean allResourcesFound = true;

        logger.info("Начинаем валидацию ресурсов приложения...");

        for (String resource : REQUIRED_RESOURCES) {
            if (!validateResource(resource)) {
                allResourcesFound = false;
                logger.error("Критический ресурс не найден: {}", resource);
            } else {
                logger.debug("Ресурс найден: {}", resource);
            }
        }

        if (allResourcesFound) {
            logger.info("Все обязательные ресурсы найдены");
        } else {
            logger.error("Некоторые критические ресурсы отсутствуют");
        }

        return allResourcesFound;
    }

    /**
     * Проверка существования конкретного ресурса
     *
     * @param resourceName имя ресурса
     * @return true если ресурс найден, false иначе
     */
    public static boolean validateResource(String resourceName) {
        if (resourceName == null || resourceName.trim().isEmpty()) {
            logger.warn("Передано пустое имя ресурса");
            return false;
        }

        URL resource = ResourceValidator.class.getClassLoader().getResource(resourceName);
        return resource != null;
    }

    /**
     * Безопасное получение URL ресурса с логированием
     *
     * @param resourceName   имя ресурса
     * @param requesterClass класс, запрашивающий ресурс
     * @return URL ресурса или null если не найден
     */
    public static URL getResourceSafely(String resourceName, Class<?> requesterClass) {
        if (resourceName == null || resourceName.trim().isEmpty()) {
            logger.error("Класс {} запросил ресурс с пустым именем", requesterClass.getSimpleName());
            return null;
        }

        URL resource = requesterClass.getClassLoader().getResource(resourceName);
        if (resource == null) {
            logger.error("Класс {} не смог найти ресурс: {}", requesterClass.getSimpleName(), resourceName);
        } else {
            logger.debug("Класс {} успешно загрузил ресурс: {}", requesterClass.getSimpleName(), resourceName);
        }

        return resource;
    }

    private ResourceValidator() {
        // Noop Private Constructor
    }

}
