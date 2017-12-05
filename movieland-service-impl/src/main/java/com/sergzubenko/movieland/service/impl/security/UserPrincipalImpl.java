package com.sergzubenko.movieland.service.impl.security;

import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.entity.UserRole;
import com.sergzubenko.movieland.service.api.security.UserPrincipal;

import java.util.Set;

public class UserPrincipalImpl implements UserPrincipal {

    private final User user;

    private final Set<UserRole> roles;

    public UserPrincipalImpl(User user, Set<UserRole> roles) {
        this.user = user;
        this.roles = roles;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    @Override
    public String getName() {
        return user.getEmail();
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPrincipalImpl that = (UserPrincipalImpl) o;

        return user.getEmail().equals(that.user.getEmail());
    }


}
