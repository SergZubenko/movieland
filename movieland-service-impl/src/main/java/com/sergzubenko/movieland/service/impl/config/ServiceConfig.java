package com.sergzubenko.movieland.service.impl.config;


import com.sergzubenko.movieland.persistence.jdbc.config.PersistenceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@Import(PersistenceConfig.class)
@EnableScheduling
@PropertySource({"classpath:currency.properties",
        "classpath:security.properties",
        "classpath:cache.properties"})
@ComponentScan(basePackages = "com.sergzubenko.movieland.service.impl")
public class ServiceConfig {
}
