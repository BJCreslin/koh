package ru.cbr.koh.properties;

public final class PropertiesService implements ApplicationProperties {

    private static final PropertiesService INSTANCE = new PropertiesService();

    private PropertiesService() {
    }

    public static PropertiesService getInstance() {
        return INSTANCE;
    }

    @Override
    public String get(String key) {
        return ConfigManager.getProperty(key);
    }

    @Override
    public void set(String key, String value) {
        ConfigManager.setProperty(key, value);
    }

    @Override
    public int getHorizontalSize() {
        return getRequiredInt("window.horizontalSize");
    }

    @Override
    public int getVerticalSize() {
        return getRequiredInt("window.verticalSize");
    }

    @Override
    public String getTitle() {
        return getRequired("window.title");
    }

    @Override
    public String getAuthor() {
        return getRequired("story.author");
    }

    @Override
    public String getStoryNumber() {
        return getRequired("story.number");
    }

    @Override
    public String getStoryName() {
        return getRequired("story.name");
    }

    @Override
    public String getStoryKey() {
        return getRequired("story.key");
    }

    @Override
    public boolean getSaveAbacPolitics() {
        return getRequiredBoolean("story.shouldWriteAbacFile");
    }

    @Override
    public boolean getSaveAbacAttributeCode() {
        return getRequiredBoolean("story.shouldWriteAbacAttributeCode");
    }

    @Override
    public String getAbacFileName() {
        return getRequired("abac.fileName");
    }

    @Override
    public String getAbacAttributeCodeFilePath() {
        return getRequired("abac.attributeCodeFilePath");
    }

    @Override
    public boolean getFromExcel() {
        return getRequiredBoolean("story.fromExcel");
    }

    @Override
    public String getPathExcel() {
        return getRequired("project.pathExcel");
    }

    @Override
    public char getExcelRowSelector() {
        String value = getRequired("excel.rowSelector").trim();
        if (value.length() != 1) {
            throw new IllegalStateException("Ключ excel.rowSelector должен содержать ровно один символ: " + value);
        }
        return value.charAt(0);
    }

    @Override
    public void setExcelRowSelector(char rowSelector) {
        set("excel.rowSelector", String.valueOf(rowSelector));
    }

    @Override
    public int getExcelProfileStartColumn() {
        return getRequiredInt("excel.profileStartColumn");
    }

    @Override
    public void setExcelProfileStartColumn(int profileStartColumn) {
        if (profileStartColumn < 1) {
            throw new IllegalArgumentException("excel.profileStartColumn должен быть >= 1");
        }
        set("excel.profileStartColumn", String.valueOf(profileStartColumn));
    }

    @Override
    public void setPathExcel(String pathExcel) {
        if (pathExcel == null || pathExcel.isBlank()) {
            throw new IllegalArgumentException("project.pathExcel не может быть пустым");
        }
        set("project.pathExcel", pathExcel);
    }

    private String getRequired(String key) {
        String value = ConfigManager.getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Отсутствует обязательный ключ: " + key + " в config.properties");
        }
        return value;
    }

    private boolean getRequiredBoolean(String key) {
        String value = getRequired(key);
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return Boolean.parseBoolean(value);
        }
        throw new IllegalStateException("Некорректное boolean-значение для ключа " + key + ": " + value);
    }

    private int getRequiredInt(String key) {
        String value = getRequired(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            throw new IllegalStateException("Некорректное integer-значение для ключа " + key + ": " + value, ex);
        }
    }
}
