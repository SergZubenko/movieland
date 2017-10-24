package com.sergzubenko.data.enums;

import java.security.InvalidParameterException;

/**
 * Created by sergz on 24.10.2017.
 */
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
