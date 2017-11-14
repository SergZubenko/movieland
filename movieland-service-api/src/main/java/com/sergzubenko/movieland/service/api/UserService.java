package com.sergzubenko.movieland.service.api;

import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.entity.UserRole;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getAll();

    User getUserByEmail(String email);

    Set<UserRole> getRoles(User user);

}
