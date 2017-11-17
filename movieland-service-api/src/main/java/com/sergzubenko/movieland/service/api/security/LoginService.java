package com.sergzubenko.movieland.service.api.security;

import java.security.Principal;

public interface LoginService {

    AccessToken login(Principal principal);

    void logout(String token);

    AccessToken getValidToken(String tokenID);
}
