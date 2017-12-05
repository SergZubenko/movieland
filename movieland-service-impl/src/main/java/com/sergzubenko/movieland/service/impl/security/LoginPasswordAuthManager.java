package com.sergzubenko.movieland.service.impl.security;

import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.service.api.UserService;
import com.sergzubenko.movieland.service.api.security.AuthManager;
import com.sergzubenko.movieland.service.api.security.UserPrincipal;
import com.sergzubenko.movieland.service.impl.security.exception.InvalidUserPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
class LoginPasswordAuthManager implements AuthManager {

    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserPrincipal auth(String username, String password) {
        User user;
        try {
            user = userService.getUserByEmailAndPassword(username, password);
        } catch (EmptyResultDataAccessException e) {
            logger.error("User " + username + " not found");
            throw new InvalidUserPasswordException("User " + username + " does not found");
        }
        return new UserPrincipalImpl(user, userService.getRoles(user));
    }
}

