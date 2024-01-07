package com.jsrdev.jankenpon.web;

import com.jsrdev.jankenpon.exception.NotFoundException;
import com.jsrdev.jankenpon.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/game/{gameId}")
public class MatchController {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    UserRepository userRepository;

    private final Logger log = LoggerFactory.getLogger(MatchController.class);

    @PostMapping("/match")
    ResponseEntity<?> createMatch(@Valid @RequestBody Match match,
                                  @AuthenticationPrincipal OAuth2User principal,
                                  @PathVariable Long gameId) {
        log.info("Request to create match: {}", match);
        Map<String, Object> details = principal.getAttributes();
        String userId = details.get("sub").toString();
        Optional<User> user = userRepository.findById(userId);
        Optional<Game> game = Optional
                .ofNullable(gameRepository.findById(gameId).orElseThrow(() -> new NotFoundException("Game Not Found")));
        return game.map(value -> {
            match.setGame(value);
            match.setUser(user.orElse(new User(userId,
                    details.get("name").toString(), details.get("email").toString())));
            value.getMatches().add(match);
            gameRepository.save(value);
            try {
                return ResponseEntity.created(
                        new URI(
                            "/api/game/" + value.getId() + "/match/" + match.getId())
                        ).body(match);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
