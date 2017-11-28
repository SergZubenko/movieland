package com.sergzubenko.movieland.service.api.security;

public interface LoginService {

    AccessToken login(String username, String password);

    void logout(String token);

    AccessToken getValidToken(String tokenID);
}
