package ru.cbr.koh;

import ru.cbr.koh.panes_storage.panels.permission_migration.information.domain.Information;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.base_clases.ChangeLog;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.PermissionType;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.TreeType;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

import java.util.List;

@SuppressWarnings({"java:S1192", "java:S1854"})
public class OnlyMigrationApplication {

    private static final String KEY_TEXT = "credit_organisation_card_structure_remake";

    private static final String AUTHOR = "KreslinVYu";

    private static final String STORY_NUMBER = "DOSIE-12652 (12753)";

    private static final String TAB_NAME = "Изменить структуру карточки КО";

    public static void main(String[] args) {
        action();
    }

    private static void action() {

        List<Profile> allProfiles =
                List.of(
                        Profile.REGIONAL_CURATOR, Profile.EMPLOYEE_SAR, Profile.AUDITOR,
                        Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN,
                        Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO,
                        Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO,
                        Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                );
        List<Profile> allWithoutSarAndRegionalCurator =
                List.of(
                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN,
                        Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN,
                        Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN,
                        Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                );
        List<Profile> allWithoutSar =
                List.of(
                        Profile.REGIONAL_CURATOR, Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR,
                        Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS,
                        Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO,
                        Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN,
                        Profile.METHODOLOGIST_DNSZKO
                );
        List<Profile> allWithoutSarAndRegionalCuratorAndCoordinatorAnalystMethotologDNSZKO =
                List.of(
                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN,
                        Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN,
                        Profile.COORDINATOR_STBN, Profile.ANALYST_STBN, Profile.METHODOLOGIST_STBN
                );

        List<Profile> baAndOther =
                List.of(
                        Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_DNSZKO
                );


        List<Permission> permissions =
                List.of(


                        new Permission(
                                "credit-organisation-card#general-info-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"1. Общая информация по КО\"",
                                List.of(
                                        Profile.REGIONAL_CURATOR, Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#general-info-tab#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.REGIONAL_CURATOR, Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Общая информация &#13;&#10;2) Краткая справочная информация &#13;&#10;3) Лицензии &#13;&#10;4) Филиалы",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#market-position-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"3. Положение на рынке\"",
                                List.of(
                                        Profile.REGIONAL_CURATOR, Profile.EMPLOYEE_SAR, Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#market-position-tab#ratings-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"3.3. Рейтинги\"",
                                List.of(
                                        Profile.REGIONAL_CURATOR, Profile.EMPLOYEE_SAR, Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#market-position-tab#ratings-tab#agency-ratings-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"3.3.1. Рейтинги агентств\"",
                                List.of(
                                        Profile.REGIONAL_CURATOR, Profile.EMPLOYEE_SAR, Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#market-position-tab#ratings-tab#agency-ratings-tab#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.REGIONAL_CURATOR, Profile.EMPLOYEE_SAR, Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Страница с актуальными рейтингами &#13;&#10;2) Модальное окно c историей присвоения рейтингов &#13;&#10;3) Выгрузка excel-файла с актуальными рейтингами &#13;&#10;4) Выгрузка excel-файла с историей присвоения рейтингов агентством &#13;&#10;",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#financial-analysis-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"5. Финансовый анализ (соло)\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.ANALYST_STBN, Profile.METHODOLOGIST_STBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#financial-analysis-tab#performance-indicators-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"5.9. Показатели деятельности\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.ANALYST_STBN, Profile.METHODOLOGIST_STBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#financial-analysis-tab#performance-indicators-tab#high-risk-zones-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"5.9.1. Зоны повышенных рисков на основе надзорных импульсов по методике СТБН\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.ANALYST_STBN, Profile.METHODOLOGIST_STBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#financial-analysis-tab#performance-indicators-tab#high-risk-zones-tab#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.ANALYST_STBN, Profile.METHODOLOGIST_STBN
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Таблица с данными &#13;&#10;2) Выгрузка в excel &#13;&#10;3) Просмотр окна \"Динамика показателя\", выгрузка графика в PNG",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#supervisory-activities-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"6. Надзорные мероприятия\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#supervisory-activities-tab#supervision-risk-profile-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"6.4. Режим надзора / риск профиль\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#supervisory-activities-tab#supervision-risk-profile-tab#supervision-mode-table",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_RESULTS_INFORMATION",
                                null,
                                "Таблица \"Режим надзора\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#supervisory-activities-tab#supervision-risk-profile-tab#supervision-mode-table#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_RESULTS_INFORMATION",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Таблица со списком режимов надзора и клас.группы &#13;&#10;2) Выгрузка в excel &#13;&#10;3) Скачивание файлов (В окне \"Работа с файлами\")",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#supervisory-activities-tab#supervision-risk-profile-tab#supervision-mode-table#view#open",
                                PermissionType.ACTION,
                                "GET_CO_RESULTS_INFORMATION_DIVISION_OF_INFORMATION",
                                null,
                                "открытая",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Информация в свободном доступе",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#supervisory-activities-tab#supervision-risk-profile-tab#supervision-mode-table#view#close",
                                PermissionType.ACTION,
                                "GET_CO_RESULTS_INFORMATION_DIVISION_OF_INFORMATION",
                                null,
                                "закрытая",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Надзорная информация",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#supervisory-activities-tab#business-model-analysis-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"6.6. Анализ бизнес модели\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#supervisory-activities-tab#business-model-analysis-tab#conclusions-abm-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"Заключения по АБМ\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#supervisory-activities-tab#business-model-analysis-tab#conclusions-abm-tab#finalized-dates-mark",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Выделение отчетных дат с фин.версией",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Выделение отчетных дат с фин.версией (звезда, чекбокс, тултип)",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#supervisory-activities-tab#business-model-analysis-tab#conclusions-abm-tab#write",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                "GET_KO_LIST_MYKO_ABM_PVR_EDITPVR",
                                "Право на редактирование БМ",
                                List.of(
                                        Profile.CURATOR_STBN, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                "Право на редактирование БМ:  &#13;&#10;1) Кнопка \"Редактировать\" &#13;&#10;2) Кнопка \"Удалить\"",
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#supervisory-activities-tab#business-model-analysis-tab#conclusions-abm-tab#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Качественные составляющие &#13;&#10;2) Количественные составляющие &#13;&#10;3) Вывод и обоснование &#13;&#10;4) Критерии для направления требования &#13;&#10;5) Выгрузка",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#supervisory-activities-tab#business-model-analysis-tab#indicators-amb-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"Показатели АБМ в динамике\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#supervisory-activities-tab#business-model-analysis-tab#indicators-amb-tab#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Таблица с данными &#13;&#10;2) Выгрузка в excel &#13;&#10;3) Просмотр окна \"Динамика показателя\", выгрузка графика в PNG &#13;&#10;",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#supervisory-activities-tab#business-model-analysis-tab#pvr-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"6.13 ПВР\"",
                                List.of(
                                        Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#supervisory-activities-tab#business-model-analysis-tab#pvr-tab#main-result-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"Основные результаты применения ПВР\"",
                                List.of(
                                        Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#supervisory-activities-tab#business-model-analysis-tab#pvr-tab#main-result-tab#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#supervisory-activities-tab#business-model-analysis-tab#pvr-tab#main-result-tab#write",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                "GET_KO_LIST_MYKO_ABM_PVR_EDITPVR",
                                "Право на редактирование ПВР",
                                List.of(
                                        Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на редактирование ПВР: &#13;&#10;1) Загрузка файлов &#13;&#10;2) Удаление файлов",
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#supervisory-activities-tab#business-model-analysis-tab#pvr-tab#detail-result-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"Детальная информация по ПВР\"",
                                List.of(
                                        Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#supervisory-activities-tab#business-model-analysis-tab#pvr-tab#detail-result-tab#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"7. Взаимодействие с СП БР\"",
                                List.of(
                                        Profile.REGIONAL_CURATOR, Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"7.1. САР\"",
                                List.of(
                                        Profile.REGIONAL_CURATOR, Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"7.1.1. Кредитный риск ЮЛ и ИП\"",
                                List.of(
                                        Profile.REGIONAL_CURATOR, Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"Реестр ссуд\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab#loans-table",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_LOANS",
                                null,
                                "Таблица \"Список ссуд\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab#loans-table#co-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_LOANS",
                                null,
                                "Ссылка на Карточку КО",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Ссылка на Карточку КО из всех таблиц этой страницы",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab#loans-table#loan-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_LOANS",
                                null,
                                "Ссылка на карточку ссуды",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Ссылка на карточку ссуды из всех таблиц этой страницы",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab#loans-table#borrower-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_LOANS",
                                null,
                                "Ссылка на карточку заемщика",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Ссылка на карточку заемщика из всех таблиц этой страницы",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab#loans-table#analysis-task-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_LOANS",
                                null,
                                "Ссылка на карточку Задания на анализ",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Ссылка на карточку Задания на анализ из всех таблиц этой страницы",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab#loans-table#create-quota-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_LOANS",
                                null,
                                "Кнопка \"Создать/обновить квоту\" БА",
                                List.of(
                                        Profile.BUSINESS_ADMINISTRATOR, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Кнопка \"Создать/обновить квоту\" аналогичная функционалу бизнес администратора",
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab#loans-table#create-quota-stbn-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_LOANS",
                                null,
                                "Кнопка \"Создать/обновить квоту\" - куратор СТБН",
                                List.of(
                                        Profile.CURATOR_STBN, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab#loans-table#create-quota-dnszko-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_LOANS",
                                null,
                                "Кнопка \"Сформировать запрос в ЛК\" - куратор ДНСЗКО",
                                List.of(
                                        Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Создание или обновление квоты с последующей возможностью отправки запроса в ЛК",
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab#loans-table#additional-filters",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_LOANS",
                                null,
                                "Дополнительная фильтрация и сортировка",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab#loans-table#additional-filters#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_LOANS",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Тип контрагента &#13;&#10;2) Период привязки первички",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab#loans-table#additional-filters#conclusion-filters",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_LOANS",
                                null,
                                "Источник данных по анализу",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Источник данных по анализу: &#13;&#10;1) Селект с выбором источника &#13;&#10;2) Чекбокс \"С учетом статуса \"В работе\"\"",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab#loans-table#additional-filters#conclusion-filters#actual",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_LOANS",
                                null,
                                "Актуальная оценка ссуд",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Актуальная оценка ссуд",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab#loans-table#additional-filters#conclusion-filters#curator",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_LOANS",
                                null,
                                "Оценка куратор",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab#loans-table#additional-filters#conclusion-filters#sar",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_LOANS",
                                null,
                                "Оценка САР",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab#loans-table#additional-filters#conclusion-filters#gibr",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_LOANS",
                                null,
                                "Оценка ГИБР",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab#loans-table#update-min-package-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_LOANS",
                                null,
                                "Кнопка \"Обновить мин пакет\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab#loans-table#documents-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_LOANS",
                                null,
                                "Кнопка \"Документы\"",
                                List.of(
                                        Profile.BUSINESS_ADMINISTRATOR, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab#loans-table#mesasure-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_LOANS",
                                null,
                                "Кнопка \"Применить меру\"",
                                List.of(
                                        Profile.CURATOR_STBN, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab#loans-table#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_LOANS",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Таблица со списком ссуд &#13;&#10;2) Выгрузка в excel &#13;&#10;3) Кнопка ТОП",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab#loans-table#view#open",
                                PermissionType.ACTION,
                                "GET_CO_ASSETS_LOANS_DIVISION_OF_INFORMATION",
                                null,
                                "Информация в свободном доступе",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "открытая",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#co-loans-tab#loans-table#view#close",
                                PermissionType.ACTION,
                                "GET_CO_ASSETS_LOANS_DIVISION_OF_INFORMATION",
                                null,
                                "Надзорная информация",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "закрытая",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"Новые ссуды и транши\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab#loans-table",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_LOANS",
                                null,
                                "Таблица \"Список ссуд\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab#loans-table#co-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_LOANS",
                                null,
                                "Ссылка на Карточку КО",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Ссылка на Карточку КО из всех таблиц этой страницы",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab#loans-table#loan-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_LOANS",
                                null,
                                "Ссылка на карточку ссуды",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Ссылка на карточку ссуды из всех таблиц этой страницы",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab#loans-table#borrower-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_LOANS",
                                null,
                                "Ссылка на карточку заемщика",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Ссылка на карточку заемщика из всех таблиц этой страницы",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab#loans-table#analysis-task-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_LOANS",
                                null,
                                "Ссылка на карточку Задания на анализ",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Ссылка на карточку Задания на анализ из всех таблиц этой страницы",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab#loans-table#create-quota-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_LOANS",
                                null,
                                "Кнопка \"Создать/обновить квоту\" БА",
                                List.of(
                                        Profile.BUSINESS_ADMINISTRATOR
                                ),
                                "Кнопка \"Создать/обновить квоту\"  аналогичная функционалу бизнес администратора",
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab#loans-table#create-quota-stbn-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_LOANS",
                                null,
                                "Кнопка \"Создать/обновить квоту\" - куратор СТБН",
                                List.of(
                                        Profile.CURATOR_STBN, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab#loans-table#create-quota-dnszko-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_LOANS",
                                null,
                                "Кнопка \"Сформировать запрос в ЛК\" - куратор ДНСЗКО",
                                List.of(
                                        Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                "Создание или обновление квоты с последующей возможностью отправки запроса в ЛК",
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab#loans-table#additional-filters",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_LOANS",
                                null,
                                "Дополнительная фильтрация и сортировка",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab#loans-table#additional-filters#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_LOANS",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Тип контрагента &#13;&#10;2) Период привязки первички",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab#loans-table#additional-filters#conclusion-filters",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_LOANS",
                                null,
                                "Источник данных по анализу",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Источник данных по анализу: &#13;&#10;1) Селект с выбором источника &#13;&#10;2) Чекбокс \"С учетом статуса \"В работе\"\"",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab#loans-table#additional-filters#conclusion-filters#actual",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_LOANS",
                                null,
                                "Актуальная оценка ссуд",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Актуальная оценка ссуд",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab#loans-table#additional-filters#conclusion-filters#curator",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_LOANS",
                                null,
                                "Оценка куратор",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab#loans-table#additional-filters#conclusion-filters#sar",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_LOANS",
                                null,
                                "Оценка САР",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab#loans-table#additional-filters#conclusion-filters#gibr",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_LOANS",
                                null,
                                "Оценка ГИБР",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab#loans-table#update-min-package-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_LOANS",
                                null,
                                "Кнопка \"Обновить мин пакет\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab#loans-table#documents-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_LOANS",
                                null,
                                "Кнопка \"Документы\"",
                                List.of(
                                        Profile.BUSINESS_ADMINISTRATOR, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab#loans-table#mesasure-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_LOANS",
                                null,
                                "Кнопка \"Применить меру\"",
                                List.of(
                                        Profile.CURATOR_STBN, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab#loans-table#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_LOANS",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Таблица со списком ссуд &#13;&#10;2) Выгрузка в excel &#13;&#10;3) Кнопка ТОП",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab#loans-table#view#open",
                                PermissionType.ACTION,
                                "GET_CO_ASSETS_NEW_LOANS_DIVISION_OF_INFORMATION",
                                null,
                                "Информация в свободном доступе",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "открытая",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-loans-tab#loans-table#view#close",
                                PermissionType.ACTION,
                                "GET_CO_ASSETS_NEW_LOANS_DIVISION_OF_INFORMATION",
                                null,
                                "Надзорная информация",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "закрытая",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"Реструктурированные ссуды и транши\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab#loans-table",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_RESTRUCTED_LOANS",
                                null,
                                "Таблица \"Список ссуд\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab#loans-table#co-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_RESTRUCTED_LOANS",
                                null,
                                "Ссылка на Карточку КО",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Ссылка на Карточку КО из всех таблиц этой страницы",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab#loans-table#loan-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_RESTRUCTED_LOANS",
                                null,
                                "Ссылка на карточку ссуды",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Ссылка на карточку ссуды из всех таблиц этой страницы",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab#loans-table#borrower-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_RESTRUCTED_LOANS",
                                null,
                                "Ссылка на карточку заемщика",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Ссылка на карточку заемщика из всех таблиц этой страницы",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab#loans-table#analysis-task-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_RESTRUCTED_LOANS",
                                null,
                                "Ссылка на карточку Задания на анализ",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Ссылка на карточку Задания на анализ из всех таблиц этой страницы",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab#loans-table#create-quota-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_RESTRUCTED_LOANS",
                                null,
                                "Кнопка \"Создать/обновить квоту\" БА",
                                List.of(
                                        Profile.BUSINESS_ADMINISTRATOR
                                ),
                                "Кнопка \"Создать/обновить квоту\"  аналогичная функционалу бизнес администратора",
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab#loans-table#create-quota-stbn-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_RESTRUCTED_LOANS",
                                null,
                                "Кнопка \"Создать/обновить квоту\" - куратор СТБН",
                                List.of(
                                        Profile.CURATOR_STBN, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab#loans-table#create-quota-dnszko-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_RESTRUCTED_LOANS",
                                null,
                                "Кнопка \"Сформировать запрос в ЛК\" - куратор ДНСЗКО",
                                List.of(
                                        Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                "Создание или обновление квоты с последующей возможностью отправки запроса в ЛК",
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab#loans-table#additional-filters",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_RESTRUCTED_LOANS",
                                null,
                                "Дополнительная фильтрация и сортировка",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab#loans-table#additional-filters#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_RESTRUCTED_LOANS",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Тип контрагента &#13;&#10;2) Период привязки первички",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab#loans-table#additional-filters#conclusion-filters",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_RESTRUCTED_LOANS",
                                null,
                                "Источник данных по анализу",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Источник данных по анализу: &#13;&#10;1) Селект с выбором источника &#13;&#10;2) Чекбокс \"С учетом статуса \"В работе\"\"",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab#loans-table#additional-filters#conclusion-filters#actual",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_RESTRUCTED_LOANS",
                                null,
                                "Актуальная оценка ссуд",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Актуальная оценка ссуд",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab#loans-table#additional-filters#conclusion-filters#curator",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_RESTRUCTED_LOANS",
                                null,
                                "Оценка куратор",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab#loans-table#additional-filters#conclusion-filters#sar",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_RESTRUCTED_LOANS",
                                null,
                                "Оценка САР",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab#loans-table#additional-filters#conclusion-filters#gibr",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_RESTRUCTED_LOANS",
                                null,
                                "Оценка ГИБР",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab#loans-table#update-min-package-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_RESTRUCTED_LOANS",
                                null,
                                "Кнопка \"Обновить мин пакет\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab#loans-table#documents-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_RESTRUCTED_LOANS",
                                null,
                                "Кнопка \"Документы\"",
                                List.of(
                                        Profile.BUSINESS_ADMINISTRATOR, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab#loans-table#mesasure-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_RESTRUCTED_LOANS",
                                null,
                                "Кнопка \"Применить меру\"",
                                List.of(
                                        Profile.CURATOR_STBN, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab#loans-table#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_RESTRUCTED_LOANS",
                                null,
                                "Право на просмотр информации:",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Таблица со списком ссуд &#13;&#10;2) Выгрузка в excel &#13;&#10;3) Кнопка ТОП",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab#loans-table#view#open",
                                PermissionType.ACTION,
                                "GET_CO_ASSETS_RESTRUCTURED_LOANS_DIVISION_OF_INFORMATION",
                                null,
                                "Информация в свободном доступе",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "открытая",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#restructured-loans-tab#loans-table#view#close",
                                PermissionType.ACTION,
                                "GET_CO_ASSETS_RESTRUCTURED_LOANS_DIVISION_OF_INFORMATION",
                                null,
                                "Надзорная информация",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "закрытая",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"Реестр заемщиков\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_BORROWERS",
                                null,
                                "Таблица \"Список заемщиков",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table#additional-filters",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_BORROWERS",
                                null,
                                "Дополнительная фильтрация и сортировка",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table#additional-filters#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_BORROWERS",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Тип контрагента &#13;&#10;2) Вхождение в период в разрезе банков",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table#additional-filters#conclusion-filters",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_BORROWERS",
                                null,
                                "Источник данных по анализу",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Источник данных по анализу: &#13;&#10;1) Селект с выбором источника &#13;&#10;2) Чекбокс \"С учетом статуса \"В работе\"\"",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table#additional-filters#conclusion-filters#actual",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_BORROWERS",
                                null,
                                "Актуальная оценка ссуд",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table#additional-filters#conclusion-filters#curator",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_BORROWERS",
                                null,
                                "Оценка куратор",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table#additional-filters#conclusion-filters#sar",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_BORROWERS",
                                null,
                                "Оценка САР",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table#additional-filters#conclusion-filters#gibr",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_BORROWERS",
                                null,
                                "Оценка ГИБР",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table#top-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_BORROWERS",
                                null,
                                "Кнопка \"ТОП\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table#additional-rows",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_BORROWERS",
                                null,
                                "Дополнительные строки",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Дополнительные строки (иконка \"Плюс\" в таблице)",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table#export-excel-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_BORROWERS",
                                null,
                                "Кнопка \"Выгрузить в Excel\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table#export-excel-but#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_BORROWERS",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Возможность выгрузки в excel",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table#export-excel-but#excel-but-with-modal",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_BORROWERS",
                                null,
                                "Модальное окно с параметрами выгрузки (по кнопке \"Выгрузить в Excel\")",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table#export-excel-but#excel-but-with-modal#with-section-checkbox",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_BORROWERS",
                                null,
                                "Чек-бокс \"Учесть при выгрузке разделы\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table#export-excel-but#excel-but-with-modal#with-add-rows-checkbox",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_BORROWERS",
                                null,
                                "Чек-бокс \"Выгрузить с дополнительными строками\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table#search-pre-filteration",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_BORROWERS",
                                null,
                                "Предфильтрация таблицы",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Предфильтрация таблицы: &#13;&#10;1. Выбор периода &#13;&#10;2) Кнопка \"Найти заемщиков\"",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table#borrower-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_BORROWERS",
                                null,
                                "Ссылка на карточку заемщика",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table#gk-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_BORROWERS",
                                null,
                                "Ссылка на карточку ГК",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table#loan-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_BORROWERS",
                                null,
                                "Ссылка на карточку ссуды",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_BORROWERS",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Таблица с заёмщиками",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table#view#open",
                                PermissionType.ACTION,
                                "GET_CO_ASSETS_NEW_BORROWERS_DIVISION_OF_INFORMATION",
                                null,
                                "Информация в свободном доступе",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "открытая",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#borrowers-tab#borrowers-table#view#close",
                                PermissionType.ACTION,
                                "GET_CO_ASSETS_NEW_BORROWERS_DIVISION_OF_INFORMATION",
                                null,
                                "Надзорная информация",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "закрытая",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-borrowers-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"Новые заемщики\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-borrowers-tab#new-borrowers-table",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_BORROWERS",
                                null,
                                "Таблица \"Новые заемщики\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-borrowers-tab#new-borrowers-table#borrower-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_BORROWERS",
                                null,
                                "Ссылка на карточку заемщика",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-borrowers-tab#new-borrowers-table#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_NEW_BORROWERS",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Таблица с заёмщиками &#13;&#10;2) Выгрузка в excel",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-borrowers-tab#new-borrowers-table#view#open",
                                PermissionType.ACTION,
                                "GET_CO_ASSETS_NEW_BORROWERS_DIVISION_OF_INFORMATION",
                                null,
                                "Информация в свободном доступе",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "открытая",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#new-co-borrowers-tab#new-borrowers-table#view#close",
                                PermissionType.ACTION,
                                "GET_CO_ASSETS_NEW_BORROWERS_DIVISION_OF_INFORMATION",
                                null,
                                "Надзорная информация",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "закрытая",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#reports-collateral-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"Реестр залогов\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#reports-collateral-tab#collateral-table",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_REPORTS_COLLATERAL",
                                null,
                                "Таблица \"Реестр залогов\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#reports-collateral-tab#collateral-table#borrower-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_REPORTS_COLLATERAL",
                                null,
                                "Ссылка на карточку заемщика",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#reports-collateral-tab#collateral-table#loan-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_REPORTS_COLLATERAL",
                                null,
                                "Ссылка на карточку ссуды",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#reports-collateral-tab#collateral-table#co-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_REPORTS_COLLATERAL",
                                null,
                                "Ссылка на карточку КО",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#reports-collateral-tab#collateral-table#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_REPORTS_COLLATERAL",
                                null,
                                "Право на просмотр информации:",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Таблица \"Реестр залогов\" &#13;&#10;2) Выгрузка в excel таблицы \"Реестр залогов\" &#13;&#10;3) Модальное окно \"История изменений\" &#13;&#10;4) Выгрузка в excel таблицы \"История изменений\" &#13;&#10;5) Модальное окно \"Объект заложен в нескольких ссудах\" &#13;&#10;6) Модальное окно \"Вид имущества\" &#13;&#10;7) Модальное окно \"Адрес\" &#13;&#10;8) Блок с количественной и суммовой информацией &#13;&#10;9) Модальное окно \"Заключения САР по оценке группы залоговых объектов\" &#13;&#10;10) Модальное окно \"Заключения САР по оценке залогового объекта\" &#13;&#10;11) Выгрузка в excel заключения САР по оценке группы залоговых объектов &#13;&#10;12) Выгрузка в ворд заключения САР по оценке группы залоговых объектов &#13;&#10;13) Выгрузка в excel заключения САР по оценке залогового объекта &#13;&#10;14) Выгрузка в ворд заключения САР по оценке залогового объекта",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"Погашенные ссуды\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab#loans-table",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_REPAID_LOANS",
                                null,
                                "Таблица \"Список ссуд\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab#loans-table#co-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_REPAID_LOANS",
                                null,
                                "Ссылка на Карточку КО",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Ссылка на Карточку КО из всех таблиц этой страницы",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab#loans-table#loan-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_REPAID_LOANS",
                                null,
                                "Ссылка на карточку ссуды",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Ссылка на карточку ссуды из всех таблиц этой страницы",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab#loans-table#borrower-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_REPAID_LOANS",
                                null,
                                "Ссылка на карточку заемщика",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Ссылка на карточку заемщика из всех таблиц этой страницы",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab#loans-table#analysis-task-link",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_REPAID_LOANS",
                                null,
                                "Ссылка на карточку Задания на анализ",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Ссылка на карточку Задания на анализ из всех таблиц этой страницы",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab#loans-table#create-quota-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_REPAID_LOANS",
                                null,
                                "Кнопка \"Создать/обновить квоту\" БА",
                                List.of(
                                        Profile.BUSINESS_ADMINISTRATOR
                                ),
                                "Кнопка \"Создать/обновить квоту\"  аналогичная функционалу бизнес администратора",
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab#loans-table#create-quota-stbn-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_REPAID_LOANS",
                                null,
                                "Кнопка \"Создать/обновить квоту\" - куратор СТБН",
                                List.of(
                                        Profile.CURATOR_STBN, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab#loans-table#create-quota-dnszko-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_REPAID_LOANS",
                                null,
                                "Кнопка \"Создать/обновить квоту\" - куратор ДНСЗКО",
                                List.of(
                                        Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab#loans-table#additional-filters",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_REPAID_LOANS",
                                null,
                                "Дополнительная фильтрация и сортировка",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab#loans-table#additional-filters#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_REPAID_LOANS",
                                null,
                                "Право на просмотр информации:",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Тип контрагента &#13;&#10;2) Период привязки первички",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab#loans-table#additional-filters#conclusion-filters",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_REPAID_LOANS",
                                null,
                                "Источник данных по анализу",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Источник данных по анализу: &#13;&#10;1) Селект с выбором источника &#13;&#10;2) Чекбокс \"С учетом статуса \"В работе\"\"",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab#loans-table#additional-filters#conclusion-filters#actual",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_REPAID_LOANS",
                                null,
                                "Актуальная оценка ссуд",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Актуальная оценка ссуд",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab#loans-table#additional-filters#conclusion-filters#curator",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_REPAID_LOANS",
                                null,
                                "Оценка куратор",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab#loans-table#additional-filters#conclusion-filters#sar",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_REPAID_LOANS",
                                null,
                                "Оценка САР",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab#loans-table#additional-filters#conclusion-filters#gibr",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_REPAID_LOANS",
                                null,
                                "Оценка ГИБР",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab#loans-table#update-min-package-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_REPAID_LOANS",
                                null,
                                "Кнопка \"Обновить мин пакет\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab#loans-table#documents-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_REPAID_LOANS",
                                null,
                                "Кнопка \"Документы\"",
                                List.of(
                                        Profile.BUSINESS_ADMINISTRATOR, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab#loans-table#mesasure-but",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_REPAID_LOANS",
                                null,
                                "Кнопка \"Применить меру\"",
                                List.of(
                                        Profile.CURATOR_STBN, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab#loans-table#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_REPAID_LOANS",
                                null,
                                "Право на просмотр информации:",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Таблица со списком ссуд &#13;&#10;2) Выгрузка в excel &#13;&#10;3) Кнопка ТОП",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab#loans-table#view#open",
                                PermissionType.ACTION,
                                "GET_CO_ASSETS_REPAID_LOANS_DIVISION_OF_INFORMATION",
                                null,
                                "Информация свободном доступе",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "открытая",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#repaid-loans-tab#loans-table#view#close",
                                PermissionType.ACTION,
                                "GET_CO_ASSETS_REPAID_LOANS_DIVISION_OF_INFORMATION",
                                null,
                                "Надзорная информация",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "закрытая",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#loan-portfolio-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"Анализ кредитного портфеля\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#loan-portfolio-tab#loan-portfolio",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_LOAN_PORTFOLIO",
                                null,
                                "Блок \"Анализ кредитного портфеля\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#loan-portfolio-tab#loan-portfolio#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_LOAN_PORTFOLIO",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Таблица \"анализ кредитного портфеля\" &#13;&#10;2) Таблица \"структура портфеля по категории качества\"",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#tasks-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"Реестр заданий на анализ\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#tasks-tab#co-task-table",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_TASKS",
                                null,
                                "Таблица \"Список заданий\" (КО)",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#tasks-tab#co-task-table#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_ASSETS_TASKS",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Таблица со списком заданий",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#tasks-tab#co-task-table#view#open",
                                PermissionType.ACTION,
                                "GET_CO_ASSETS_TASKS_DIVISION_OF_INFORMATION",
                                null,
                                "Информация в свободном доступе",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "открытая",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#credit-risk-tab#tasks-tab#co-task-table#view#close",
                                PermissionType.ACTION,
                                "GET_CO_ASSETS_TASKS_DIVISION_OF_INFORMATION",
                                null,
                                "Надзорная информация",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "закрытая",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#retail-credit-risk-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"7.1.3. Кредитный риск ФЛ\"",
                                List.of(
                                        Profile.REGIONAL_CURATOR, Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#retail-credit-risk-tab#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.REGIONAL_CURATOR, Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Блок с заключением &#13;&#10;2) Кнопка \"Выгрузить\": &#13;&#10;     - Excel &#13;&#10;     - Word",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#market-risk-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"7.1.5. Рыночный риск\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#market-risk-tab#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Блоки со всеми видами заключений, в том числе и подвидов заключений для \"Кросс-анализа отчетности\" &#13;&#10;2) Выбор версии заключения в каждом блоке вида заключения &#13;&#10;3) Заключение по результатам оценки активов &#13;&#10;4) Выгрузка в excel таблицы с результатами оценки каждого вида заключения &#13;&#10;5) Скачивание прикрепленных документов к заключению &#13;&#10;6) Кнопка \"Раскрыть все\" &#13;&#10;7) Кнопка \"скрыть все\" &#13;&#10;8) Ссылка на карточку заемщика",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#operational-risk-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"7.1.6. Операционные риски\"",
                                List.of(
                                        Profile.REGIONAL_CURATOR, Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#operational-risk-tab#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.REGIONAL_CURATOR, Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Блок с заключением &#13;&#10;2) Кнопка \"Выгрузить\": &#13;&#10;     - Excel &#13;&#10;     - Word",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#asset-conclusions-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"7.1.7. Заключения по активам КО\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#interaction-spbr-tab#sar-tab#asset-conclusions-tab#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Блок \"Актуальная информация о непрофильных активах\" &#13;&#10;2) Выбор версии заключения &#13;&#10;3) Заключение по результатам оценки активов &#13;&#10;4) Выгрузка в excel таблицы с результатами оценки активов КО &#13;&#10;5) Модальное окно \"Заключения САР по оценке группы активов\" &#13;&#10;6) Модальное окно \"Заключения САР по оценке актива\" &#13;&#10;7) Выгрузка в excel заключения САР по оценке группы активов &#13;&#10;8) Выгрузка архива с заключением САР по оценке группы активов и прикрепленных документов &#13;&#10;9) Выгрузка в excel заключения САР по оценке актива &#13;&#10;10) Выгрузка архива с заключением САР по оценке актива и прикрепленных документов &#13;&#10;11) Скачивание прикрепленных документов к заключению",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#reports-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"8. Отчетность\"",
                                List.of(
                                        Profile.REGIONAL_CURATOR, Profile.EMPLOYEE_SAR, Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#reports-tab#regulatory-reports-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"8.1. Регламентная отчетность\"",
                                List.of(
                                        Profile.REGIONAL_CURATOR, Profile.EMPLOYEE_SAR, Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#reports-tab#regulatory-reports-tab#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.REGIONAL_CURATOR, Profile.EMPLOYEE_SAR, Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Селект \"Отчетная форма\" &#13;&#10;2) Селект \"Отчетная дата\" &#13;&#10;3) Кнопка перейти",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#reports-tab#msfo-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"1.4. МСФО\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#reports-tab#msfo-tab#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Таблица &#13;&#10;2) Выгрузка в excel &#13;&#10;3) Выгрузка аттачей",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#documents-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"9. Документы\"",
                                List.of(
                                        Profile.REGIONAL_CURATOR, Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#documents-tab#files-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"9.1. Все документы\"",
                                List.of(
                                        Profile.REGIONAL_CURATOR, Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#documents-tab#files-tab#file-explorer",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Дерево файлов",
                                List.of(
                                        Profile.REGIONAL_CURATOR, Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#documents-tab#files-tab#file-explorer#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.REGIONAL_CURATOR, Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Дерево файлов &#13;&#10;2) Возможно скачать файл &#13;&#10;3) Возможно выгрузить в excel",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#documents-tab#letters-templates-tab",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"9.2. Формирование шаблонов писем\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#documents-tab#letters-templates-tab#templates-table",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_REPORTS_LETTERS",
                                null,
                                "Таблица \"Список шаблонов\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                null,
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#documents-tab#letters-templates-tab#templates-table#write",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_REPORTS_LETTERS",
                                null,
                                "Право на редактирование",
                                List.of(
                                        Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на редактирование: &#13;&#10;1) Кнопка \"Создать\" &#13;&#10;2) Кнопка \"Изменить активный\"",
                                List.of(
                                        TreeType.KO
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#documents-tab#letters-templates-tab#templates-table#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_REPORTS_LETTERS",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Таблица со списком шаблонов &#13;&#10;2) Возможность скачать шаблон",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )

                );


        var information = new Information(KEY_TEXT, AUTHOR, STORY_NUMBER, TAB_NAME, true, true);

        ChangeLog changeLog = new ChangeLog(information, permissions);
        changeLog.create();
    }
}
