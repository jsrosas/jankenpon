package com.jsrdev.jankenpon.web;

import com.jsrdev.jankenpon.dto.MatchDTO;
import com.jsrdev.jankenpon.model.Match;
import com.jsrdev.jankenpon.service.GameStatisticsService;
import com.jsrdev.jankenpon.service.MatchService;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/game/{gameId}")
public class MatchController {
    @Autowired
    MatchService matchService;

    @Autowired
    GameStatisticsService gameStatisticsService;

    private final Logger log = LoggerFactory.getLogger(MatchController.class);

    @PostMapping("/match")
    ResponseEntity<?> createMatch(@Valid @RequestBody Match match,
                                  @AuthenticationPrincipal OAuth2User principal,
                                  @PathVariable Long gameId) {
        log.info("Request to create match: {}", match);
        Optional<MatchDTO> result = matchService.saveMatch(match, gameId, principal);
        return result.map(value -> {
            try {
                value.setGameStatistics(gameStatisticsService.buildStatisticsFromGame(match.getGame()));
                return ResponseEntity.created(
                            generateCreatedURI(gameId, value)
                            ).body(result);
            } catch (URISyntaxException e) {
               return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private URI generateCreatedURI(Long gameId, MatchDTO matchDTO) throws URISyntaxException{
        return new URI("/api/game/" + gameId + "/match/" + matchDTO.getId());
    }
}
