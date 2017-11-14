package com.sergzubenko.movieland.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergzubenko.movieland.service.impl.security.exception.InvalidUserPasswordException;
import com.sergzubenko.movieland.service.impl.security.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            InvalidUserPasswordException.class,
            UserNotFoundException.class,
            InvalidParameterException.class
    })
    ResponseEntity<?> badRequestHandler(HttpServletRequest req, Exception e) {
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
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
