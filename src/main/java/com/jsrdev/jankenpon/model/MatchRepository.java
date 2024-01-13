package com.jsrdev.jankenpon.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
    long countByGameId(long gameId);

    long countByGameIdAndWinnerId(long gameId, long winnerId);

}
