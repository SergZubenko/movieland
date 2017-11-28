package com.sergzubenko.movieland.service.api.security;

public interface AuthManager {
    UserPrincipal auth(String username, String password);
}
