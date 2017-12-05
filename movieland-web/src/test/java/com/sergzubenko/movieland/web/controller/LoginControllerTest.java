package com.sergzubenko.movieland.web.controller;

import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.entity.UserRole;
import com.sergzubenko.movieland.service.api.security.AccessToken;
import com.sergzubenko.movieland.service.api.security.LoginService;
import com.sergzubenko.movieland.service.impl.config.ServiceConfig;
import com.sergzubenko.movieland.service.impl.security.UserPrincipalImpl;
import com.sergzubenko.movieland.service.impl.security.token.UUIDBasedToken;
import com.sergzubenko.movieland.web.config.WebAppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebAppConfig.class, ServiceConfig.class})
@WebAppConfiguration
public class LoginControllerTest {
    private MockMvc mvc;

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders
                .standaloneSetup(loginController)
                .build();

        User user = new User();
        user.setNickname("Some guy");
        user.setPassword("password");

        Set<UserRole> roles = new HashSet<>();

        UserPrincipalImpl principal = new UserPrincipalImpl(user, roles);

        AccessToken token = new UUIDBasedToken(principal, LocalDateTime.now());
        when(loginService.login(matches("someguy"), matches("password"))).thenReturn(principal);
        when(loginService.generateNewToken(any())).thenReturn(token);
    }

    @Test
    public void login() throws Exception {
        String request = "{\n" +
                "\"email\" : \"someguy\",\n" +
                "\"password\" : \"password\"\n" +
                "}";
        mvc.perform(
                post("/login", request)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("nickname").value("Some guy"));
    }

    @Configuration
    static class config {
        @Bean
        LoginService loginService() {
            return mock(LoginService.class);
        }
    }
}
