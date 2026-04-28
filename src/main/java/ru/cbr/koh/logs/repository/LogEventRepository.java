package ru.cbr.koh.logs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.cbr.koh.logs.domain.LogEventEntity;
import ru.cbr.koh.logs.domain.LogLevel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LogEventRepository extends JpaRepository<LogEventEntity, Long> {

    Optional<LogEventEntity> findByEventHash(String eventHash);

    List<LogEventEntity> findTop500BySchedulerEventTrueOrderByEventTimestampDesc();

    @Query("""
            select e from LogEventEntity e
            where (:level is null or e.level = :level)
              and (:attentionOnly = false or e.attention = true or e.tracked = true)
              and (:fromDate is null or e.eventTimestamp >= :fromDate)
              and (:toDate is null or e.eventTimestamp <= :toDate)
              and e.schedulerEvent = false
            order by e.eventTimestamp desc
            """)
    List<LogEventEntity> search(LogLevel level, boolean attentionOnly, LocalDateTime fromDate, LocalDateTime toDate);

    @Query("""
            select e from LogEventEntity e
            where e.schedulerEvent = true
              and (:logger is null or lower(e.loggerName) like lower(concat('%', :logger, '%')))
              and (:executor is null or lower(e.executorName) like lower(concat('%', :executor, '%')))
            order by e.eventTimestamp desc
            """)
    List<LogEventEntity> searchSchedulerEvents(String logger, String executor);

    @Modifying
    void deleteByEventTimestampBefore(LocalDateTime before);
}
