package com.jsrdev.jankenpon.api.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsrdev.jankenpon.dto.GameDTO;
import com.jsrdev.jankenpon.model.Game;
import com.jsrdev.jankenpon.model.Player;
import com.jsrdev.jankenpon.service.GameService;
import com.jsrdev.jankenpon.service.OwnershipService;
import com.jsrdev.jankenpon.web.GameController;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.opaqueToken;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = GameController.class)
@AutoConfigureMockMvc
public class GameControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean(name = "ownershipService")
    private OwnershipService ownershipService;

    @MockBean(name = "gameService")
    private GameService gameService;

    @Autowired
    private ObjectMapper objectMapper;
    private Game game;
    private GameDTO gameDTO;
    private Collection<GameDTO> gamesDTO;
    private Player player;


    OAuth2User oauth2User = new DefaultOAuth2User(
            AuthorityUtils.createAuthorityList("SCOPE_message:read"),
            Collections.singletonMap("user_name", "foo_user"),
            "user_name");

    @BeforeEach
    public void init() {
        game = new Game("Test");
        game.setId(1L);
        player = new Player("Test");
        game.setPlayers(Set.of(player));
        gameDTO = GameDTO.buildFromRecord(game);
    }

    @After
    public void tearDown() {
        Mockito.reset(gameService);
    }

    @Test
    public void GameController_GetGame_ReturnGameDTO() throws Exception {
        long gameId = 1L;
        when(ownershipService.check(gameId, oauth2User)).thenReturn(true);
        when(gameService.getGame(gameId)).thenReturn(Optional.ofNullable(gameDTO));

        ResultActions response = mockMvc.perform(get("/api/game/1")
                .with(oauth2Login().oauth2User(oauth2User))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(gameDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.id", CoreMatchers.is(Math.toIntExact(gameDTO.getId())))
                );
        Mockito.verify(gameService, times(1)).getGame(gameId);
    }

    @Test
    public void GameController_GetGame_ReturnCollectionOfGamesDTO() throws Exception {
        gamesDTO = Set.of(gameDTO);
        when(gameService.getGames(any(Principal.class))).thenReturn(gamesDTO);

        ResultActions response = mockMvc.perform(get("/api/games")
                .with(oauth2Login().oauth2User(oauth2User))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gamesDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is(gameDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$[0].id", CoreMatchers.is(Math.toIntExact(gameDTO.getId())))
                );
        Mockito.verify(gameService, times(1)).getGames(any(Principal.class));
    }

    @Test
    public void GameController_CreateGame_ReturnCreated() throws Exception {
        Game newGame = new Game("new game");
        newGame.setPlayers(Set.of(player));
        GameDTO newGameDTO = GameDTO.buildFromRecord(newGame);
        newGameDTO.setId(1L);
        when(gameService.saveGame(any(Game.class), any(OAuth2User.class))).thenReturn(newGameDTO);

        ResultActions response = mockMvc.perform(post("/api/game")
                .with(opaqueToken().principal(oauth2User))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newGameDTO)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(newGameDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.id", CoreMatchers.is(Math.toIntExact(Math.toIntExact(newGameDTO.getId()))))
                );
        Mockito.verify(gameService, times(1)).saveGame(game, oauth2User);
    }

    @Test
    public void GameController_UpdateGame_ReturnCreated() throws Exception {
        Game updateGame = new Game("updated game");
        updateGame.setPlayers(Set.of(player));
        GameDTO updateGameDTO = GameDTO.buildFromRecord(updateGame);
        updateGameDTO.setId(1L);
        when(ownershipService.check(1L, oauth2User)).thenReturn(true);
        when(gameService.updateGame(any(Game.class), any(OAuth2User.class))).thenReturn(Optional.of(updateGameDTO));

        ResultActions response = mockMvc.perform(put("/api/game/1")
                .with(opaqueToken().principal(oauth2User))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateGameDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(updateGameDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.id", CoreMatchers.is(Math.toIntExact(Math.toIntExact(updateGameDTO.getId()))))
                );
        Mockito.verify(gameService, times(1)).updateGame(game, oauth2User);
    }

}
