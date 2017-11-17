package com.sergzubenko.movieland.service.impl.security.token;

import com.sergzubenko.movieland.service.api.security.AccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
public class UUIDBasedTokenFactory extends AbstractTokenFactory {
    @Value("${token.expirationTimeSeconds}")
    Integer tokenLifeTime;

    @Override
    public AccessToken generateToken(Principal principal) {
        return new UUIDBasedToken(principal, LocalDateTime.now().plusSeconds(tokenLifeTime));
    }
}
