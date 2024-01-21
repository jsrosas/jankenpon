package com.jsrdev.jankenpon.service;

import com.jsrdev.jankenpon.dto.MatchDTO;
import com.jsrdev.jankenpon.model.Match;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

public interface MatchService {
    Optional<MatchDTO> saveMatch(Match match, Long gameId, OAuth2User principal);
}
