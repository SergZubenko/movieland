package com.sergzubenko.movieland.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergzubenko.movieland.service.impl.security.exception.AlreadyLoggedInException;
import com.sergzubenko.movieland.service.impl.security.exception.InvalidUserPasswordException;
import com.sergzubenko.movieland.service.impl.security.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler({
            UserNotFoundException.class,
            InvalidParameterException.class,
            AlreadyLoggedInException.class
    })
    ResponseEntity<?> badRequestHandler(HttpServletRequest req, Exception e) {
        return new ResponseEntity<>(handleError(req, e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            InvalidUserPasswordException.class
    })
    ResponseEntity<?> unauthorizedHandler(HttpServletRequest req, Exception e) {

        return new ResponseEntity<>(handleError(req, e), HttpStatus.UNAUTHORIZED);
    }


    private String handleError(HttpServletRequest req, Exception e) {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> response = new HashMap<>();
        response.put("url", req.getRequestURL().toString());
        response.put("error", e.getMessage());
        String error = null;
        try {
            error = mapper.writeValueAsString(response);
        } catch (JsonProcessingException e1) {
            logger.error("error in JSON converter", e1);
        }

        return error;
    }


}
