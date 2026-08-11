package ru.cbr.koh.panes_storage.panels.permission_migration.save_abac_profile_file;

import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.output.GeneratedFile;
import ru.cbr.koh.properties.ApplicationProperties;
import ru.cbr.koh.properties.PropertiesService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AbacProfileFileSaver {

    private final ApplicationProperties properties;

    public AbacProfileFileSaver() {
        this(PropertiesService.getInstance());
    }

    AbacProfileFileSaver(ApplicationProperties properties) {
        this.properties = properties;
    }

    public void save(List<Permission> permissions) {
        GeneratedFile file = buildFile(permissions);
        saveToFile(file);
    }

    public GeneratedFile buildFile(List<Permission> permissions) {
        Map<String, List<Permission>> grouped = getCollectedProfilesMap(permissions);
        String content = buildContent(grouped);
        return new GeneratedFile(Path.of(properties.getAbacFileName()), content);
    }

    private void saveToFile(GeneratedFile file) {
        try {
            Files.writeString(file.path(), file.content());
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось сохранить ABAC profiles: " + file.path().toAbsolutePath(), e);
        }
    }

    private String buildContent(Map<String, List<Permission>> groupedProfiles) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, List<Permission>> entry : groupedProfiles.entrySet()) {
            builder.append(entry.getKey()).append(System.lineSeparator()).append(System.lineSeparator());
            for (Permission permission : entry.getValue()) {
                builder.append(permission.getAbacPermPresAttrCode().replace(Permission.PREFIX, ""))
                        .append(System.lineSeparator());
                builder.append(permission.getKey()).append(System.lineSeparator()).append(System.lineSeparator());
            }
            builder.append(System.lineSeparator()).append(System.lineSeparator());
        }
        return builder.toString();
    }

    private Map<String, List<Permission>> getCollectedProfilesMap(List<Permission> permissions) {
        return permissions.stream()
                .flatMap(permission -> Stream.of(
                                Optional.ofNullable(permission.getAbacPermPresGroupAction()),
                                Optional.ofNullable(permission.getAbacPermPresUserAction())
                        ).filter(Optional::isPresent)
                        .map(Optional::get)
                        .distinct()
                        .map(key -> new AbstractMap.SimpleEntry<>(key, permission)))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        LinkedHashMap::new,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    }

}
