package ru.cbr.koh.panes_storage.panels.logger_proxy.service;

import ru.cbr.koh.utils.ResourceValidator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SpyServiceImpl implements SpyService {

    private static final String POM_XML = "/application-ppod/pom.xml";
    private static final String YAML_XML = "/application-ppod/src/main/resources/application.yml";
    private static final String PROPERTY_FILE = "/application-ppod/src/main/resources/spy.properties";


    private static final String ORIGINAL_POM_XML_FILENAME = "original_pom.txt";
    private static final String REPLACEMENT_POM_XML_FILENAME = "replacement_pom.txt";

    private static final String ORIGINAL_YAML_FILENAME = "original_yaml.txt";
    private static final String REPLACEMENT_YAML_FILENAME = "replacement_yaml.txt";

    private static final String ORIGINAL_PROPERTIES_FILENAME = "spy.properties";

    @Override
    public void addLoggerProxy(String dossierKoDirectory) {
        replaceDataInFile(dossierKoDirectory + POM_XML, ORIGINAL_YAML_FILENAME, REPLACEMENT_POM_XML_FILENAME);
        replaceDataInFile(dossierKoDirectory + YAML_XML, ORIGINAL_POM_XML_FILENAME, REPLACEMENT_YAML_FILENAME);
        createSpyPropertiesFile(dossierKoDirectory, PROPERTY_FILE);
    }

    private void createSpyPropertiesFile(String dossierKoDirectory, String propertyFile) {
        if (dossierKoDirectory == null || propertyFile == null) {
            throw new IllegalArgumentException("Параметры dossierKoDirectory и propertyFile не могут быть null");
        }

        URL originalPomResource = ResourceValidator.getResourceSafely(ORIGINAL_PROPERTIES_FILENAME, SpyServiceImpl.class);
        if (originalPomResource == null) {
            throw new IllegalArgumentException("Файл ресурса не найден: " + ORIGINAL_PROPERTIES_FILENAME);
        }
        try {
            Path propertiespath = Paths.get(originalPomResource.toURI());
            String content = Files.readString(propertiespath);

            Path destinationPath = Paths.get(dossierKoDirectory + propertyFile);
            Files.createDirectories(destinationPath.getParent());
            Files.writeString(destinationPath, content);

        } catch (IOException e) {
            throw new IllegalArgumentException("Ошибка записи файла: " + propertyFile, e);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Некорректный URI ресурса: " + ORIGINAL_PROPERTIES_FILENAME, e);
        }
    }

    @Override
    public void removeLoggerProxy(String dossierKoDirectory) {
        replaceDataInFile(dossierKoDirectory + POM_XML, REPLACEMENT_POM_XML_FILENAME, ORIGINAL_POM_XML_FILENAME);
        replaceDataInFile(dossierKoDirectory + YAML_XML, REPLACEMENT_YAML_FILENAME, ORIGINAL_YAML_FILENAME);
    }

    void replaceDataInFile(String filePath, String findTextFileName, String replaceTextFilename) {
        if (filePath == null || findTextFileName == null || replaceTextFilename == null) {
            throw new IllegalArgumentException("Все параметры должны быть не null");
        }
        
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("Целевой файл не существует: " + filePath);
        }

        URL originalPomResource = ResourceValidator.getResourceSafely(findTextFileName, SpyServiceImpl.class);
        if (originalPomResource == null) {
            throw new IllegalArgumentException("Файл ресурса не найден: " + findTextFileName);
        }

        URL replacementPomResource = ResourceValidator.getResourceSafely(replaceTextFilename, SpyServiceImpl.class);
        if (replacementPomResource == null) {
            throw new IllegalArgumentException("Файл ресурса не найден: " + replaceTextFilename);
        }

        try {
            String content = Files.readString(path);

            Path originalPomPath = Paths.get(originalPomResource.toURI());
            String searchText = Files.readString(originalPomPath);

            Path replacementTextPath = Paths.get(replacementPomResource.toURI());
            String replacementText = Files.readString(replacementTextPath);

            content = content.replace(searchText, replacementText);
            Files.writeString(path, content);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка работы с файлом: " + filePath, e);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Некорректный URI ресурса", e);
        }
    }

}
