package ru.cbr.koh.panes_storage.panels.logger_proxy.service;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpyServiceImplTest {

    private final SpyServiceImpl spyService = new SpyServiceImpl();

    @Test
    void shouldDetectManagedSqlLoggingBlockInPropertiesFile() throws Exception {
        Path projectDir = Files.createTempDirectory("spy-service");
        Path resourcesDir = projectDir.resolve("application-ppod/src/main/resources");
        Files.createDirectories(resourcesDir);
        Path propertiesFile = resourcesDir.resolve("application.properties");
        Files.writeString(propertiesFile, "server.port=8080\n");

        assertFalse(spyService.isLoggerProxyEnabled(projectDir.toString()));

        spyService.addLoggerProxy(projectDir.toString());

        assertTrue(spyService.isLoggerProxyEnabled(projectDir.toString()));
    }
}
