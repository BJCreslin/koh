package ru.cbr.koh.properties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertiesService {

    public static final String PROPERTIES_FILE = "config.properties";

    private static PropertiesService instance;

    public static PropertiesService getInstance() {
        if (instance == null) {
            instance = new PropertiesService();
        }
        return instance;
    }

    private int horizontalSize;
    private int verticalSize;
    private String title;
    private String author;
    private String storyNumber;
    private String storyName;
    private String storyKey;
    private Boolean shouldWriteAbakFile;
    private String abacFileName;


    public PropertiesService() {
        Properties properties = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            assert in != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                properties.load(reader);
                this.horizontalSize = Integer.parseInt(properties.getProperty("window.size.horizontal"));
                this.verticalSize = Integer.parseInt(properties.getProperty("window.size.vertical"));
                this.title = properties.getProperty("window.title");

                this.author = properties.getProperty("story.author");
                this.storyNumber = properties.getProperty("story.number");
                this.storyName = properties.getProperty("story.name");
                this.storyKey = properties.getProperty("story.key");
                this.shouldWriteAbakFile = Boolean.parseBoolean(properties.getProperty("story.shouldWriteAbacFile"));

                this.abacFileName = properties.getProperty("abac.fileName");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int getHorizontalSize() {
        return horizontalSize;
    }

    public int getVerticalSize() {
        return verticalSize;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getStoryNumber() {
        return storyNumber;
    }

    public String getStoryName() {
        return storyName;
    }

    public String getStoryKey() {
        return storyKey;
    }

    public boolean getSaveAbacPolitics() {
        return shouldWriteAbakFile != null && shouldWriteAbakFile;
    }

    public String getAbacFileName() {
        return abacFileName;
    }
}
