package com.jsrdev.jankenpon.api.service;

import com.jsrdev.jankenpon.TestHelper;
import com.jsrdev.jankenpon.model.Choice;
import com.jsrdev.jankenpon.model.Match;
import com.jsrdev.jankenpon.model.Player;
import com.jsrdev.jankenpon.service.PlayService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PlayServiceTests {
    @Test
    public void PlayService_PlayMatch_RockVSPaper_PaperShouldWin(){
        Match match = TestHelper.generateMatch(Choice.ROCK, Choice.PAPER);
        PlayService.playMatch(match);
        Assertions.assertThat(match.getWinnerId()).isNotNull();
        Assertions.assertThat(match.getWinnerId()).isEqualTo(Player.DEFAULT_COMPUTER_ID);
    }

    @Test
    public void PlayService_PlayMatch_RockVSScissors_ScissorsShouldWin(){
        Match match = TestHelper.generateMatch(Choice.SCISSORS, Choice.ROCK);
        PlayService.playMatch(match);
        Assertions.assertThat(match.getWinnerId()).isNotNull();
        Assertions.assertThat(match.getWinnerId()).isEqualTo(Player.DEFAULT_COMPUTER_ID);
    }

    @Test
    public void PlayService_PlayMatch_PaperVSScissors_ScissorsShouldWin(){
        Match match = TestHelper.generateMatch(Choice.PAPER, Choice.SCISSORS);
        PlayService.playMatch(match);
        Assertions.assertThat(match.getWinnerId()).isNotNull();
        Assertions.assertThat(match.getWinnerId()).isEqualTo(Player.DEFAULT_COMPUTER_ID);
    }
}
