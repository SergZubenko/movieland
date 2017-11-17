package com.sergzubenko.movieland.service.impl.security;

import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.service.api.UserService;
import com.sergzubenko.movieland.service.api.security.AuthManager;
import com.sergzubenko.movieland.service.api.security.LoginPasswordPrincipal;
import com.sergzubenko.movieland.service.impl.security.exception.InvalidUserPasswordException;
import com.sergzubenko.movieland.service.impl.security.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.security.Principal;

@Service
class LoginPasswordAuthManager implements AuthManager {

    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void auth(Principal principal) {

        if (!(principal instanceof LoginPasswordPrincipal)) {
            throw new InvalidParameterException("Current principal does not implement ILoginPasswordPrincipal");
        }
        LoginPasswordPrincipal lpPrincipal = (LoginPasswordPrincipal) principal;

        User user = userService.getUserByEmail(principal.getName());
        if (user == null) {
            logger.error("User " + principal.getName() + " not found. Using default");
            throw new UserNotFoundException("User " + principal.getName() + " does not found");
        }

        if (user.getPassword().equals(lpPrincipal.getPassword())) {
            authorize(user, lpPrincipal);
        } else {
            logger.error("Can't authenticate user: " + principal.getName() + ". Password is incorrect");
            throw new InvalidUserPasswordException("Invalid user/password. Authentication fails");
        }
    }

    private void authorize(User user, LoginPasswordPrincipal principal) {
        principal.setUser(user);
        principal.setAuthorities(userService.getRoles(user));
    }
}
