package com.sergzubenko.movieland.persistance.api;

import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.entity.UserRole;

import java.util.List;
import java.util.Set;

public interface UserDao {
    List<User> getAll();

    User getByEmailAndPassword(String email, String password);

    Set<UserRole> getRoles(User user);

}
