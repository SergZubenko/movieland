package com.sergzubenko.movieland.service.impl.security.exception;

import java.security.InvalidParameterException;

public class TokenNotFoundException extends InvalidParameterException{

    public TokenNotFoundException(String message) {
        super(message);
    }


}
