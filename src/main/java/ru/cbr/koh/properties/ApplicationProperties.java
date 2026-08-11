package ru.cbr.koh.properties;

public interface ApplicationProperties {

    String get(String key);

    void set(String key, String value);

    int getHorizontalSize();

    int getVerticalSize();

    String getTitle();

    String getAuthor();

    String getStoryNumber();

    String getStoryName();

    String getStoryKey();

    boolean getSaveAbacPolitics();

    boolean getSaveAbacAttributeCode();

    String getAbacFileName();

    String getAbacAttributeCodeFilePath();

    boolean getFromExcel();

    String getPathExcel();

    char getExcelRowSelector();

    void setExcelRowSelector(char rowSelector);

    int getExcelProfileStartColumn();

    void setExcelProfileStartColumn(int profileStartColumn);

    void setPathExcel(String pathExcel);
}
