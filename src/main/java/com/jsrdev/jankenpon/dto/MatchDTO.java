package com.jsrdev.jankenpon.dto;

import com.jsrdev.jankenpon.model.Match;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchDTO {
    private Long id;
    private int player1Choice;
    private int player2Choice;
    private Long winnerId;
    private boolean isTie;
    private GameStatisticsDTO gameStatistics;

    public static MatchDTO buildFromRecord(Match match) {
        return MatchDTO.builder()
                .id(match.getId())
                .player1Choice(match.getPlayer1Choice())
                .player2Choice(match.getPlayer2Choice())
                .isTie(match.isTie())
                .winnerId(match.getWinnerId())
                .build();
    }
}
