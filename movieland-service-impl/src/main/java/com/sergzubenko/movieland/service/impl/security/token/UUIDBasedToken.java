package com.sergzubenko.movieland.service.impl.security.token;

import com.sergzubenko.movieland.service.api.security.AccessToken;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.UUID;

public class UUIDBasedToken implements AccessToken {

    private final String uid;

    private final LocalDateTime logonTime;

    private volatile LocalDateTime expirationTime;

    private final Principal principal;


    public UUIDBasedToken(Principal principal, LocalDateTime expirationTime) {
        uid = UUID.randomUUID().toString();
        this.principal = principal;
        this.logonTime = LocalDateTime.now();
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
    public Principal getPrincipal() {
        return principal;
    }

}
