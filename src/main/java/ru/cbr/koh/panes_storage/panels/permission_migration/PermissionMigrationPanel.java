package ru.cbr.koh.panes_storage.panels.permission_migration;

import ru.cbr.koh.app.AppContext;
import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.panes_storage.panels.permission_migration.excel.ExcelInputPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.InformationPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.InformationStorage;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.PermissionPanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.ProfilePanel;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.ProfileSelectionStorage;
import ru.cbr.koh.properties.ApplicationProperties;
import ru.cbr.koh.properties.PropertiesService;

import javax.swing.*;

public class PermissionMigrationPanel implements PaneInterface {

    private static final int INFORMATION_TAB_INDEX = 0;
    private static final int PROFILES_TAB_INDEX = 1;
    private static final int PERMISSION_TAB_INDEX = 2;
    private static final int EXCEL_TAB_INDEX = 3;

    private static final String DISABLED_SUFFIX = " (disabled)";
    private static final String EXCEL_MODE_REASON = "Unavailable while \"Input data from excel\" is enabled.";
    private static final String MANUAL_MODE_REASON = "Enable \"Input data from excel\" to open this tab.";

    private final AppContext appContext;

    private InformationPanel informationPanel;
    private ProfilePanel profilePanel;

    public PermissionMigrationPanel() {
        this(new AppContext(PropertiesService.getInstance()));
    }

    public PermissionMigrationPanel(AppContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public String getTitle() {
        return "permissions";
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        JTabbedPane nestedTabbedPane = new JTabbedPane();
        nestedTabbedPane.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        ApplicationProperties properties = appContext.getProperties();

        informationPanel = new InformationPanel(properties, new InformationStorage());
        String informationTitle = informationPanel.getTitle();
        nestedTabbedPane.addTab(informationTitle, informationPanel.createPanel(frame));

        profilePanel = new ProfilePanel(new ProfileSelectionStorage(), appContext.getErrorHandler());
        String profilesTitle = profilePanel.getTitle();
        nestedTabbedPane.addTab(profilesTitle, profilePanel.createPanel(frame));

        PermissionPanel permissionPanel = new PermissionPanel(
                informationPanel::getInformation,
                profilePanel::getCheckedProfiles,
                appContext.getPermissionMigrationService(),
                appContext.getTaskRunner(),
                appContext.getErrorHandler());
        String permissionTitle = permissionPanel.getTitle();
        nestedTabbedPane.addTab(permissionTitle, permissionPanel.createPanel(frame));

        ExcelInputPanel excelInputPanel = new ExcelInputPanel(
                informationPanel::getInformation,
                properties,
                appContext.getPermissionMigrationService(),
                appContext.getTaskRunner());
        String excelTitle = excelInputPanel.getTitle();
        nestedTabbedPane.addTab(excelTitle, excelInputPanel.createPanel(frame));

        setDefaultPanels(nestedTabbedPane, profilesTitle, permissionTitle, excelTitle);

        informationPanel.getExcelInputCheckBox().addItemListener(
                e -> {
                    boolean fromExcelEnabled = informationPanel.getExcelInputCheckBox().isSelected();
                    updateTabAvailability(nestedTabbedPane, profilesTitle, permissionTitle, excelTitle, fromExcelEnabled);
                }
        );

        return nestedTabbedPane;
    }

    @Override
    public void onClose() {
        if (informationPanel != null) {
            informationPanel.onClose();
        }
        if (profilePanel != null) {
            profilePanel.onClose();
        }
    }

    private void setDefaultPanels(JTabbedPane nestedTabbedPane,
                                  String profilesTitle,
                                  String permissionTitle,
                                  String excelTitle) {
        boolean fromExcelEnabled = informationPanel.getInformation().fromExcel();
        updateTabAvailability(nestedTabbedPane, profilesTitle, permissionTitle, excelTitle, fromExcelEnabled);
    }

    private void updateTabAvailability(JTabbedPane nestedTabbedPane,
                                       String profilesTitle,
                                       String permissionTitle,
                                       String excelTitle,
                                       boolean fromExcelEnabled) {
        boolean manualMode = !fromExcelEnabled;

        setTabState(nestedTabbedPane, PROFILES_TAB_INDEX, profilesTitle, manualMode, EXCEL_MODE_REASON);
        setTabState(nestedTabbedPane, PERMISSION_TAB_INDEX, permissionTitle, manualMode, EXCEL_MODE_REASON);
        setTabState(nestedTabbedPane, EXCEL_TAB_INDEX, excelTitle, fromExcelEnabled, MANUAL_MODE_REASON);

        int selectedIndex = nestedTabbedPane.getSelectedIndex();
        if (selectedIndex == -1) {
            nestedTabbedPane.setSelectedIndex(INFORMATION_TAB_INDEX);
            return;
        }

        if (!nestedTabbedPane.isEnabledAt(selectedIndex)) {
            nestedTabbedPane.setSelectedIndex(fromExcelEnabled ? EXCEL_TAB_INDEX : PROFILES_TAB_INDEX);
        }
    }

    private void setTabState(JTabbedPane nestedTabbedPane,
                             int tabIndex,
                             String title,
                             boolean enabled,
                             String disabledReason) {
        nestedTabbedPane.setEnabledAt(tabIndex, enabled);
        nestedTabbedPane.setTitleAt(tabIndex, enabled ? title : title + DISABLED_SUFFIX);
        nestedTabbedPane.setToolTipTextAt(tabIndex, enabled ? null : disabledReason);
    }
}
