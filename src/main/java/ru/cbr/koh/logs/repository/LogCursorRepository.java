package ru.cbr.koh.logs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cbr.koh.logs.domain.LogCursorEntity;

import java.util.Optional;

public interface LogCursorRepository extends JpaRepository<LogCursorEntity, Long> {

    Optional<LogCursorEntity> findBySourceKey(String sourceKey);
}
