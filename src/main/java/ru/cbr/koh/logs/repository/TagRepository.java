package ru.cbr.koh.logs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cbr.koh.logs.domain.TagEntity;

import java.util.List;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

    List<TagEntity> findByErrorGroupId(Long errorGroupId);

    List<TagEntity> findByLogEventId(Long logEventId);
}
