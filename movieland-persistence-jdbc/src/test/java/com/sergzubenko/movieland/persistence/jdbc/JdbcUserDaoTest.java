package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.entity.UserRole;
import com.sergzubenko.movieland.persistance.api.UserDao;
import com.sergzubenko.movieland.persistence.jdbc.config.PersistenceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class})
public class JdbcUserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void getRoles() throws Exception {
        User user = new User();
        user.setId(1);
        Set<UserRole> roles = userDao.getRoles(user);
        for (UserRole role : roles) {
            assertNotNull(role);
        }
    }

    @Test
    public void getAll() throws Exception {
        List<User> users = userDao.getAll();
        for (User user : users) {
            assertNotNull(user.getEmail());
            assertNotNull(user.getId());
            assertNull(user.getPassword());
        }
    }

    @Test
    public void getByEmail() throws Exception {
        User paramUser = userDao.getAll().get(0);
        User user = userDao.getByEmail(paramUser.getEmail());
        assertNotNull(user.getPassword());
        assertEquals(paramUser.getId(), user.getId());
        assertEquals(paramUser.getNickname(), user.getNickname());
        assertEquals(paramUser.getEmail(), user.getEmail());
    }

}