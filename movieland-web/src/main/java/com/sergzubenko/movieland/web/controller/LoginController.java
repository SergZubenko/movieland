package com.sergzubenko.movieland.web.controller;

import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.service.api.security.AccessToken;
import com.sergzubenko.movieland.service.api.security.LoginService;
import com.sergzubenko.movieland.service.api.security.UserPrincipal;
import com.sergzubenko.movieland.web.dto.login.EmailPasswordDto;
import com.sergzubenko.movieland.web.dto.login.LoginResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@CrossOrigin
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @RequestMapping(path = "/login", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> login(@RequestBody EmailPasswordDto request) {
        log.info("User " + request.getEmail() + " is trying to login");

        UserPrincipal principal = loginService.login(request.getEmail(), request.getPassword());
        AccessToken accessToken = loginService.generateNewToken(principal);
        LoginResponseDto response = prepareResponse(principal.getUser(), accessToken);

        log.info("User " + request.getEmail() + " is logged in");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(path = "/logout", method = RequestMethod.DELETE)
    public ResponseEntity<?> logout(@RequestHeader String uuid, Principal principal) {
        loginService.logout(uuid);

        log.info("User " + principal.getName() + " is logged out");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private LoginResponseDto prepareResponse(User user, AccessToken token) {
        LoginResponseDto dto = new LoginResponseDto();
        dto.setNickname(user.getNickname());
        dto.setUuid(token.uid());
        return dto;
    }


}
