package com.sergzubenko.movieland.service.api.security;


import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.entity.UserRole;

import java.security.Principal;
import java.util.Set;

public interface UserPrincipal extends Principal {

    User getUser();

    Set<UserRole> getRoles();
}
