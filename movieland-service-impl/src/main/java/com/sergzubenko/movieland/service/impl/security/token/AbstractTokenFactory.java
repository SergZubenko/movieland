package com.sergzubenko.movieland.service.impl.security.token;

import com.sergzubenko.movieland.service.api.security.AccessToken;
import com.sergzubenko.movieland.service.api.security.UserPrincipal;

public abstract class AbstractTokenFactory {
    public abstract AccessToken generateToken(UserPrincipal principal);
}
