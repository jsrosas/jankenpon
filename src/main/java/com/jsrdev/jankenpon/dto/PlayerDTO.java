package com.jsrdev.jankenpon.dto;

import com.jsrdev.jankenpon.model.Player;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerDTO {
    private Long id;
    private String name;
    private boolean isDefaultComputer;

    public static PlayerDTO buildFromRecord(Player player){
        return PlayerDTO.builder()
                .id(player.getId())
                .name(player.getName())
                .isDefaultComputer(player.isDefaultComputer())
                .build();
    }
}
