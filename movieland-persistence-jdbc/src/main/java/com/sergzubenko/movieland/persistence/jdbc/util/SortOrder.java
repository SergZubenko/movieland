package com.sergzubenko.movieland.persistence.jdbc.util;

import java.security.InvalidParameterException;

public enum SortOrder {
    ASC, DESC;

    public static SortOrder getByName(String name){
        if ("asc".equals(String.valueOf(name).toLowerCase())) {
            return ASC;
        }else if ("desc".equals(String.valueOf(name).toLowerCase())){
            return DESC;
        }else throw new InvalidParameterException("invalid param "+ name);
    }
}
