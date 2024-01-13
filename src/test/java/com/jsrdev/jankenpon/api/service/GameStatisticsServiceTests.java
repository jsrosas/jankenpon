package com.jsrdev.jankenpon.api.service;

import com.jsrdev.jankenpon.TestHelper;
import com.jsrdev.jankenpon.dto.GameStatisticsDTO;
import com.jsrdev.jankenpon.model.*;
import com.jsrdev.jankenpon.service.GameStatisticsService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameStatisticsServiceTests {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private GameStatisticsService gameStatisticsService;

    @Test
    public void GameStatisticsService_BuildFromGame_ReturnGameStatisticsDTO() {
        Match match = TestHelper.generateMatch(Choice.SCISSORS, Choice.ROCK);
        match.setWinnerId(Player.DEFAULT_COMPUTER_ID);
        Game game = match.getGame();
        Player player = Game.findHumanPlayer(game);
        when(matchRepository.countByGameId(game.getId())).thenReturn((long) game.getMatches().size());
        when(matchRepository.countByGameIdAndWinnerId(game.getId(), Player.DEFAULT_COMPUTER_ID)).
                thenReturn((long) game.getMatches().size());
        when(matchRepository.countByGameIdAndWinnerId(game.getId(), player.getId())).
                thenReturn((long) game.getMatches().size());
        GameStatisticsDTO gameStatisticsDTO = gameStatisticsService.buildStatisticsFromGame(match.getGame());
        Assertions.assertThat(gameStatisticsDTO).isNotNull();
        Assertions.assertThat(gameStatisticsDTO.getTotalMatches()).isEqualTo(1);
        Assertions.assertThat(gameStatisticsDTO.getTotalMatchesWonByComputer()).isEqualTo(1);
    }
}
