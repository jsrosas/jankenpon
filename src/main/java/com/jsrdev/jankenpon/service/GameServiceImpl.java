package com.jsrdev.jankenpon.service;

import com.jsrdev.jankenpon.dto.GameDTO;
import com.jsrdev.jankenpon.exception.NotFoundException;
import com.jsrdev.jankenpon.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserService userService;

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
        User user = userService.getUser(principal);
        game.setUser(user);
        Player computerDefaultPlayer = playerRepository.findById(Player.DEFAULT_COMPUTER_ID).orElseThrow();
        game.getPlayers().add(computerDefaultPlayer);
        Game result = gameRepository.save(game);
        return GameDTO.buildFromRecord(result);
    }

    public Optional<GameDTO> updateGame(Game game, OAuth2User principal) {
        Optional<Game> record = Optional
                .ofNullable(gameRepository.findById(game.getId()).orElseThrow(() -> new NotFoundException("Game Not Found")));
        return Optional.of(record.map(gameRecord -> {
            gameRecord.setName(game.getName());
            Player player = Game.findHumanPlayer(game);
            Player existingPlayer = Game.findHumanPlayer(gameRecord);
            existingPlayer.setName(player.getName());
            gameRepository.save(gameRecord);
            return GameDTO.buildFromRecord(gameRecord);
        }).orElseThrow());
    }
}
