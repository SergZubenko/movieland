package com.sergzubenko.movieland.persistence.jdbc.util;

import java.security.InvalidParameterException;

public enum SortOrder {
    ASC("ASC"), DESC("DESC");

    private final String value;

    SortOrder(String value) {
        this.value = value;
    }

    public static SortOrder getByName(String name) {
        for (SortOrder sortOrder : values()) {
            if (sortOrder.value.equalsIgnoreCase(name)) {
                return sortOrder;
            }
        }
        throw new InvalidParameterException("Sort order value not found. Value: "+ name);
    }
}
