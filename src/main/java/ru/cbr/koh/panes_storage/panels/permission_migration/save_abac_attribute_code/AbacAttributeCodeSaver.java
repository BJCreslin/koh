package ru.cbr.koh.panes_storage.panels.permission_migration.save_abac_attribute_code;

import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.output.GeneratedFile;
import ru.cbr.koh.properties.ApplicationProperties;
import ru.cbr.koh.properties.PropertiesService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

public class AbacAttributeCodeSaver {

    private final ApplicationProperties properties;

    public AbacAttributeCodeSaver() {
        this(PropertiesService.getInstance());
    }

    AbacAttributeCodeSaver(ApplicationProperties properties) {
        this.properties = properties;
    }

    public void save(List<Permission> permissions) {
        GeneratedFile file = buildFile(permissions);
        try {
            Files.writeString(file.path(), file.content());
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось сохранить ABAC attribute codes: " + file.path().toAbsolutePath(), e);
        }
    }

    public GeneratedFile buildFile(List<Permission> permissions) {
        Path filePath = Path.of(properties.getAbacAttributeCodeFilePath());
        List<String> abacAttributeCodes = permissions.stream().map(this::createAbacAttributeCode).toList();
        String content = String.join("\n", abacAttributeCodes);
        return new GeneratedFile(filePath, content);
    }

    private String createAbacAttributeCode(Permission permission) {

        return String.format("""
                        %s("%s", %s, %s),
                        """,
                getPrefix(permission),
                permission.getAbacPermPresAttrCode(),
                getAttributeCode(permission),
                getDescription(permission));
    }

    private String getDescription(Permission permission) {
        if (permission.getDescription() == null) {
            return null;
        }
        return "\"" + permission.getDescription().replace("\"", "\\\"") + "\"";
    }

    private String getAttributeCode(Permission permission) {
        return "AttributeCategory.SUBJECT";
    }

    private String getPrefix(Permission permission) {
        return permission.getKey().toUpperCase(Locale.ROOT).replace("-", "_").replace("#", "_");
    }
}
