package com.jsrdev.jankenpon.service;

import com.jsrdev.jankenpon.model.Choice;
import com.jsrdev.jankenpon.model.Game;
import com.jsrdev.jankenpon.model.Match;
import com.jsrdev.jankenpon.model.Player;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayService {

    private static final Map<Choice, Choice> beats   = new EnumMap<>(Choice.class);

    // init the beats
    static {
        beats.put(Choice.ROCK, Choice.SCISSORS);
        beats.put(Choice.PAPER, Choice.ROCK);
        beats.put(Choice.SCISSORS, Choice.PAPER);
    }

    public static void setDefaultComputerPlayerChoice(Match match){
        var choice = Choice.getRandom();
        match.setPlayer2Choice(choice.ordinal());
    }

    private static Choice beats(final Choice choice) {
        return beats.get(choice);
    }

    public static void playMatch(Match match){
        Choice firsPlayerChoice = Choice.castIntToEnum(match.getPlayer1Choice());
        Choice secondPlayerChoice = Choice.castIntToEnum(match.getPlayer2Choice());
        if(firsPlayerChoice == secondPlayerChoice){
            match.setTie(true);
        }

        if(PlayService.beats(firsPlayerChoice) == secondPlayerChoice){
            Player realPlayer = Game.findHumanPlayer(match.getGame());
            match.setWinnerId(realPlayer.getId());
        }
        if(PlayService.beats(secondPlayerChoice) == firsPlayerChoice){
            match.setWinnerId(Player.DEFAULT_COMPUTER_ID);
        }
    }
}
