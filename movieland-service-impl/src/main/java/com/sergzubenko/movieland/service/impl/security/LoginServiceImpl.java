package com.sergzubenko.movieland.service.impl.security;

import com.sergzubenko.movieland.service.api.security.AccessToken;
import com.sergzubenko.movieland.service.api.security.IAuthManager;
import com.sergzubenko.movieland.service.api.security.LoginService;
import com.sergzubenko.movieland.service.impl.security.token.AbstractTokenFactory;
import com.sergzubenko.movieland.service.impl.security.token.TokenCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.security.Principal;

@Service
public class LoginServiceImpl implements LoginService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AbstractTokenFactory tokenFactory;

    @Autowired
    private IAuthManager authManager;

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
        AccessToken token = tokenCache.getToken(tokenId);
        if (token == null){
            throw new InvalidParameterException("Token "+tokenId+" invalid");
        }else if(!token.isExpired()){
            token.invalidate();
        }
    }

}

