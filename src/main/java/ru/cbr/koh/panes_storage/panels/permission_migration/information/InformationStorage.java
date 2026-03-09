package ru.cbr.koh.panes_storage.panels.permission_migration.information;

import ru.cbr.koh.panes_storage.panels.permission_migration.information.domain.Information;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Properties;

public class InformationStorage {

    private static final String FILE_NAME = "information.txt";
    private static final String KEY_TEXT = "keyText";
    private static final String AUTHOR = "author";
    private static final String STORY_NUMBER = "storyNumber";
    private static final String STORY_TEXT = "storyText";
    private static final String SHOULD_WRITE_ABAC_FILE = "shouldWriteAbacFile";
    private static final String SHOULD_WRITE_ABAC_ATTRIBUTE_CODE = "shouldWriteAbacAttributeCode";
    private static final String FROM_EXCEL = "fromExcel";

    private final File storageFile;

    public InformationStorage() {
        this(new File(FILE_NAME));
    }

    InformationStorage(File storageFile) {
        this.storageFile = storageFile;
    }

    public Optional<Information> load() {
        if (!storageFile.exists()) {
            return Optional.empty();
        }

        if (isLegacySerializedFile()) {
            return loadLegacySerialized();
        }

        Properties properties = new Properties();
        try (Reader reader = java.nio.file.Files.newBufferedReader(storageFile.toPath(), StandardCharsets.UTF_8)) {
            properties.load(reader);
            return Optional.of(fromProperties(properties));
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось прочитать " + FILE_NAME, e);
        }
    }

    public void save(Information information) {
        Properties properties = toProperties(information);
        try (Writer writer = java.nio.file.Files.newBufferedWriter(storageFile.toPath(), StandardCharsets.UTF_8)) {
            properties.store(writer, "KOH information state");
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось сохранить " + FILE_NAME, e);
        }
    }

    private boolean isLegacySerializedFile() {
        try (FileInputStream inputStream = new FileInputStream(storageFile)) {
            int first = inputStream.read();
            int second = inputStream.read();
            return first == 0xAC && second == 0xED;
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось определить формат " + FILE_NAME, e);
        }
    }

    private Optional<Information> loadLegacySerialized() {
        try (FileInputStream fileIn = new FileInputStream(storageFile);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            Information information = (Information) in.readObject();
            save(information);
            return Optional.of(information);
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось прочитать legacy-формат " + FILE_NAME, e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Некорректный legacy-формат " + FILE_NAME, e);
        }
    }

    private Properties toProperties(Information information) {
        Properties properties = new Properties();
        properties.setProperty(KEY_TEXT, nullToEmpty(information.keyText()));
        properties.setProperty(AUTHOR, nullToEmpty(information.author()));
        properties.setProperty(STORY_NUMBER, nullToEmpty(information.storyNumber()));
        properties.setProperty(STORY_TEXT, nullToEmpty(information.storyText()));
        properties.setProperty(SHOULD_WRITE_ABAC_FILE, Boolean.toString(information.shouldWriteAbakFile()));
        properties.setProperty(SHOULD_WRITE_ABAC_ATTRIBUTE_CODE, Boolean.toString(information.shouldWriteAbacAttributeCode()));
        properties.setProperty(FROM_EXCEL, Boolean.toString(information.fromExcel()));
        return properties;
    }

    private Information fromProperties(Properties properties) {
        return new Information(
                properties.getProperty(KEY_TEXT, ""),
                properties.getProperty(AUTHOR, ""),
                properties.getProperty(STORY_NUMBER, ""),
                properties.getProperty(STORY_TEXT, ""),
                Boolean.parseBoolean(properties.getProperty(SHOULD_WRITE_ABAC_FILE, "false")),
                Boolean.parseBoolean(properties.getProperty(SHOULD_WRITE_ABAC_ATTRIBUTE_CODE, "false")),
                Boolean.parseBoolean(properties.getProperty(FROM_EXCEL, "false"))
        );
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
