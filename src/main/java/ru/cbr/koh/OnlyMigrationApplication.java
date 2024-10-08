package ru.cbr.koh;

import ru.cbr.koh.panes_storage.panels.permission_migration.information.domain.Information;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.base_clases.ChangeLog;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.PermissionType;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.TreeType;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

import java.util.ArrayList;
import java.util.List;

public class OnlyMigrationApplication {

    private static final String KEY_TEXT = "collateral_conclusion";

    private static final String AUTHOR = "KreslinVYu";

    private static final String STORY_NUMBER = "DOSIE-11982 (12083)";

    private static final String TAB_NAME = "Заключения САР по залоговым объектам";

    public static void main(String[] args) {
        action();
    }

    private static void action() {

        List<Profile> allWithoutSarWorkerAndRegionalCurator = List.of(
                Profile.AUDITOR,
                Profile.BUSINESS_ANALYST_GIBR,
                Profile.BUSINESS_ADMINISTRATOR,
                Profile.CURATOR_STBN,
                Profile.CURATOR_GIBR,
                Profile.CURATOR_DFS,
                Profile.CURATOR_DNSZKO,
                Profile.MANAGER_CURATOR_OBN,
                Profile.COORDINATOR_STBN,
                Profile.COORDINATOR_DNSZKO,
                Profile.ANALYST_STBN,
                Profile.ANALYST_DNSZKO,
                Profile.METHODOLOGIST_STBN,
                Profile.METHODOLOGIST_DNSZKO);

        List<Profile> allWithoutSarWorker = new ArrayList<>(allWithoutSarWorkerAndRegionalCurator);
        allWithoutSarWorker.add(Profile.REGIONAL_CURATOR);

        List<Profile> allProfiles = new ArrayList<>(allWithoutSarWorker);
        allProfiles.add(Profile.EMPLOYEE_SAR);

        List<Permission> permissions = List.of(
                new Permission(
                        "credit-organisation-card#general-info-tab",
                        PermissionType.ACTION,
                        "GET_PERMISSIONS_CO_CARD",
                        null,
                        "Раздел \"1. Общая информация по КО\"",
                        allWithoutSarWorker,
                        null,
                        List.of(TreeType.GIBR, TreeType.KO)
                ),
                new Permission(
                        "credit-organisation-card#general-info-tab#view",
                        PermissionType.ACTION,
                        "GET_PERMISSIONS_CO_CARD",
                        null,
                        "Право на просмотр информации",
                        allWithoutSarWorker,
                        "Право на просмотр информации:&#13;&#10;" +
                        "1) Общая информация&#13;&#10;" +
                        "2) Краткая справочная информация&#13;&#10;" +
                        "3) Лицензии&#13;&#10;" +
                        "4) Филиалы",
                        List.of(TreeType.GIBR, TreeType.KO)
                ),
                new Permission(
                        "credit-organisation-card#interaction-spbr-tab",
                        PermissionType.ACTION,
                        "GET_PERMISSIONS_CO_CARD",
                        null,
                        "Раздел \"7. Взаимодействие с СП БР\"",
                        allWithoutSarWorker,
                        null,
                        List.of(TreeType.GIBR, TreeType.KO)
                ),
                new Permission(
                        "credit-organisation-card#interaction-spbr-tab#sar-tab",
                        PermissionType.ACTION,
                        "GET_PERMISSIONS_CO_CARD",
                        null,
                        "Раздел \"7.1. САР\"",
                        allWithoutSarWorker,
                        null,
                        List.of(TreeType.GIBR, TreeType.KO)
                ),
                new Permission(
                        "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab",
                        PermissionType.ACTION,
                        "GET_PERMISSIONS_CO_CARD",
                        null,
                        "Раздел \"7.1.1. Кредитный риск ЮЛ и ИП\"",
                        allWithoutSarWorker,
                        null,
                        List.of(TreeType.GIBR, TreeType.KO)
                ),
                new Permission(
                        "credit-organisation-card#market-position-tab",
                        PermissionType.ACTION,
                        "GET_PERMISSIONS_CO_CARD",
                        null,
                        "Раздел \"3. Положение на рынке\"",
                        allProfiles,
                        null,
                        List.of(TreeType.GIBR, TreeType.KO)
                )
                ,
                new Permission(
                        "credit-organisation-card#market-position-tab#ratings-tab",
                        PermissionType.ACTION,
                        "GET_PERMISSIONS_CO_CARD",
                        null,
                        "Раздел \"3.3. Рейтинги\"",
                        allProfiles,
                        null,
                        List.of(TreeType.GIBR, TreeType.KO)
                )
                ,
                new Permission(
                        "credit-organisation-card#market-position-tab#ratings-tab#agency-ratings-tab",
                        PermissionType.ACTION,
                        "GET_PERMISSIONS_CO_CARD",
                        null,
                        "Раздел \"3.3.1. Рейтинги агентств\"",
                        allProfiles,
                        null,
                        List.of(TreeType.GIBR, TreeType.KO)
                )
               ,
                new Permission(
                        "credit-organisation-card#market-position-tab#ratings-tab#agency-ratings-tab#view",
                        PermissionType.ACTION,
                        "GET_PERMISSIONS_CO_CARD",
                        null,
                        "Право на просмотр информации",
                        allProfiles,
                        "Право на просмотр информации:&#13;&#10;" +
                        "1) Страница с актуальными рейтингами&#13;&#10;" +
                        "2) Модальное окно c историей присвоения рейтингов&#13;&#10;" +
                        "3) Выгрузка excel-файла с актуальными рейтингами&#13;&#10;" +
                        "4) Выгрузка excel-файла с историей присвоения рейтингов агентством",
                        List.of(TreeType.GIBR, TreeType.KO)
                ),
        new Permission(
                "credit-organisation-card#financial-analysis-tab",
                PermissionType.ACTION,
                "GET_PERMISSIONS_CO_CARD",
                null,
                "Раздел \"5. Финансовый анализ (соло)\"",
                allWithoutSarWorker,
                null,
                List.of(TreeType.GIBR, TreeType.KO)
        )

        );

        var information = new Information(KEY_TEXT, AUTHOR, STORY_NUMBER, TAB_NAME, true);

        ChangeLog changeLog = new ChangeLog(information, permissions);
        changeLog.create();
    }
}
