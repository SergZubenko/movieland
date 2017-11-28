package com.sergzubenko.movieland.web.controller;

import com.sergzubenko.movieland.service.api.security.AccessToken;
import com.sergzubenko.movieland.service.api.security.LoginService;
import com.sergzubenko.movieland.service.impl.security.dto.LoginResponseDto;
import com.sergzubenko.movieland.service.impl.security.exception.AlreadyLoggedInException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;


@RestController
@CrossOrigin
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LoginService loginService;

    @RequestMapping(path = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> login(@RequestBody Map<String, String> request, Principal principal) {

        logger.info("User " + request.get("email") + " is trying to login");

        if (principal != null) {
            logger.warn("User is already logged in");
            throw new AlreadyLoggedInException("User is already logged in");
        }

        AccessToken accessToken = loginService.login(request.get("email"), request.get("password"));
        LoginResponseDto dto = new LoginResponseDto();
        dto.setNickname(accessToken.getPrincipal().getUser().getNickname());
        dto.setUuid(accessToken.uid());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(path = "/logout", method = RequestMethod.DELETE)
    public ResponseEntity<?> logout(@RequestHeader String uuid, Principal principal) {
        logger.info("User " + principal.getName() + " is logged out");
        loginService.logout(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
