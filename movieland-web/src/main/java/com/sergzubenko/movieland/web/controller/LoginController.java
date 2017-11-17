package com.sergzubenko.movieland.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergzubenko.movieland.service.api.security.AccessToken;
import com.sergzubenko.movieland.service.api.security.LoginService;
import com.sergzubenko.movieland.service.impl.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LoginService loginService;

    @RequestMapping(path = "/login" ,method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody String json) {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> request;
        try {
            request = mapper.readValue(json, new TypeReference<Map<String, String>>() {});
        } catch (IOException e) {
            logger.error("Can't parse input params");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserPrincipal principal = new UserPrincipal(request.get("email"), request.get("password"));

        AccessToken accessToken = loginService.login(principal);

        Map<String, String> response = new HashMap<>();
        response.put("uuid", accessToken.uid());
        response.put("nickname", principal.getUser().getNickname());
        String responseJson;
        try {
            responseJson = mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            logger.error("Error while creating response",e);
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(responseJson, HttpStatus.OK);
    }


    @RequestMapping(path = "/logout" ,method = RequestMethod.DELETE)
    public ResponseEntity<?> logout(@RequestHeader String uuid) {
        loginService.logout(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
