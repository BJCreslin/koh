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

    @Override
    public void addLoggerProxy(String dossierKoDirectory) {
        doPomXml(dossierKoDirectory);
    }

    @Override
    public void removeLoggerProxy() {
    }

    void doPomXml(String dossierKoDirectory) {
        replaceDataInFile(dossierKoDirectory + POM_XML);
    }

    void replaceDataInFile(String filePath) {
        Path path = Paths.get(filePath);

        URL originalPomResource = SpyServiceImpl.class.getClassLoader().getResource(ORIGINAL_POM_XML_FILENAME);
        if (originalPomResource == null) {
            throw new IllegalArgumentException("Файл не найден: " + ORIGINAL_POM_XML_FILENAME);
        }

        URL replacementPomResource = SpyServiceImpl.class.getClassLoader().getResource(REPLACEMENT_POM_XML_FILENAME);

        if (replacementPomResource == null) {
            throw new IllegalArgumentException("Файл не найден: " + REPLACEMENT_POM_XML_FILENAME);
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
