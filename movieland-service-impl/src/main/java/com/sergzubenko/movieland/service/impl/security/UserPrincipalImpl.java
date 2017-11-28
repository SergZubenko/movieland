package com.sergzubenko.movieland.service.impl.security;

import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.entity.UserRole;
import com.sergzubenko.movieland.service.api.security.UserPrincipal;

import java.util.Set;

public class UserPrincipalImpl implements UserPrincipal {

    private User user;

    private Set<UserRole> authorities;

    public UserPrincipalImpl(User user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public void setUser(User user) {
        this.user = user;
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

    public Set<UserRole> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAuthorized() {
        return user != null;
    }

    public void setAuthorities(Set<UserRole> authorities) {
        this.authorities = authorities;
    }

}
