package com.sergzubenko.movieland.service.impl.config;

import com.sergzubenko.movieland.persistence.jdbc.config.PersistenceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceConfig.class)
@ComponentScan(basePackages = "com.sergzubenko.movieland.service.impl")
 public class ServiceConfig {
}
