package com.sergzubenko.movieland.web.security.utils;

import com.sergzubenko.movieland.entity.UserRole;
import com.sergzubenko.movieland.web.security.HasRole;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UserRoleHandler {

    public static Set<UserRole> getRequiredRoles(Class clazz) {
        Set<UserRole> requiredRoles = new HashSet<>();
        HasRole annotation = (HasRole) clazz.getAnnotation(HasRole.class);
        requiredRoles.addAll(Arrays.asList(annotation.value()));
        return requiredRoles;
    }


}
