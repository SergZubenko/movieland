package com.sergzubenko.movieland.service.api.security;

import java.security.Principal;

public interface AccessToken {

    boolean isExpired();

    String uid();

    void invalidate();

    Principal getPrincipal();

}
