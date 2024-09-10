package ru.cbr.koh.panes_storage.panels.logger_proxy.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SpyServiceImpl implements SpyService {

    private static final String POM_XML = "/application-ppod/pom.xml";

    private static final String ORIGINAL_POM_XML_FILENAME = "original_pom.txt";
    private static final String REPLACEMENT_POM_XML_FILENAME = "replacement_pom.txt";

    private static final String ORIGINAL_YAML_FILENAME = "original_yaml.txt";
    private static final String REPLACEMENT_YAML_FILENAME = "replacement_yaml.txt";

    @Override
    public void addLoggerProxy(String dossierKoDirectory) {
        replaceDataInFile(dossierKoDirectory + POM_XML, ORIGINAL_YAML_FILENAME, REPLACEMENT_POM_XML_FILENAME);
        replaceDataInFile(dossierKoDirectory + POM_XML, ORIGINAL_POM_XML_FILENAME, REPLACEMENT_YAML_FILENAME);
    }

    @Override
    public void removeLoggerProxy(String dossierKoDirectory) {
        replaceDataInFile(dossierKoDirectory + POM_XML, REPLACEMENT_POM_XML_FILENAME, ORIGINAL_POM_XML_FILENAME);
        replaceDataInFile(dossierKoDirectory + POM_XML, REPLACEMENT_YAML_FILENAME, ORIGINAL_YAML_FILENAME);
    }

    void replaceDataInFile(String filePath, String findTextFileName, String replaceTextFilename) {
        Path path = Paths.get(filePath);

        URL originalPomResource = SpyServiceImpl.class.getClassLoader().getResource(findTextFileName);
        if (originalPomResource == null) {
            throw new IllegalArgumentException("Файл не найден: " + findTextFileName);
        }

        URL replacementPomResource = SpyServiceImpl.class.getClassLoader().getResource(replaceTextFilename);

        if (replacementPomResource == null) {
            throw new IllegalArgumentException("Файл не найден: " + replaceTextFilename);
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
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
