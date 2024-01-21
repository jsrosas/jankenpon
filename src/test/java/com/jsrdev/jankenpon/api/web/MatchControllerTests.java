package com.jsrdev.jankenpon.api.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsrdev.jankenpon.dto.MatchDTO;
import com.jsrdev.jankenpon.model.Match;
import com.jsrdev.jankenpon.service.GameStatisticsService;
import com.jsrdev.jankenpon.service.MatchService;
import com.jsrdev.jankenpon.service.OwnershipService;
import com.jsrdev.jankenpon.web.MatchController;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.opaqueToken;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = MatchController.class)
@AutoConfigureMockMvc
@ActiveProfiles("mock")
public class MatchControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean(name = "matchService")
    private MatchService matchService;

    @MockBean(name = "ownershipService")
    private OwnershipService ownershipService;

    @MockBean(name = "gameStatisticsService")
    private GameStatisticsService gameStatisticsService;

    @Autowired
    private ObjectMapper objectMapper;
    private Match match;
    private MatchDTO matchDTO;

    OAuth2User oauth2User = new DefaultOAuth2User(
            AuthorityUtils.createAuthorityList("SCOPE_message:read"),
            Collections.singletonMap("user_name", "foo_user"),
            "user_name");

    @Test
    public void MatchController_CreateMatch_ReturnMatchDTO() throws Exception {
        long gameId = 1L;
        match = new Match();
        match.setId(1L);
        matchDTO = MatchDTO.buildFromRecord(match);
        when(ownershipService.check(gameId, oauth2User)).thenReturn(true);
        when(matchService.saveMatch(any(Match.class), any(Long.class), any(OAuth2User.class)))
                .thenReturn(Optional.ofNullable(matchDTO));

        ResultActions response = mockMvc.perform(post("/api/game/1/match")
                .with(oauth2Login().oauth2User(oauth2User))
                .with(opaqueToken().principal(oauth2User))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(matchDTO)));
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(Math.toIntExact(matchDTO.getId()))));
    }
}
