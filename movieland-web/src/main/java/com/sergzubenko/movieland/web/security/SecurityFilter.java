package com.sergzubenko.movieland.web.security;

import com.sergzubenko.movieland.service.api.security.AccessToken;
import com.sergzubenko.movieland.service.api.security.LoginService;
import com.sergzubenko.movieland.service.impl.security.exception.TokenNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.security.Principal;

public class SecurityFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LoginService loginService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {


    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String tokenId = httpServletRequest.getHeader("uuid");

        AccessToken token = null;
        try {
            token = loginService.getValidToken(tokenId);
        } catch (TokenNotFoundException e) {
            logger.warn("token was not restored");
        }

        if (token != null) {
            Principal principal = token.getPrincipal();

            HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(httpServletRequest) {
                @Override
                public Principal getUserPrincipal() {
                    return principal;
                }
            };

            chain.doFilter(requestWrapper, response);
        } else
            chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }


}
