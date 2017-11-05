package com.sergzubenko.movieland.persistence.jdbc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan(basePackages = "com.sergzubenko.movieland.persistence.jdbc",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = PersistenceConfig.class)})
@PropertySource(
        {"classpath:scheduler.properties",
                "classpath:mysql.sql.template.properties"})
public class TestConfig {

}