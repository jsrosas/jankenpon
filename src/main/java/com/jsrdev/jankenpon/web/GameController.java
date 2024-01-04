package com.jsrdev.jankenpon.web;

import com.jsrdev.jankenpon.dto.GameDTO;
import com.jsrdev.jankenpon.model.Game;
import com.jsrdev.jankenpon.model.GameRepository;
import com.jsrdev.jankenpon.service.GameService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GameController {
    @Autowired
    GameService gameService;

    private final Logger log = LoggerFactory.getLogger(GameController.class);
    private final GameRepository gameRepository;

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping("/games")
    ResponseEntity<Collection<GameDTO>> games(Principal principal) {
        Collection<GameDTO> gamesDTO = gameService.getGames(principal);
        return new ResponseEntity<>(
                gamesDTO, HttpStatus.OK);
    }

    @GetMapping("/game/{id}")
    ResponseEntity<?> getGame(@PathVariable Long id) {
        Optional<GameDTO> game = gameService.getGame(id);
        return game.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/game")
    ResponseEntity<GameDTO> createGame(@Valid @RequestBody Game game,
                                    @AuthenticationPrincipal OAuth2User principal) throws URISyntaxException {
        log.info("Request to create game: {}", game);
        GameDTO result = gameService.saveGame(game, principal);
        return ResponseEntity.created(new URI("/api/game/" + result.getId())).body(result);
    }

    @PutMapping("/game/{id}")
    ResponseEntity<GameDTO> updateGame(@Valid @RequestBody Game game, @AuthenticationPrincipal OAuth2User principal) {
        log.info("Request to update game: {}", game);
        GameDTO result = gameService.saveGame(game, principal);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/game/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable Long id) {
        log.info("Request to delete game: {}", id);
        gameService.deleteGame(id);
        return ResponseEntity.ok().build();
    }
}
