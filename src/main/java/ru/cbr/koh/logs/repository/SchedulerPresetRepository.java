package ru.cbr.koh.logs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cbr.koh.logs.domain.SchedulerPresetEntity;

import java.util.Optional;

public interface SchedulerPresetRepository extends JpaRepository<SchedulerPresetEntity, Long> {

    Optional<SchedulerPresetEntity> findByName(String name);
}
