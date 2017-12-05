package com.sergzubenko.movieland.web.security;

import com.sergzubenko.movieland.entity.UserRole;
import com.sergzubenko.movieland.service.api.security.AccessToken;
import com.sergzubenko.movieland.service.api.security.UserPrincipal;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Set;

class UserPrincipalRequestWrapper extends HttpServletRequestWrapper {
    private UserPrincipal principal;

    UserPrincipalRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    UserPrincipalRequestWrapper(HttpServletRequest request, AccessToken token) {
        super(request);
         this.principal = token.getPrincipal();
    }

    @Override
    @Nullable
    public UserPrincipal getUserPrincipal() {
        return principal;
    }

    @Override
    public boolean isUserInRole(String role) {
        if (principal == null) {
            return false;
        }
        Set<UserRole> roles = principal.getRoles();
        UserRole requestedRole = UserRole.getByName(role);
        return roles.contains(requestedRole);
    }
}
