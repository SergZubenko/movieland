package com.sergzubenko.movieland.web.security;

import com.sergzubenko.movieland.service.api.security.AccessToken;
import com.sergzubenko.movieland.service.api.security.LoginService;
import com.sergzubenko.movieland.service.impl.security.exception.TokenNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@WebFilter("/v1/*")
public class SecurityFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private LoginService loginService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        loginService = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext())
                .getBean(LoginService.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        Optional<AccessToken> optToken = getTokenFromRequest(request);
        if (optToken.isPresent()) {
            request = new CustomPrincipalHttpServletRequestWrapper((HttpServletRequest) request, optToken.get().getPrincipal());
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private Optional<AccessToken> getTokenFromRequest(ServletRequest request) {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Optional<String> tokenId = Optional.ofNullable(httpServletRequest.getHeader("uuid"));

        if (!tokenId.isPresent()) {
            return Optional.empty();
        }

        AccessToken token;
        try {
            token = loginService.getValidToken(tokenId.get());
            return Optional.of(token);
        } catch (TokenNotFoundException e) {
            logger.warn("token was not restored");
            return Optional.empty();
        }
    }
}
