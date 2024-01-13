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
    UserRepository userRepository;

    @Autowired
    GameRepository gameRepository;

    public Optional<MatchDTO> saveMatch(Match match, Long gameId, OAuth2User principal){
        Map<String, Object> details = principal.getAttributes();
        String userId = details.get("sub").toString();
        Optional<User> user = userRepository.findById(userId);
        Optional<Game> game = Optional
                .ofNullable(gameRepository.findById(gameId).orElseThrow(() -> new NotFoundException("Game Not Found")));
        return Optional.of(game.map(value -> {
            match.setGame(value);
            match.setUser(user.orElse(new User(userId,
                    details.get("name").toString(), details.get("email").toString())));
            PlayService.setDefaultComputerPlayerChoice(match);
            PlayService.playMatch(match);
            value.getMatches().add(match);

            gameRepository.save(value);
            return MatchDTO.buildFromRecord(match);
        }).orElseThrow());
    }
}
