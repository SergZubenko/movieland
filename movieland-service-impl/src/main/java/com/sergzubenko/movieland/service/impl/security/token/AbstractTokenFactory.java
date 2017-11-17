package com.sergzubenko.movieland.service.impl.security.token;

import com.sergzubenko.movieland.service.api.security.AccessToken;

import java.security.Principal;

public abstract class AbstractTokenFactory {
    public abstract AccessToken generateToken(Principal principal);
}
