package com.jsrdev.jankenpon.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameStatisticsDTO {
    private Long totalMatches;
    private Long totalMatchesWonByComputer;
    private Long totalMatchesWonByPlayer;
}
