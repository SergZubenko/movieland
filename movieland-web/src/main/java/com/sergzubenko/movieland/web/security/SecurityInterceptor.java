package com.sergzubenko.movieland.web.security;

import com.sergzubenko.movieland.entity.UserRole;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

import static com.sergzubenko.movieland.web.security.annotation.HasRoleHandler.getRequiredRoles;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

public class SecurityInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        Set<UserRole> requiredRoles = getRequiredRoles(handler);

        if (requiredRoles.isEmpty()) {
            return true;
        }
        for (UserRole requiredRole : requiredRoles) {
            if (request.isUserInRole(requiredRole.name())){
                return true;
            }
        }
        response.sendError(SC_FORBIDDEN);
        return false;
    }
}