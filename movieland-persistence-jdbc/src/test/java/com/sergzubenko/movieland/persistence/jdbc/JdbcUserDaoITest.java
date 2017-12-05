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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class})
public class JdbcUserDaoITest {

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
    public void getByEmailAndPassword() throws Exception {
        User user = userDao.getByEmailAndPassword("ronald.reynolds66@example.com", "paco");
        assertNull(user.getPassword());
        assertNotNull(user.getId());
        assertNotNull(user.getNickname());
        assertNotNull(user.getEmail());
    }

}