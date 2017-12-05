package com.sergzubenko.movieland.service.impl;

import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.entity.UserRole;
import com.sergzubenko.movieland.persistance.api.UserDao;
import com.sergzubenko.movieland.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        return userDao.getByEmailAndPassword(email, password);
    }

    @Override
    public Set<UserRole> getRoles(User user) {
        return userDao.getRoles(user);
    }
}
