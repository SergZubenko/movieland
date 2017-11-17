package com.sergzubenko.movieland.service.impl.security;

import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.entity.UserRole;
import com.sergzubenko.movieland.service.api.security.ILoginPasswordPrincipal;

import java.util.Set;

public class UserPrincipal implements ILoginPasswordPrincipal {

    private User user;

    private String username;
    private String password;

    private Set<UserRole> authorities;

    public UserPrincipal(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
        this.username = user.getEmail();
        this.password = user.getPassword();
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPrincipal that = (UserPrincipal) o;

        return username.equals(that.username);
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
