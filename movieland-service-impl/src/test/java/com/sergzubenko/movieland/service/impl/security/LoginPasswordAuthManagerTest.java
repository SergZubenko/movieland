package com.sergzubenko.movieland.service.impl.security;

import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.entity.UserRole;
import com.sergzubenko.movieland.service.api.UserService;
import com.sergzubenko.movieland.service.api.security.UserPrincipal;
import com.sergzubenko.movieland.service.impl.config.ServiceConfig;
import com.sergzubenko.movieland.service.impl.security.exception.InvalidUserPasswordException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class})
public class LoginPasswordAuthManagerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private LoginPasswordAuthManager manager;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        User user = new User();
        user.setPassword("password");
        user.setEmail("login");

        Set<UserRole> roleSet = new HashSet<>();
        roleSet.add(UserRole.USER);
        roleSet.add(UserRole.ADMIN);

        when(userService.getUserByEmailAndPassword(matches("login"), matches("password"))).thenReturn(user);
        when(userService.getUserByEmailAndPassword(matches("nologin"),any())).thenThrow(new EmptyResultDataAccessException(1));
        when(userService.getRoles(any())).thenReturn(roleSet);
    }

    @Test
    public void auth() throws Exception {
        UserPrincipal principal = manager.auth("login", "password");
        assertEquals("login", principal.getName());
        Set<UserRole> roles = principal.getRoles();
        assertTrue(roles.contains(UserRole.USER));
        assertTrue(roles.contains(UserRole.ADMIN));
    }

    @Test(expected = InvalidUserPasswordException.class)
    public void authNoUser() throws Exception {
        manager.auth("nologin", "password");
    }
}