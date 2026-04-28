package ru.cbr.koh.app;

import ru.cbr.koh.app.async.UiTaskRunner;
import ru.cbr.koh.app.error.ErrorHandler;
import ru.cbr.koh.app.error.SwingErrorHandler;
import ru.cbr.koh.app.service.PermissionMigrationService;
import ru.cbr.koh.logs.LogViewerSpringContext;
import ru.cbr.koh.logs.service.ApplicationLogFacade;
import ru.cbr.koh.properties.ApplicationProperties;

public class AppContext {

    private final ApplicationProperties properties;
    private final ErrorHandler errorHandler;
    private final UiTaskRunner taskRunner;
    private final PermissionMigrationService permissionMigrationService;
    private final ApplicationLogFacade applicationLogFacade;

    public AppContext(ApplicationProperties properties) {
        this(properties, new SwingErrorHandler(), new PermissionMigrationService());
    }

    AppContext(ApplicationProperties properties,
               ErrorHandler errorHandler,
               PermissionMigrationService permissionMigrationService) {
        this.properties = properties;
        this.errorHandler = errorHandler;
        this.taskRunner = new UiTaskRunner(errorHandler);
        this.permissionMigrationService = permissionMigrationService;
        this.applicationLogFacade = LogViewerSpringContext.getBean(ApplicationLogFacade.class);
    }

    public ApplicationProperties getProperties() {
        return properties;
    }

    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public UiTaskRunner getTaskRunner() {
        return taskRunner;
    }

    public PermissionMigrationService getPermissionMigrationService() {
        return permissionMigrationService;
    }

    public ApplicationLogFacade getApplicationLogFacade() {
        return applicationLogFacade;
    }
}
