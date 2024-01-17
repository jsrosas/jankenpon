package com.jsrdev.jankenpon.service;

import com.jsrdev.jankenpon.model.User;
import com.jsrdev.jankenpon.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User getUser(OAuth2User principal){
        String userId = getIdFromOAuth2User(principal);
        Map<String, Object> details = getDetailsFromOAuth2User(principal);
        return userRepository.findById(userId)
                .orElse(
                        new User(userId,
                                details.get("name").toString(),
                                details.get("email").toString()
                        )
                );
    }

    private String getIdFromOAuth2User(OAuth2User principal){
        Map<String, Object> details = getDetailsFromOAuth2User(principal);
        return details.get("sub").toString();
    }

    private Map<String, Object> getDetailsFromOAuth2User(OAuth2User principal){
        return principal.getAttributes();
    }
}
