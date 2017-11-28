package com.sergzubenko.movieland.service.impl.security;

import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.service.api.UserService;
import com.sergzubenko.movieland.service.api.security.AuthManager;
import com.sergzubenko.movieland.service.api.security.UserPrincipal;
import com.sergzubenko.movieland.service.impl.security.exception.InvalidUserPasswordException;
import com.sergzubenko.movieland.service.impl.security.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class LoginPasswordAuthManager implements AuthManager {

    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserPrincipal auth(String username, String password) {

        User user = userService.getUserByEmail(username);
        if (user == null) {
            logger.error("User " + username + " not found. Using default");
            throw new UserNotFoundException("User " + username + " does not found");
        }

        if (user.getPassword().equals(password)) {
            UserPrincipal principal = new UserPrincipalImpl(user);
            authorize(user, principal);
            return principal;
        } else {
            logger.error("Can't authenticate user: " + username + ". Password is incorrect");
            throw new InvalidUserPasswordException("Invalid user/password. Authentication fails");
        }
    }

    private void authorize(User user, UserPrincipal principal) {
        principal.setUser(user);
        principal.setAuthorities(userService.getRoles(user));
    }
}

