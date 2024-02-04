package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.dto.ViewStats;
import ru.practicum.model.Stat;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatRepository extends JpaRepository<Stat, Long> {

    @Query(value = "SELECT app AS app, uri AS uri, COUNT(id) AS hits " +
            "FROM statistics " +
            "WHERE timestamp > :start AND timestamp < :end " +
            "AND uri IN :uris OR :uris IS null " +
            "GROUP BY app, uri " +
            "ORDER BY hits DESC", nativeQuery = true)
    List<ViewStats> search(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value =
            "SELECT s.app AS app, s.uri AS uri, COUNT(*) AS hits " +
            "FROM (" +
                    "SELECT DISTINCT app, uri " +
                    "FROM statistics " +
                    "WHERE timestamp > :start AND timestamp < :end " +
                    "AND uri IN :uris OR :uris IS null) AS s " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY hits DESC", nativeQuery = true)
    List<ViewStats> searchUnique(LocalDateTime start, LocalDateTime end, List<String> uris);

}
