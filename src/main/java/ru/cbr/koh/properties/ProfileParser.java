package ru.cbr.koh.properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Сервис для парсинга профилей из конфигурации
 */
public class ProfileParser {
    
    private static final Logger logger = LogManager.getLogger(ProfileParser.class);
    
    private final Properties properties;
    
    public ProfileParser(Properties properties) {
        this.properties = properties;
    }
    
    /**
     * Получить все профили
     */
    public List<Profile> getAllProfiles() {
        return parseProfiles("migration.profiles.all");
    }
    
    /**
     * Получить профили без SAR и регионального куратора
     */
    public List<Profile> getAllWithoutSarAndRegionalCurator() {
        return parseProfiles("migration.profiles.allWithoutSarAndRegionalCurator");
    }
    
    /**
     * Получить профили без SAR
     */
    public List<Profile> getAllWithoutSar() {
        return parseProfiles("migration.profiles.allWithoutSar");
    }
    
    /**
     * Получить профили без SAR, регионального куратора и координатора-аналитика-методолога ДНСЗКО
     */
    public List<Profile> getAllWithoutSarAndRegionalCuratorAndCoordinatorAnalystMethotologDNSZKO() {
        return parseProfiles("migration.profiles.allWithoutSarAndRegionalCuratorAndCoordinatorAnalystMethotologDNSZKO");
    }
    
    /**
     * Получить профили БА и другие
     */
    public List<Profile> getBaAndOther() {
        return parseProfiles("migration.profiles.baAndOther");
    }
    
    /**
     * Парсинг профилей по ключу свойства
     */
    private List<Profile> parseProfiles(String propertyKey) {
        String profilesString = properties.getProperty(propertyKey);
        if (profilesString == null || profilesString.isEmpty()) {
            logger.warn("Профили для ключа {} не найдены", propertyKey);
            return List.of();
        }
        
        return Arrays.stream(profilesString.split(","))
                .map(String::trim)
                .map(this::parseProfile)
                .filter(profile -> profile != null)
                .collect(Collectors.toList());
    }
    
    /**
     * Парсинг отдельного профиля
     */
    private Profile parseProfile(String profileName) {
        try {
            return Profile.valueOf(profileName);
        } catch (IllegalArgumentException e) {
            logger.error("Неизвестный профиль: {}", profileName, e);
            return null;
        }
    }
}

