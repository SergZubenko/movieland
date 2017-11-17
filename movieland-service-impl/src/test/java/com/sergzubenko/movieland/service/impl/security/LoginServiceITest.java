package com.sergzubenko.movieland.service.impl.security;

import com.sergzubenko.movieland.service.impl.config.ServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.security.Principal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class})
@DirtiesContext
public class LoginServiceITest {
    @Autowired
    private LoginServiceImpl loginService;

    @Test
    public void login() throws Exception {
        Principal principal = new UserPrincipal("ronald.reynolds66@example.com", "paco");
        System.out.println(loginService.login(principal));
    }

}