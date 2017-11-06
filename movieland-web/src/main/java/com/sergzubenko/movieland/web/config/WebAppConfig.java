package com.sergzubenko.movieland.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebMvc
@ComponentScan("com.sergzubenko.movieland.web")
public class WebAppConfig{
}

