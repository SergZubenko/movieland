package com.sergzubenko.movieland.web.security;

import com.sergzubenko.movieland.entity.UserRole;
import com.sergzubenko.movieland.service.impl.security.UserPrincipal;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Set;

import static com.sergzubenko.movieland.web.security.utils.UserRoleHandler.getRequiredRoles;

public class SecurityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        UserPrincipal principal = (UserPrincipal) request.getUserPrincipal();
        Set<UserRole> requiredRoles = getRequiredRoles(handler.getClass());



        if (requiredRoles.size() == 0){
            return true;
        }

//        HandlerMethod method = (HandlerMethod) handler;

        Set<UserRole> grantedRoles = principal.getAuthorities();
        for (UserRole requiredRole : requiredRoles) {
            if (grantedRoles.contains(requiredRole))
                return true;
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("Post-handle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("After completion handle");
    }
}