package com.sergzubenko.movieland.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;

public class CustomPrincipalHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final Principal principal;

    public CustomPrincipalHttpServletRequestWrapper(HttpServletRequest request, Principal principal) {
        super(request);
        this.principal = principal;
    }

    @Override
    public Principal getUserPrincipal() {
        return principal;
    }


}
