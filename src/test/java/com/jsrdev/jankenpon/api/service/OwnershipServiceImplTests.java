package com.jsrdev.jankenpon.api.service;

import com.jsrdev.jankenpon.model.Game;
import com.jsrdev.jankenpon.model.GameRepository;
import com.jsrdev.jankenpon.model.User;
import com.jsrdev.jankenpon.service.OwnershipServiceImpl;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OwnershipServiceImplTests {
    @Mock
    GameRepository gameRepository;

    @Mock
    UserService userService;

    @InjectMocks
    private OwnershipServiceImpl ownershipServiceImpl;

    @Test
    public void OwnershipService_Check_ReturnTrue(){
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", "foo_user");
        attributes.put("name", "test_name");
        attributes.put("email", "test@test.com");
        OAuth2User principal = new DefaultOAuth2User(
                AuthorityUtils.createAuthorityList("SCOPE_message:read"),
                attributes,
                "sub");
        User user = new User("foo_user", "test_name", "test@test.com");
        Game existingGame = new Game("Test");
        existingGame.setId(1L);
        existingGame.setUser(user);
        when(gameRepository.findById(1L)).thenReturn(Optional.of(existingGame));
        when(userService.getUser(principal)).thenReturn(user);
        boolean result = ownershipServiceImpl.check(existingGame.getId(), principal);
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void OwnershipService_Check_ReturnFalse(){
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", "foo_user_test");
        attributes.put("name", "test_name");
        attributes.put("email", "test@test.com");
        OAuth2User principal = new DefaultOAuth2User(
                AuthorityUtils.createAuthorityList("SCOPE_message:read"),
                attributes,
                "sub");
        User userOwner = new User("foo_user", "test_name", "test@test.com");
        User user = new User("foo_user_test", "test_name", "test@test.com");
        Game existingGame = new Game("Test");
        existingGame.setId(1L);
        existingGame.setUser(userOwner);
        when(gameRepository.findById(1L)).thenReturn(Optional.of(existingGame));
        when(userService.getUser(principal)).thenReturn(user);
        boolean result = ownershipServiceImpl.check(existingGame.getId(), principal);
        Assertions.assertThat(result).isFalse();
    }
}
