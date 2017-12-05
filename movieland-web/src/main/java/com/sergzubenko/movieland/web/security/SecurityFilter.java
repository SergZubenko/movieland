package com.sergzubenko.movieland.web.security;

import com.sergzubenko.movieland.service.api.security.AccessToken;
import com.sergzubenko.movieland.service.api.security.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebFilter("/v1/*")
public class SecurityFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);

    private LoginService loginService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        if (context != null) {
            loginService = context.getBean(LoginService.class);
        } else {
            log.error("Can't find web application context");
            throw new RuntimeException("Can't find web application context");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String tokenId = httpRequest.getHeader("uuid");

        UserPrincipalRequestWrapper httpRequestWrapped;
        if (tokenId == null) {
            httpRequestWrapped = new UserPrincipalRequestWrapper(httpRequest);
        } else {
            Optional<AccessToken> token = loginService.getActiveTokenByUUID(tokenId);
            if (!token.isPresent()) {
                log.error("token {} not found", tokenId);
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,"");
                return;
            }
            AccessToken accessToken = token.get();
            httpRequestWrapped = new UserPrincipalRequestWrapper(httpRequest, accessToken);
            log.info("token {} accepted for user {}", tokenId, accessToken.getPrincipal().getName());
        }
        chain.doFilter(httpRequestWrapped, httpResponse);
    }

    @Override
    public void destroy() {

    }

}
