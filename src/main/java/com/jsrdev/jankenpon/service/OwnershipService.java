package com.jsrdev.jankenpon.service;

import com.jsrdev.jankenpon.model.Game;
import com.jsrdev.jankenpon.model.GameRepository;
import com.jsrdev.jankenpon.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class OwnershipService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserService userService;

    public boolean check(Long gameId, OAuth2User principal) {
        Optional<Game> game = gameRepository.findById(gameId);
        return game.map(gameValue -> {
            User user = userService.getUser(principal);
            return Objects.equals(gameValue.getUser().getId(), user.getId());
        }).orElse(false);
    }
}
