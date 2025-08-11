package ru.cbr.koh;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.cbr.koh.exceptions.ConfigurationException;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.domain.Information;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.base_clases.ChangeLog;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.PermissionType;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.TreeType;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;
import ru.cbr.koh.properties.ConfigurationService;
import ru.cbr.koh.utils.ResourceValidator;

import java.util.List;

@SuppressWarnings({"java:S1192", "java:S1854"})
public class OnlyMigrationApplication {

    private static final Logger logger = LogManager.getLogger(OnlyMigrationApplication.class);

    public static void main(String[] args) {
        logger.info("Запуск Migration приложения...");

        // Валидация ресурсов перед запуском
        if (!ResourceValidator.validateAllResources()) {
            logger.error("Критическая ошибка: отсутствуют обязательные ресурсы. Приложение не может быть запущено.");
            System.err.println("Ошибка: отсутствуют критические файлы ресурсов. Проверьте логи для деталей.");
            System.exit(1);
        }

        try {
            ConfigurationService config = ConfigurationService.getInstance();
            action(config);
            logger.info("Migration приложение завершено успешно");
        } catch (ConfigurationException e) {
            logger.fatal("Ошибка загрузки конфигурации", e);
            System.err.println("Ошибка загрузки конфигурации: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            logger.fatal("Критическая ошибка при выполнении миграции", e);
            System.err.println("Критическая ошибка при выполнении миграции: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void action(ConfigurationService config) {

        List<Profile> allWithoutSarAndRegionalCurator = config.getAllWithoutSarAndRegionalCurator();

        List<Permission> permissions =
                List.of(
                        new Permission(
                                "credit-organisation-card#Financial-analysis-tab#capital-tab",
                                PermissionType.COMPONENT,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"5.4 Капитал\"",
                                allWithoutSarAndRegionalCurator,
                                "Раздел \"5.4 Капитал\"",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                ),
                                10
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#Financial-analysis-tab#capital-tab#subord-tab",
                                PermissionType.COMPONENT,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"5.4.2 Субординированные инструменты\"",
                                allWithoutSarAndRegionalCurator,
                                "Раздел \"5.4.2 Субординированные инструменты\"",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                ),
                                10
                        )
                );


        var information = new Information(
                config.getMigrationKeyText(),
                config.getMigrationAuthor(),
                config.getMigrationStoryNumber(),
                config.getMigrationTabName(),
                true, true, true);

        ChangeLog changeLog = new ChangeLog(information, permissions);
        changeLog.create();
    }
}
