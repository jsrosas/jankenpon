package com.jsrdev.jankenpon.service;

import com.jsrdev.jankenpon.dto.GameStatisticsDTO;
import com.jsrdev.jankenpon.model.Game;
import com.jsrdev.jankenpon.model.MatchRepository;
import com.jsrdev.jankenpon.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameStatisticsService {
    @Autowired
    MatchRepository matchRepository;

    public GameStatisticsDTO buildStatisticsFromGame(Game game){
        long totalMatches = matchRepository.countByGameId(game.getId());
        long wonByComputer = matchRepository.countByGameIdAndWinnerId(game.getId(), Player.DEFAULT_COMPUTER_ID);
        long ties = matchRepository.countByGameIdAndIsTie(game.getId(), true);
        Player player = Game.findHumanPlayer(game);
        long wonByPlayer = matchRepository.countByGameIdAndWinnerId(game.getId(),player.getId());
        return GameStatisticsDTO.builder()
                .totalMatches(totalMatches)
                .totalMatchesWonByComputer(wonByComputer)
                .totalMatchesWonByPlayer(wonByPlayer)
                .totalTies(ties)
                .build();
    }
}
