package ru.cbr.koh.panes_storage.panels.permission_migration.profile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProfileTest {

    @Test
    void shouldResolveProfileByCode() {
        Profile profile = Profile.getByCode("AUDITOR");
        assertEquals(Profile.AUDITOR, profile);
    }

    @Test
    void shouldResolveProfileByDisplayNameAsFallback() {
        Profile profile = Profile.getByCodeOrDisplayName("Аудитор");
        assertEquals(Profile.AUDITOR, profile);
    }

    @Test
    void shouldReturnNullForUnknownToken() {
        assertNull(Profile.getByCodeOrDisplayName("UNKNOWN_PROFILE_TOKEN"));
    }
}
