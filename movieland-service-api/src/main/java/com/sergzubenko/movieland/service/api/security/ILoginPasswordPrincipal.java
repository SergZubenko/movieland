package com.sergzubenko.movieland.service.api.security;


import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.entity.UserRole;

import java.security.Principal;
import java.util.Set;

public interface ILoginPasswordPrincipal extends Principal {
    String getPassword();

    void setUser(User user);

    User getUser();

    void setAuthorities(Set<UserRole> authorities);

    Set<UserRole> getAuthorities();

    boolean isAuthorized();

}
