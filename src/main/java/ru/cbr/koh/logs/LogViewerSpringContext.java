package ru.cbr.koh.logs;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;
import java.util.Map;

public final class LogViewerSpringContext {

    private static ConfigurableApplicationContext context;

    private LogViewerSpringContext() {
    }

    public static synchronized ConfigurableApplicationContext getContext() {
        if (context == null) {
            context = new SpringApplicationBuilder(LogViewerSpringConfig.class)
                    .web(WebApplicationType.NONE)
                    .headless(false)
                    .properties(defaultProperties())
                    .run();
        }
        return context;
    }

    public static <T> T getBean(Class<T> type) {
        return getContext().getBean(type);
    }

    private static Map<String, Object> defaultProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("spring.datasource.url", "jdbc:h2:file:./koh-log-viewer-db/logs-viewer;AUTO_SERVER=TRUE");
        properties.put("spring.datasource.driverClassName", "org.h2.Driver");
        properties.put("spring.datasource.username", "sa");
        properties.put("spring.datasource.password", "");
        properties.put("spring.jpa.hibernate.ddl-auto", "update");
        properties.put("spring.jpa.open-in-view", "false");
        properties.put("spring.jpa.show-sql", "false");
        properties.put("spring.main.banner-mode", "off");
        properties.put("logging.level.org.springframework", "WARN");
        properties.put("logging.level.org.hibernate", "WARN");
        return properties;
    }
}
