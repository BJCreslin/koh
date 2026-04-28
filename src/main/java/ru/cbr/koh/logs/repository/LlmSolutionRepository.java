package ru.cbr.koh.logs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cbr.koh.logs.domain.LlmSolutionEntity;

import java.util.Optional;

public interface LlmSolutionRepository extends JpaRepository<LlmSolutionEntity, Long> {

    Optional<LlmSolutionEntity> findFirstByErrorGroupIdOrderByCreatedAtDesc(Long errorGroupId);
}
