package ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser;

import ru.cbr.koh.panes_storage.panels.permission_migration.permission.enums.TreeType;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

import java.util.List;
import java.util.stream.Collectors;

public record ParserPermission(
        boolean isBankDependent,
        String key,
        String politic,
        String name,
        List<Profile> profiles,
        String description,
        List<TreeType> types,
        String relKey,
        boolean needSave

) {

    private static final List<String> ACTION_TYPES = List.of("actions-journal-tab", "actual", "add-but", "additional-rows",
            "allow-only-my", "analysis-task-link", "archives-tab-link", "asset-conclusions-tab", "asset-conclusions-table",
            "borrower-cards-type", "borrower-link", "brw-conclusion-received-on-confirm-type", "brw-conclusions-create-on-confirm-type",
            "brw-gibr-conclusion-received-type", "brw-gk-sar-conclusion-created-type", "brw-zsk-type", "change-active-but",
            "change-comment-archives-but", "close", "co-but", "co-cards-type", "co-link", "collateral-conclusions-tab",
            "collateral-conclusions-table", "commit-but", "conclusion-draft-created-type", "conclusion-filter-but",
            "confirm-loan-conclusion-received-type", "consolidated-report-tab", "copy-file-but", "create-quota-but",
            "create-quota-dnszko-but", "create-quota-stbn-but", "curator", "curator-info", "data-source-0409303",
            "delete-archives-but", "delete-file-but", "delete-quota-but", "delete-task-but", "diff-upload-data-business-model-type",
            "documents-but", "download-data-button", "download-file-but", "download-orphans-but", "draft-but", "edit-beneficiary-but",
            "edit-company-name-but", "event-log-tab-link", "event-rule-manager-link", "export-excel-but", "file-download-but",
            "files-tab-link", "finalized-dates-mark", "finish-loans-completed-but", "finish-loans-finalized-but",
            "finish-loans-in-work-but", "finish-loans-with-draft-but", "gibr", "gibr-info", "gk-cards-type", "gk-link",
            "gk-load-received-type", "in-work-but", "large-brw-sar-conclusion-received-type", "lk-but", "lk-file-receive-type",
            "loan-cards-type", "loan-conclusions-create-on-confirm-type", "loan-gibr-conclusion-received-type", "loan-link",
            "loans-confirmed-in-quota-type", "logins-by-department-tab", "measure-but", "mesasure-but", "need-accept-quota-loan-type",
            "need-accept-return-loans-sar", "open", "operational-risks-type", "org-structure-report-but", "publish-but",
            "quota-loan-accept-col-edit", "reject-but", "rejected-brw-conclusion-received-type", "rejected-loan-conclusion-received-type",
            "rename-archives-but", "requests-table", "result-return-loans-sar", "retail-credit-risks-type", "return-to-sar-but", "sar",
            "sar-but", "sar-conclusion-market-risk-received-type", "sar-conclusion-operation-risk-received-type", "sar-conclusion-received-type",
            "sar-conclusion-uarkr-received-type", "sar-info", "search-pre-filteration", "send-to-sar-but", "show-only-my-files",
            "show-other-source-data-hint", "supervision-impulse-load-received-type", "task-link", "task-sar-expired-type", "top-but",
            "unpackage-bind-but", "update-co-but", "update-min-package-but", "upload-archives-but", "upload-files-but",
            "upload-orphans-but", "view", "view-file-but", "vkippd-link", "with-add-rows-checkbox", "with-section-checkbox",
            "write", "write-but");

    @Override
    public String toString() {
        if (needSave) {
            return String.format("""
                              new Permission(
                              "%s",
                              PermissionType.%s,
                              "%s",
                              %s,
                              "%s",
                              List.of(
                              %s
                              ),
                              %s,
                              List.of(
                              %s
                              )
                              )
                            """, key,
                    getPermissionType(),
                    politic,
                    isBankDependent ? "НАЗВАНИЕ КО ПОЛИТИКИ" : null,
                    name.replace("\"", "\\\"").trim(),
                    profiles.stream().map(it -> "Profile." + it.name()).collect(Collectors.joining(", ")),
                    description != null ? "\"" + description.replace("\"", "\\\"").trim() + "\"" : null,
//                    types.stream().map(it -> "TreeType." + it.name()).collect(Collectors.joining(", "))
                    " TreeType.KO, TreeType.GIBR"
            );
        }
        return "";
    }

    private String getPermissionType() {
        return (relKey != null && !relKey.isEmpty() && ACTION_TYPES.stream().anyMatch(it -> it.equals(relKey))) ? "ACTION"
                : "COMPONENT";
    }
}
