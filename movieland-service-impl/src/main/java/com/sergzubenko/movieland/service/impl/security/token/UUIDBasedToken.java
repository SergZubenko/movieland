package com.sergzubenko.movieland.service.impl.security.token;

import com.sergzubenko.movieland.service.api.security.AccessToken;
import com.sergzubenko.movieland.service.api.security.UserPrincipal;

import java.time.LocalDateTime;
import java.util.UUID;

public class UUIDBasedToken implements AccessToken {

    private final String uid;

    private volatile LocalDateTime expirationTime;

    private final UserPrincipal principal;


    public UUIDBasedToken(UserPrincipal principal, LocalDateTime expirationTime) {
        uid = UUID.randomUUID().toString();
        this.principal = principal;
        this.expirationTime = expirationTime;
    }

    @Override
    public boolean isExpired() {
        return expirationTime.isBefore(LocalDateTime.now());
    }

    @Override
    public String uid() {
        return uid;
    }

    @Override
    public void invalidate() {
        this.expirationTime = LocalDateTime.now();
    }

    @Override
    public UserPrincipal getPrincipal() {
        return principal;
    }

}
