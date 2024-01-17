package com.jsrdev.jankenpon.service;

import com.jsrdev.jankenpon.dto.MatchDTO;
import com.jsrdev.jankenpon.exception.NotFoundException;
import com.jsrdev.jankenpon.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class MatchService {
    @Autowired
    UserService userService;

    @Autowired
    GameRepository gameRepository;

    public Optional<MatchDTO> saveMatch(Match match, Long gameId, OAuth2User principal){
        User user = userService.getUser(principal);
        Optional<Game> game = Optional
                .ofNullable(gameRepository.findById(gameId).orElseThrow(() -> new NotFoundException("Game Not Found")));
        return Optional.of(game.map(value -> {
            match.setGame(value);
            match.setUser(user);
            PlayService.setDefaultComputerPlayerChoice(match);
            PlayService.playMatch(match);
            value.getMatches().add(match);
            gameRepository.save(value);
            return MatchDTO.buildFromRecord(match);
        }).orElseThrow());
    }
}
