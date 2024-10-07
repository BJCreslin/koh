package ru.cbr.koh;

import ru.cbr.koh.panes_storage.panels.permission_migration.information.domain.Information;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.base_clases.ChangeLog;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.PermissionType;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.TreeType;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

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
        List<Profile> profiles = List.of(
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

        List<Permission> permissions = List.of(
                new Permission(
                        "sar-conclusions#collateral-conclusions-tab",
                        PermissionType.ACTION,
                        "GET_PERMISSIONS_SAR_CONCLUSION",
                        null,
                        "Боковое меню «Заключения по залоговым объектам»",
                        profiles,
                        null,
                        List.of(TreeType.GIBR, TreeType.KO)
                ),

                new Permission(
                        "sar-conclusions#collateral-conclusions-tab#collateral-conclusions-table",
                        PermissionType.ACTION,
                        "GET_PERMISSIONS_SAR_CONCLUSION",
                        null,
                        "Таблица «Заключения по залоговым объектам»",

                        profiles,
                        null,
                        List.of(TreeType.GIBR, TreeType.KO)
                ),
                //Ко- зависимый пермишн
                new Permission(
                        "sar-conclusions#collateral-conclusions-tab#view",
                        PermissionType.ACTION,
                        "GET_PERMISSIONS_SAR_CONCLUSION",
                        "GET_PERMISSIONS_SAR_CONCLUSION_CONCLUSION_TAB_VIEW",
                        "Боковое меню «Заключения по залоговым объектам»",
                        profiles,
                        "Право на просмотр информации:&#13;&#10; 1) Таблица с заключениями по залогам &#13;&#10;" +
                        "2) Выгрузка в Excel",
                        List.of(TreeType.GIBR, TreeType.KO)
                )
        );

        var information = new Information(KEY_TEXT, AUTHOR, STORY_NUMBER, TAB_NAME);

        ChangeLog changeLog = new ChangeLog(information, permissions);
        changeLog.create();
    }
}
