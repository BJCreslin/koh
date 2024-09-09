package ru.cbr.koh.panes_storage.panels.permission.domain.secure_elem;


import ru.cbr.koh.panes_storage.panels.permission.domain.Permission;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SecureElemMigrationContentRollback {

    private final String keyText;

    private static final String TEMPLATE = """
                <delete tableName="rights_template_profile_secur_elem">
                            <where>
                                secur_elem_id IN(
                                SELECT id FROM secur_elem WHERE key IN (
                                %s
                                ))
                            </where>
                        </delete>
                        
                        <delete tableName="secur_elem_perm">
                            <where>
                                secur_elem_id IN (
                                 SELECT id FROM secur_elem WHERE key IN (
                                %s
                                ))
                            </where>
                        </delete>
                        
                        <delete tableName="secur_elem_ko_perm">
                            <where>
                                secur_elem_id IN (
                                SELECT id FROM secur_elem WHERE key IN (
                                %s
                                ))
                            </where>
                        </delete>
                        
                        <delete tableName="secur_elem">
                            <where>
                                key IN (
                                %s
                                )
                            </where>
                        </delete> \n
            """;

    public SecureElemMigrationContentRollback(Permission[] permissions) {
        this.keyText =
                Arrays.stream(permissions).map(Permission::getKey).map(it -> "'" + it + "'")
                        .collect(Collectors.joining(", \n\t\t\t\t\t"));
    }

    @Override
    public String toString() {
        return String.format(TEMPLATE, this.keyText, this.keyText, this.keyText, this.keyText);
    }
}
