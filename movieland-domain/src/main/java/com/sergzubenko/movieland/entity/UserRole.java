package com.sergzubenko.movieland.entity;

public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public static UserRole getByName(String name) {
        for (UserRole userRole : values()) {
            if (userRole.value.equals(name)) {
                return userRole;
            }
        }
        return null;
    }
}
