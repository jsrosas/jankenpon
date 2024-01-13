package com.jsrdev.jankenpon.api.service;

import com.jsrdev.jankenpon.dto.GameDTO;
import com.jsrdev.jankenpon.model.*;
import com.jsrdev.jankenpon.service.GameService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.security.Principal;
import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceTests {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private GameService gameService;


    @Test
    public void GameService_GetGames_ReturnsGameDTOS(){
        Principal principal = () -> "Test";
        List<Game> games = new ArrayList<>();
        Player player = new Player("PlayerTest");
        Set<Player> players = new HashSet<>();
        players.add(player);
        Game game1 = new Game();
        game1.setId(1L);
        game1.setName("Test1");
        game1.setPlayers(players);
        Game game2 = new Game();
        game2.setId(2L);
        game2.setName("Test2");
        game2.setPlayers(players);
        games.add(game1);
        games.add(game2);
        when(gameRepository.findAllByUserId(principal.getName())).thenReturn(games);
        Collection<GameDTO> gameDTOS = gameService.getGames(principal);
        Assertions.assertThat(gameDTOS).isNotNull();
        Assertions.assertThat(gameDTOS.size()).isEqualTo(2);
    }

    @Test
    public void GameService_SaveGame_ReturnGameDTO(){
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
        computerDefaultPlayer.setDefaultComputer(true);
        Player player = new Player("livePlayer");
        Set<Player> players = new HashSet<>();
        players.add(player);
        when(userRepository.findById("foo_user")).thenReturn(Optional.of(user));
        Game game = new Game("Test");
        game.setPlayers(players);
        game.setId(1L);
        when(gameRepository.save(game)).thenReturn(game);
        when(playerRepository.findById(Player.DEFAULT_COMPUTER_ID)).thenReturn(Optional.of(computerDefaultPlayer));
        GameDTO gameDTO = gameService.saveGame(game, principal);
        Assertions.assertThat(gameDTO).isNotNull();
        Assertions.assertThat(gameDTO.getId()).isEqualTo(game.getId());
        Assertions.assertThat(gameDTO.getName()).isEqualTo(game.getName());
        Assertions.assertThat(gameDTO.getPlayers().size()).isEqualTo(2);
    }

    @Test
    public void GameService_UpdateGame_ReturnGameDTO(){
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", "foo_user");
        attributes.put("name", "test_name");
        attributes.put("email", "test@test.com");
        OAuth2User principal = new DefaultOAuth2User(
                AuthorityUtils.createAuthorityList("SCOPE_message:read"),
                attributes,
                "sub");
        Player player = new Player("livePlayer");
        player.setId(1L);
        Set<Player> players = new HashSet<>();
        players.add(player);
        Game existingGame = new Game("Test");
        existingGame.setPlayers(players);
        existingGame.setId(1L);
        when(gameRepository.findById(1L)).thenReturn(Optional.of(existingGame));
        Game updateGame = new Game("TestUpdate");
        updateGame.setId(1L);
        updateGame.setPlayers(players);
        when(gameRepository.save(updateGame)).thenReturn(updateGame);
        GameDTO updatedGame = gameService.updateGame(updateGame, principal).get();
        Assertions.assertThat(updatedGame).isNotNull();
        Assertions.assertThat(updatedGame.getName()).isEqualTo("TestUpdate");
    }
}
