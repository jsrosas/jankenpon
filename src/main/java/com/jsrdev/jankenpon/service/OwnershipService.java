package com.jsrdev.jankenpon.service;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OwnershipService {
    boolean check(Long gameId, OAuth2User principal);
}
