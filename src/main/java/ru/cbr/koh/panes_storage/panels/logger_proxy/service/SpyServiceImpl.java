package ru.cbr.koh.panes_storage.panels.logger_proxy.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SpyServiceImpl implements SpyService {

    private static final String APPLICATION_YML = "application-ppod/src/main/resources/application.yml";
    private static final String APPLICATION_YAML = "application-ppod/src/main/resources/application.yaml";
    private static final String APPLICATION_PROPERTIES = "application-ppod/src/main/resources/application.properties";

    private static final String START_MARKER = "# === KOH SQL LOGGING START ===";
    private static final String END_MARKER = "# === KOH SQL LOGGING END ===";

    private static final String YAML_BLOCK = String.join("\n",
            START_MARKER,
            "logging.level.org.hibernate.SQL: DEBUG",
            "logging.level.org.hibernate.orm.jdbc.bind: TRACE",
            "logging.level.org.hibernate.type.descriptor.sql.BasicBinder: TRACE",
            "spring.jpa.properties.hibernate.format_sql: true",
            END_MARKER);

    private static final String PROPERTIES_BLOCK = String.join("\n",
            START_MARKER,
            "logging.level.org.hibernate.SQL=DEBUG",
            "logging.level.org.hibernate.orm.jdbc.bind=TRACE",
            "logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE",
            "spring.jpa.properties.hibernate.format_sql=true",
            END_MARKER);

    @Override
    public void addLoggerProxy(String dossierKoDirectory) {
        Path configPath = resolveSpringConfigPath(Path.of(dossierKoDirectory));
        String block = isPropertiesFile(configPath) ? PROPERTIES_BLOCK : YAML_BLOCK;
        String content = readFile(configPath);
        writeFile(configPath, addManagedBlock(content, block));
    }

    @Override
    public void removeLoggerProxy(String dossierKoDirectory) {
        Path configPath = resolveSpringConfigPath(Path.of(dossierKoDirectory));
        String content = readFile(configPath);
        writeFile(configPath, removeManagedBlock(content));
    }

    @Override
    public boolean isLoggerProxyEnabled(String dossierKoDirectory) {
        Path configPath = resolveSpringConfigPath(Path.of(dossierKoDirectory));
        String content = readFile(configPath);
        return content.contains(START_MARKER) && content.contains(END_MARKER);
    }

    private Path resolveSpringConfigPath(Path rootPath) {
        Path ymlPath = rootPath.resolve(APPLICATION_YML);
        if (Files.exists(ymlPath)) {
            return ymlPath;
        }

        Path yamlPath = rootPath.resolve(APPLICATION_YAML);
        if (Files.exists(yamlPath)) {
            return yamlPath;
        }

        Path propertiesPath = rootPath.resolve(APPLICATION_PROPERTIES);
        if (Files.exists(propertiesPath)) {
            return propertiesPath;
        }

        throw new IllegalArgumentException("Не найден application.yml/.yaml/.properties в application-ppod/src/main/resources");
    }

    private boolean isPropertiesFile(Path path) {
        return path.getFileName() != null && path.getFileName().toString().endsWith(".properties");
    }

    private String addManagedBlock(String content, String block) {
        String cleaned = removeManagedBlock(content).stripTrailing();
        if (cleaned.isEmpty()) {
            return block + "\n";
        }
        return cleaned + "\n\n" + block + "\n";
    }

    private String removeManagedBlock(String content) {
        int start = content.indexOf(START_MARKER);
        int end = content.indexOf(END_MARKER);
        if (start == -1 || end == -1 || end < start) {
            return content;
        }

        int blockEnd = end + END_MARKER.length();
        if (blockEnd < content.length() && content.charAt(blockEnd) == '\n') {
            blockEnd++;
        }
        if (start > 0 && content.charAt(start - 1) == '\n') {
            start--;
        }
        return content.substring(0, start) + content.substring(blockEnd);
    }

    private String readFile(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось прочитать файл: " + path, e);
        }
    }

    private void writeFile(Path path, String content) {
        try {
            Files.writeString(path, content);
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось записать файл: " + path, e);
        }
    }
}
