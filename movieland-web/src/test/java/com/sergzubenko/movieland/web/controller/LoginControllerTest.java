package com.sergzubenko.movieland.web.controller;

import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.service.api.security.AccessToken;
import com.sergzubenko.movieland.service.impl.config.ServiceConfig;
import com.sergzubenko.movieland.service.impl.security.LoginServiceImpl;
import com.sergzubenko.movieland.service.impl.security.UserPrincipal;
import com.sergzubenko.movieland.service.impl.security.token.UUIDBasedToken;
import com.sergzubenko.movieland.web.config.WebAppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.time.LocalDateTime;

import static org.mockito.Matchers.any;
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
    private LoginServiceImpl loginService;

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

        UserPrincipal principal = new UserPrincipal("login", "password");
        principal.setUser(user);

        AccessToken token = new UUIDBasedToken(principal, LocalDateTime.now());
        when(loginService.login(any(Principal.class)))
                .thenAnswer(
                        invocation -> {
                            Object[] args = invocation.getArguments();
                            ((UserPrincipal)args[0]).setUser(user);
                            return token;
                        }
                );

    }


    @Test
    public void login() throws Exception {
        String request = "{\n" +
                "\"email\" : \"ronald.reynolds66@example.com\",\n" +
                "\"password\" : \"paco\"\n" +
                "}";
        mvc.perform(
                post("/login", request)
                        .content(request)
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("nickname").value("Some guy"))
        ;
    }


}