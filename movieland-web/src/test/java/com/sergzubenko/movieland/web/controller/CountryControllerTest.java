package com.sergzubenko.movieland.web.controller;

import com.sergzubenko.movieland.entity.Country;
import com.sergzubenko.movieland.service.api.CountryService;
import com.sergzubenko.movieland.service.impl.config.ServiceConfig;
import com.sergzubenko.movieland.web.config.WebAppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebAppConfig.class, ServiceConfig.class})
@WebAppConfiguration
@DirtiesContext
public class CountryControllerTest {

    private MockMvc mvc;

    @Mock
    private CountryService countryService;

    @InjectMocks
    private CountryController countryController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders
                .standaloneSetup(countryController)
                .build();
    }

    @Test
    public void getAll() throws Exception {
        Logger log = LoggerFactory.getLogger(getClass());
        log.debug("get genres");

        when(countryService.getAll()).thenReturn(Collections.singletonList(new Country(111, "test genre")));
        mvc.perform(
                get("/country")
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id").value(111))
                .andExpect(jsonPath("$[0].name").value("test genre"));
    }
}
