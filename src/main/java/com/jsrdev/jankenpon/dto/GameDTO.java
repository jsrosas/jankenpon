package com.jsrdev.jankenpon.dto;

import com.jsrdev.jankenpon.model.Game;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameDTO {
    private Long id;
    private String name;
    private PlayerDTO player1;
    private PlayerDTO player2;

    public static GameDTO buildFromRecord(Game game){
        return GameDTO.builder()
                .id(game.getId())
                .name(game.getName())
                .player1(
                        PlayerDTO.buildFromRecord(game.getPlayer1())
                )
                .player2(
                        PlayerDTO.buildFromRecord(game.getPlayer2())
                )
                .build();
    }
}
