package ru.cbr.koh.properties;

import java.io.IOException;
import java.io.InputStream;
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


    public PropertiesService() {
        Properties properties = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            properties.load(in);
            this.horizontalSize = Integer.parseInt(properties.getProperty("window.size.horizontal"));
            this.verticalSize = Integer.parseInt(properties.getProperty("window.size.vertical"));
            this.title = properties.getProperty("window.title");

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
}
