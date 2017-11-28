package com.sergzubenko.movieland.web.security.annotation;

import com.sergzubenko.movieland.entity.UserRole;
import com.sergzubenko.movieland.web.security.annotation.HasRole;
import org.springframework.web.method.HandlerMethod;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class HasRoleHandler {

    public static Set<UserRole> getRequiredRoles(Object object) {
        Set<UserRole> requiredRoles = new HashSet<>();

        if (object instanceof HandlerMethod) {
            Optional<HasRole> annotation = Optional.ofNullable(((HandlerMethod) object).getMethodAnnotation(HasRole.class));
            annotation.ifPresent(hasRole -> requiredRoles.addAll(Arrays.asList(hasRole.value())));
        }

        Class clazz = object.getClass();
        Optional<HasRole> annotation = Optional.ofNullable((HasRole) clazz.getAnnotation(HasRole.class));
        annotation.ifPresent(hasRole -> requiredRoles.addAll(Arrays.asList(hasRole.value())));
        return requiredRoles;
    }


}
