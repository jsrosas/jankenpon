package com.jsrdev.jankenpon.service;

import com.jsrdev.jankenpon.dto.GameDTO;
import com.jsrdev.jankenpon.dto.PlayerDTO;
import com.jsrdev.jankenpon.model.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Service
public class GameService {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PlayerRepository playerRepository;

    public Collection<GameDTO> getGames(Principal principal) {
        Iterable<Game> games = gameRepository.findAllByUserId(principal.getName());
        Collection<GameDTO> gamesDTO = new ArrayList<>();
        games.forEach(game -> {
            gamesDTO.add(
                GameDTO.buildFromRecord(game)
            );
        });
        return gamesDTO;
    }

    public Optional<GameDTO> getGame(Long id){
        return gameRepository.findById(id).map(GameDTO::buildFromRecord);
    }

    public GameDTO saveGame(Game game, OAuth2User principal){
        Map<String, Object> details = principal.getAttributes();
        String userId = details.get("sub").toString();
        Optional<User> user = userRepository.findById(userId);
        game.setUser(user.orElse(new User(userId,
                details.get("name").toString(), details.get("email").toString())));
        Player computerDefaultPlayer = playerRepository.findById(1L).orElseThrow();
        game.getPlayers().add(computerDefaultPlayer);
        Game result = gameRepository.save(game);
        return GameDTO.buildFromRecord(result);
    }

    public void deleteGame(Long id){
        gameRepository.deleteById(id);
    }
}
