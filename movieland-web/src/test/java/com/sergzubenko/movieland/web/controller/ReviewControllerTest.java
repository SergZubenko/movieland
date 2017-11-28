package com.sergzubenko.movieland.web.controller;

import com.sergzubenko.movieland.entity.Review;
import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.entity.UserRole;
import com.sergzubenko.movieland.service.api.ReviewService;
import com.sergzubenko.movieland.service.api.security.LoginService;
import com.sergzubenko.movieland.service.api.security.UserPrincipal;
import com.sergzubenko.movieland.service.impl.config.ServiceConfig;
import com.sergzubenko.movieland.service.impl.security.UserPrincipalImpl;
import com.sergzubenko.movieland.web.config.WebAppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebAppConfig.class, ServiceConfig.class, ReviewControllerTest.TestConfiguration.class})
@WebAppConfiguration
public class ReviewControllerTest {
    private MockMvc mvc;

    @Mock
    private ReviewService reviewService;

    @Mock
    private LoginService loginService;

    @InjectMocks
    private ReviewController reviewController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders
                .standaloneSetup(reviewController)
                .build();

        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            Review review = (Review) args[0];
            review.setId(100);
            return null;
        }).when(reviewService).save(any(Review.class));

    }

    @Test
    public void postReview() throws Exception {
        String request = "{\n" +
                "\"movieId\" : 1,\n" +
                "\"text\" : \"Очень понравилось!\"\n" +
                "}";
        User user = new User();
        user.setNickname("Some guy");
        user.setPassword("password");

        UserPrincipal principal = new UserPrincipalImpl(user);

        Set<UserRole> roles = new HashSet<>();
        roles.add(UserRole.USER);
        roles.add(UserRole.ADMIN);
        principal.setAuthorities(roles);

        mvc.perform(
                post("/review")
                        .principal(principal)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("uuid", "111111111111111111111")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("100"));
    }


    @Configuration
    static class TestConfiguration {

        @Bean
        @Primary
        ReviewController reviewController() {
            return null;
        }

    }


}