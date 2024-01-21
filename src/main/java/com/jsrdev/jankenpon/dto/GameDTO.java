package com.jsrdev.jankenpon.dto;

import com.jsrdev.jankenpon.model.Game;
import com.jsrdev.jankenpon.model.Match;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class GameDTO {
    private Long id;
    private String name;
    private Set<PlayerDTO> players;
    private Set<Match> matches;

    public static GameDTO buildFromRecord(Game game){
        return GameDTO.builder()
                .id(game.getId())
                .name(game.getName())
                .players(GameDTO.buildPlayer(game))
                .matches(game.getMatches())
                .build();
    }

    public static Set<PlayerDTO> buildPlayer(Game game){
        Set<PlayerDTO> playerDTOS = new HashSet<>();
        game.getPlayers().forEach( player -> {
            playerDTOS.add(PlayerDTO.buildFromRecord(player));
        });
        return playerDTOS;
    }
}
