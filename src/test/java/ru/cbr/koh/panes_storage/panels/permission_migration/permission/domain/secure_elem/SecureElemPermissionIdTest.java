package ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.secure_elem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SecureElemPermissionIdTest {

    @Test
    void shouldReturnStableIdAcrossMultipleToStringCalls() {
        SecureElemPermissionId permissionId = new SecureElemPermissionId("test_key");

        String first = permissionId.toString();
        String second = permissionId.toString();

        assertEquals(first, second);
    }
}
