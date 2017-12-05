package com.sergzubenko.movieland.web.controller;

import com.sergzubenko.movieland.service.impl.security.exception.InvalidUserPasswordException;
import com.sergzubenko.movieland.web.dto.ErrorResponseDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.security.InvalidParameterException;

@ControllerAdvice
public class ServletExceptionHandler {

    @ExceptionHandler({EmptyResultDataAccessException.class,
            InvalidParameterException.class
    })
    ResponseEntity<?> badRequestHandler(HttpServletRequest req, Exception e) {
        return new ResponseEntity<>(prepareErrorResponse(req, e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUserPasswordException.class)
    ResponseEntity<?> unauthorizedHandler(HttpServletRequest req, Exception e) {
        return new ResponseEntity<>(prepareErrorResponse(req, e), HttpStatus.UNAUTHORIZED);
    }

    private ErrorResponseDto prepareErrorResponse(HttpServletRequest req, Exception e) {
        return new ErrorResponseDto(req.getRequestURL().toString(), e.getMessage());
    }
}
