package ru.cbr.koh.logs;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogViewerDatabaseMigration implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;

    public LogViewerDatabaseMigration(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        alterColumnIfTableExists("LOG_EVENTS", "MESSAGE", "CLOB");
        alterColumnIfTableExists("ERROR_GROUPS", "SAMPLE_MESSAGE", "CLOB");
    }

    private void alterColumnIfTableExists(String tableName, String columnName, String type) {
        Integer count = jdbcTemplate.queryForObject(
                "select count(*) from information_schema.tables where table_name = ?",
                Integer.class,
                tableName);
        if (count == null || count == 0) {
            return;
        }
        jdbcTemplate.execute("alter table " + tableName + " alter column " + columnName + " " + type);
    }
}
