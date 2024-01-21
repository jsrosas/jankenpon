package com.jsrdev.jankenpon;

import com.jsrdev.jankenpon.model.Choice;
import com.jsrdev.jankenpon.model.Game;
import com.jsrdev.jankenpon.model.Match;
import com.jsrdev.jankenpon.model.Player;

import java.util.HashSet;
import java.util.Set;

public class TestHelper {
    public static Game generateGame(){
        Game game = new Game("Test");
        game.setId(1L);
        Player computerDefaultPlayer = new Player("test");
        computerDefaultPlayer.setId(Player.DEFAULT_COMPUTER_ID);
        computerDefaultPlayer.setDefaultComputer(true);
        Player realPlayer = new Player("test");
        realPlayer.setId(2L);
        realPlayer.setDefaultComputer(false);
        Set<Player> players = new HashSet<>();
        players.add(computerDefaultPlayer);
        players.add(realPlayer);
        game.setPlayers(players);
        return game;
    }

    public static Match generateMatch(Choice computerDefaultPlayerChoice, Choice realPlayerChoice){
        Game game = TestHelper.generateGame();
        Match match = new Match();
        match.setPlayer1Choice(computerDefaultPlayerChoice.ordinal());
        match.setPlayer2Choice(realPlayerChoice.ordinal());
        Set<Match> matches = new HashSet<>();
        matches.add(match);
        game.setMatches(matches);
        match.setGame(game);
        return match;
    }
}
