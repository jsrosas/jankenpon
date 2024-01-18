package com.jsrdev.jankenpon.api.service;

import com.jsrdev.jankenpon.model.User;
import com.jsrdev.jankenpon.model.UserRepository;
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
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void UserService_GetUser_ReturnUser(){
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", "foo_user");
        attributes.put("name", "test_name");
        attributes.put("email", "test@test.com");
        OAuth2User principal = new DefaultOAuth2User(
                AuthorityUtils.createAuthorityList("SCOPE_message:read"),
                attributes,
                "sub");
        User user = new User("foo_user", "test_name", "test@test.com");
        when(userRepository.findById("foo_user")).thenReturn(Optional.of(user));
        User testUser = userService.getUser(principal);
        Assertions.assertThat(testUser).isNotNull();
        Assertions.assertThat(testUser.getName()).isEqualTo("test_name");
        Assertions.assertThat(testUser.getEmail()).isEqualTo("test@test.com");
    }
}
