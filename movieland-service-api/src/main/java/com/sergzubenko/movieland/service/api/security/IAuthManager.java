package com.sergzubenko.movieland.service.api.security;

import java.security.Principal;

public interface IAuthManager {
    void auth(Principal principal);
}
