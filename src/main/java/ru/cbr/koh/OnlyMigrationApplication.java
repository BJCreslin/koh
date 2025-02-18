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

    private static final String KEY_TEXT = "subord_instrument";

    private static final String AUTHOR = "69KreslinVYU";

    private static final String STORY_NUMBER = "PDKO-267(PDKO-256)";

    private static final String TAB_NAME = "Реализовать работу с субординироваными инструментами";

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
                                "credit-organisation-card#Financial-analysis-tab#capital-tab",
                                PermissionType.COMPONENT,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"5.4 Капитал\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Раздел \"5.4 Капитал\"",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#Financial-analysis-tab#capital-tab#subord-tab",
                                PermissionType.COMPONENT,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Раздел \"5.4.2 Субординированные инструменты\"",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Раздел \"5.4.2 Субординированные инструменты\"",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#Financial-analysis-tab#capital-tab#subord-tab#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                null,
                                "Право на просмотр информации",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Таблица с данными &#13;&#10;2) Выгрузка в excel &#13;&#10;3) Оценка ставки",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "credit-organisation-card#Financial-analysis-tab#capital-tab#subord-tab#write",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_CO_CARD",
                                "GET_KO_LIST_CO_CARD_SUBORD_TAB_WRITE",
                                "Право на добавление инструментов",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на добавление инструментов",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "subord",
                                PermissionType.COMPONENT,
                                "GET_MAIN_PERMISSIONS",
                                null,
                                "Субординированные инструменты",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Субординированные инструменты",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "subord#view",
                                PermissionType.ACTION,
                                "GET_PERMISSIONS_SUBORD",
                                "GET_KO_LIST_SUBORD_VIEW",
                                "Право на просмотр информации",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на просмотр информации: &#13;&#10;1) Таблица с данными &#13;&#10;2) Выгрузка в excel &#13;&#10;3) Оценка ставки",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                        ,
                        new Permission(
                                "subord#edit",
                                PermissionType.COMPONENT,
                                "GET_PERMISSIONS_SUBORD",
                                "GET_KO_LIST_SUBORD_EDIT",
                                "Право на добавление инструментов",
                                List.of(
                                        Profile.AUDITOR, Profile.BUSINESS_ANALYST_GIBR, Profile.BUSINESS_ADMINISTRATOR, Profile.CURATOR_STBN, Profile.CURATOR_GIBR, Profile.CURATOR_DFS, Profile.CURATOR_DNSZKO, Profile.MANAGER_CURATOR_OBN, Profile.COORDINATOR_STBN, Profile.COORDINATOR_DNSZKO, Profile.ANALYST_STBN, Profile.ANALYST_DNSZKO, Profile.METHODOLOGIST_STBN, Profile.METHODOLOGIST_DNSZKO
                                ),
                                "Право на добавление инструментов",
                                List.of(
                                        TreeType.KO, TreeType.GIBR
                                )
                        )
                );


        var information = new Information(KEY_TEXT, AUTHOR, STORY_NUMBER, TAB_NAME, true, true, true);

        ChangeLog changeLog = new ChangeLog(information, permissions);
        changeLog.create();
    }
}
