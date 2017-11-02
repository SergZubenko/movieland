package com.sergzubenko.movieland.config;

import com.sergzubenko.movieland.serialize.JSONConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@PropertySource("classpath:web.properties")
@ComponentScan("com.sergzubenko.movieland")
public class WebAppConfig extends WebMvcConfigurerAdapter {


    @Bean
    JSONConverter jsonConverter() {
        return new JSONConverter();
    }
}

