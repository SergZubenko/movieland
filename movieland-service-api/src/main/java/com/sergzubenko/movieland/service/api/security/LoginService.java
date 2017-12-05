package com.sergzubenko.movieland.service.api.security;

import java.util.Optional;

public interface LoginService {

    UserPrincipal login(String username, String password);

    AccessToken generateNewToken(UserPrincipal principal);

    void logout(String token);

    Optional<AccessToken> getActiveTokenByUUID(String tokenID);
}
