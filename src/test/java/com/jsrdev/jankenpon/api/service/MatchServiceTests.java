package com.jsrdev.jankenpon.api.service;

import com.jsrdev.jankenpon.dto.MatchDTO;
import com.jsrdev.jankenpon.model.*;
import com.jsrdev.jankenpon.service.MatchService;
import com.jsrdev.jankenpon.service.PlayService;
import com.jsrdev.jankenpon.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MatchServiceTests {
    @Mock
    private GameRepository gameRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private MatchService matchService;

    @Test
    public void MatchService_SaveGame_ReturnGameDTO(){
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", "foo_user");
        attributes.put("name", "test_name");
        attributes.put("email", "test@test.com");
        OAuth2User principal = new DefaultOAuth2User(
                AuthorityUtils.createAuthorityList("SCOPE_message:read"),
                attributes,
                "sub");
        User user = new User("foo_user", "user", "test@test.com");
        Player computerDefaultPlayer = new Player("test");
        computerDefaultPlayer.setId(Player.DEFAULT_COMPUTER_ID);
        Player player = new Player("livePlayer");
        player.setId(2L);
        Set<Player> players = new HashSet<>();
        players.add(player);
        players.add(computerDefaultPlayer);
        Game existingGame = new Game("Test");
        Set<Match> matches = new HashSet<>();
        existingGame.setPlayers(players);
        existingGame.setId(1L);
        existingGame.setMatches(matches);
        when(gameRepository.findById(1L)).thenReturn(Optional.of(existingGame));
        when(userService.getUser(principal)).thenReturn(user);
        Match match = new Match();
        match.setPlayer1Choice(Choice.SCISSORS.ordinal());
        MatchDTO matchDTO = matchService.saveMatch(match, existingGame.getId(), principal).get();
        Assertions.assertThat(matchDTO).isNotNull();
        Assertions.assertThat(matchDTO.getPlayer1Choice()).isEqualTo(2);
    }
}
