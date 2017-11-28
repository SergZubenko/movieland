package com.sergzubenko.movieland.service.api.security;


public interface AccessToken {

    boolean isExpired();

    String uid();

    void invalidate();

    UserPrincipal getPrincipal();

}
