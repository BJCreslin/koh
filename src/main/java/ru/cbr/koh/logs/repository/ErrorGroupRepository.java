package ru.cbr.koh.logs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cbr.koh.logs.domain.ErrorGroupEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ErrorGroupRepository extends JpaRepository<ErrorGroupEntity, Long> {

    Optional<ErrorGroupEntity> findByFingerprint(String fingerprint);

    List<ErrorGroupEntity> findTop500ByOrderByLastSeenDesc();

    void deleteByLastSeenBefore(LocalDateTime before);
}
