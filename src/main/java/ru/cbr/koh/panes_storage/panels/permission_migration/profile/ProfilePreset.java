package ru.cbr.koh.panes_storage.panels.permission_migration.profile;

import java.util.EnumSet;
import java.util.Set;

public enum ProfilePreset {
    ALL("All profiles") {
        @Override
        public Set<Profile> profiles() {
            return EnumSet.allOf(Profile.class);
        }
    },
    ALL_WITHOUT_SAR("All without САР") {
        @Override
        public Set<Profile> profiles() {
            EnumSet<Profile> result = EnumSet.allOf(Profile.class);
            result.remove(Profile.EMPLOYEE_SAR);
            return result;
        }
    },
    ALL_WITHOUT_SAR_AND_REGIONAL("All without САР and regional") {
        @Override
        public Set<Profile> profiles() {
            EnumSet<Profile> result = EnumSet.allOf(Profile.class);
            result.remove(Profile.EMPLOYEE_SAR);
            result.remove(Profile.REGIONAL_CURATOR);
            return result;
        }
    },
    EMPTY("Clear selection") {
        @Override
        public Set<Profile> profiles() {
            return EnumSet.noneOf(Profile.class);
        }
    };

    private final String title;

    ProfilePreset(String title) {
        this.title = title;
    }

    public abstract Set<Profile> profiles();

    @Override
    public String toString() {
        return title;
    }
}
