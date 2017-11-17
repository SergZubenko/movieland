package com.sergzubenko.movieland.service.api.security;

import java.security.Principal;

public interface AuthManager {
    void auth(Principal principal);
}
