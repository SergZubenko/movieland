package com.sergzubenko.movieland.web.security;

import com.sergzubenko.movieland.entity.UserRole;
import com.sergzubenko.movieland.service.impl.security.UserPrincipalImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.sergzubenko.movieland.web.security.annotation.HasRoleHandler.getRequiredRoles;

public class SecurityInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        Optional<UserPrincipalImpl> principal = Optional.ofNullable((UserPrincipalImpl) request.getUserPrincipal());

        MDC.put("requestId", UUID.randomUUID().toString());
        MDC.put("nickname", principal.map((p) -> p.getUser().getNickname()).orElse("Unauthorized"));

        logger.info("Pre handle intercept");

        Set<UserRole> requiredRoles = getRequiredRoles(handler);

        return (requiredRoles.size() == 0 || (principal.isPresent() && !Collections.disjoint(requiredRoles, principal.get().getAuthorities())))
                && super.preHandle(request, response, handler);

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        MDC.clear();
        super.afterCompletion(request, response, handler, ex);
    }
}