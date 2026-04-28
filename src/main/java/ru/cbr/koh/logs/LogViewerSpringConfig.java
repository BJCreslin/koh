package ru.cbr.koh.logs;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "ru.cbr.koh.logs.repository")
public class LogViewerSpringConfig {
}
