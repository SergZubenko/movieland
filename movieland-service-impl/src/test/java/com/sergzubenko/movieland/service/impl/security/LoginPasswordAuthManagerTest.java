package com.sergzubenko.movieland.service.impl.security;

import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.entity.UserRole;
import com.sergzubenko.movieland.service.api.UserService;
import com.sergzubenko.movieland.service.api.security.ILoginPasswordPrincipal;
import com.sergzubenko.movieland.service.impl.config.ServiceConfig;
import com.sergzubenko.movieland.service.impl.security.exception.InvalidUserPasswordException;
import com.sergzubenko.movieland.service.impl.security.exception.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class})
@DirtiesContext
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

        when(userService.getUserByEmail(matches("login"))).thenReturn(user);
        when(userService.getRoles(any())).thenReturn(roleSet);
    }

    @Test
    public void auth() throws Exception {
        ILoginPasswordPrincipal principal = new UserPrincipal("login", "password");
        manager.auth(principal);
        assertEquals("login", principal.getName());
        assertEquals("password", principal.getPassword());
        Set<UserRole> roles = principal.getAuthorities();
        assertTrue(roles.contains(UserRole.USER));
        assertTrue(roles.contains(UserRole.ADMIN));
        assertFalse(roles.contains(UserRole.UNAUTHORIZED));
    }

    @Test(expected = UserNotFoundException.class)
    public void authNoUser() throws Exception {
        ILoginPasswordPrincipal principal = new UserPrincipal("nologin", "password");
        manager.auth(principal);
    }

    @Test(expected = InvalidUserPasswordException.class)
    public void authNoPass() throws Exception {
        ILoginPasswordPrincipal principal = new UserPrincipal("login", "wrong password");
        manager.auth(principal);
    }
}