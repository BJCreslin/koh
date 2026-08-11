package ru.cbr.koh.properties;

public final class ConfigurationSchemaValidator {

    private ConfigurationSchemaValidator() {
    }

    public static void validate(ApplicationProperties properties) {
        validateWindow(properties);
        validateStory(properties);
        validateAbac(properties);
        validateExcel(properties);
    }

    private static void validateWindow(ApplicationProperties properties) {
        if (properties.getHorizontalSize() <= 0) {
            throw new IllegalStateException("window.horizontalSize должен быть > 0");
        }
        if (properties.getVerticalSize() <= 0) {
            throw new IllegalStateException("window.verticalSize должен быть > 0");
        }
        requireNotBlank(properties.getTitle(), "window.title");
    }

    private static void validateStory(ApplicationProperties properties) {
        requireNotBlank(properties.getAuthor(), "story.author");
        requireNotBlank(properties.getStoryNumber(), "story.number");
        requireNotBlank(properties.getStoryName(), "story.name");
        requireNotBlank(properties.getStoryKey(), "story.key");
    }

    private static void validateAbac(ApplicationProperties properties) {
        requireNotBlank(properties.getAbacFileName(), "abac.fileName");
        requireNotBlank(properties.getAbacAttributeCodeFilePath(), "abac.attributeCodeFilePath");
    }

    private static void validateExcel(ApplicationProperties properties) {
        if (properties.getExcelProfileStartColumn() < 1) {
            throw new IllegalStateException("excel.profileStartColumn должен быть >= 1");
        }
        if (properties.getExcelRowSelector() == '\0') {
            throw new IllegalStateException("excel.rowSelector не должен быть пустым");
        }
    }

    private static void requireNotBlank(String value, String key) {
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Отсутствует обязательный ключ: " + key);
        }
    }
}
