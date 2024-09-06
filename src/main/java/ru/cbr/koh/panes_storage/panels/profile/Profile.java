package ru.cbr.koh.panes_storage.panels.profile;

/**
 * Прфоиль
 */
public enum Profile {
    BUSINESS_ADMIN_CO(2, "Бизнес администратор КО"),
    REGIONAL_CURATOR(10, "Региональный куратор"),
    EMPLOYEE_SAR(11, "Сотрудник САР"),
    AUDITOR(12, "Аудитор"),
    BUSINESS_ANALYST_GIBR(13, "Бизнес-аналитик ГИБР"),
    BUSINESS_ADMINISTRATOR(14, "Бизнес-администратор"),
    COORDINATOR_STBN(15, "Координатор СТБН"),
    CURATOR_GIBR(16, "Куратор ГИБР"),
    CURATOR_DFS(17, "Куратор ДФС"),
    CURATOR_DNSZKO(18, "Куратор ДНСЗКО"),
    COORDINATOR_DNSZKO(19, "Координатор ДНСЗКО"),
    CURATOR_STBN(20, "Куратор СТБН"),
    MANAGER_CURATOR_OBN(21, "Руководитель Куратора (ОБН)"),
    ANALYST_STBN(22, "Аналитик СТБН"),
    ANALYST_DNSZKO(23, "Аналитик ДНСЗКО"),
    METHODOLOGIST_DNSZKO(24, "Методолог ДНСЗКО"),
    METHODOLOGIST_STBN(25, "Методолог СТБН");

    private final int id;
    private final String name;

    Profile(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return name;
    }

    public static Profile getProfileByName(String name) {
        for (Profile profile : Profile.values()) {
            if (profile.getName().equals(name)) {
                return profile;
            }
        }
        return null;
    }
}