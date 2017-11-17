package com.sergzubenko.movieland.service.impl.security;

import com.sergzubenko.movieland.service.api.security.AccessToken;
import com.sergzubenko.movieland.service.api.security.AuthManager;
import com.sergzubenko.movieland.service.api.security.LoginService;
import com.sergzubenko.movieland.service.impl.security.exception.TokenNotFoundException;
import com.sergzubenko.movieland.service.impl.security.token.AbstractTokenFactory;
import com.sergzubenko.movieland.service.impl.security.token.TokenCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.security.InvalidParameterException;
import java.security.Principal;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AbstractTokenFactory tokenFactory;

    @Autowired
    private AuthManager authManager;

    @Autowired
    private TokenCache tokenCache;

    @Override
    public AccessToken login(Principal principal) {
        logger.info("User "+principal.getName()+" is logging in");

        authManager.auth(principal);

        AccessToken accessToken = tokenFactory.generateToken(principal);
        tokenCache.registerToken(accessToken);

        return accessToken;
    }

    @Override
    public void logout(String tokenId) {

        AccessToken token = getValidToken(tokenId);
        tokenCache.removeToken(token);
        token.invalidate();
    }

    @Override
    public AccessToken getValidToken(String tokenId) {
        return tokenCache.getToken(tokenId)
                .orElseThrow(()-> new TokenNotFoundException("Token "+tokenId+" invalid"));
    }
}

