package com.jsrdev.jankenpon.service;

import com.jsrdev.jankenpon.dto.GameDTO;
import com.jsrdev.jankenpon.model.Game;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

public interface GameService {
    Collection<GameDTO> getGames(Principal principal);
    Optional<GameDTO> getGame(Long id);
    GameDTO saveGame(Game game, OAuth2User principal);
    Optional<GameDTO> updateGame(Game game, OAuth2User principal);
}
