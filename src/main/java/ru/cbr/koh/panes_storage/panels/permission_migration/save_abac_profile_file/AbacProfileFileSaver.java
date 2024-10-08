package ru.cbr.koh.panes_storage.panels.permission_migration.save_abac_profile_file;

import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.properties.PropertiesService;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AbacProfileFileSaver {

    private final PropertiesService properties = PropertiesService.getInstance();

    public void save(List<Permission> permissions) {

        Map<String, List<Permission>> result = getCollectedProfilesMap(permissions);
        saveToFile(result);
    }

    private void saveToFile(Map<String, List<Permission>> result) {
        Path filePath = Path.of(properties.getAbacFileName());
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (Map.Entry<String, List<Permission>> entry : result.entrySet()) {
                writeHeader(entry.getKey(), writer);
                writeTable(entry.getValue(), writer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeTable(List<Permission> permissions, BufferedWriter writer) {
        permissions.forEach(permission -> {
            try {
                writer.write(permission.getAbacPermPresAttrCode().replace("urn:%s:attr:01:subject:", ""));
                writer.newLine();
                writer.write(permission.getKey());
                writer.newLine();
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            writer.newLine();
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeHeader(String permissionName, BufferedWriter writer) {
        try {
            writer.write(permissionName);
            writer.newLine();
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static Map<String, List<Permission>> getCollectedProfilesMap(List<Permission> permissions) {
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
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    }


}
