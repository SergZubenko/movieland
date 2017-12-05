package com.sergzubenko.movieland.web.logger;

import com.sergzubenko.movieland.service.api.security.UserPrincipal;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

public class MDCLoggerInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        Optional<UserPrincipal> principal = Optional.ofNullable((UserPrincipal) request.getUserPrincipal());
        MDC.put("requestId", UUID.randomUUID().toString());
        MDC.put("nickname", principal.map((p) -> p.getUser().getNickname()).orElse("Unauthorized"));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }
}

