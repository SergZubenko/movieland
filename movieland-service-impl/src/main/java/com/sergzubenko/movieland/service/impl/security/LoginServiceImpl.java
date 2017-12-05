package com.sergzubenko.movieland.service.impl.security;

import com.sergzubenko.movieland.service.api.security.AccessToken;
import com.sergzubenko.movieland.service.api.security.AuthManager;
import com.sergzubenko.movieland.service.api.security.LoginService;
import com.sergzubenko.movieland.service.api.security.UserPrincipal;
import com.sergzubenko.movieland.service.impl.security.token.TokenCache;
import com.sergzubenko.movieland.service.impl.security.token.UUIDBasedToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Value("${token.expirationTimeSeconds}")
    Integer tokenLifeTime;

    @Autowired
    private AuthManager authManager;

    @Autowired
    private TokenCache tokenCache;

    @Override
    public UserPrincipal login(String username, String password) {
        log.info("User " + username + " is logging in");
        return authManager.auth(username, password);
    }

    @Override
    public AccessToken generateNewToken(UserPrincipal principal) {
        AccessToken accessToken = new UUIDBasedToken(principal, LocalDateTime.now().plusSeconds(tokenLifeTime));
        tokenCache.registerToken(accessToken);
        return accessToken;
    }

    @Override
    public void logout(String tokenId) {
        tokenCache.removeToken(tokenId);
    }

    @Override
    public Optional<AccessToken> getActiveTokenByUUID(String tokenId) {
        return tokenCache.getToken(tokenId).filter((token) -> !token.isExpired());
    }
}

