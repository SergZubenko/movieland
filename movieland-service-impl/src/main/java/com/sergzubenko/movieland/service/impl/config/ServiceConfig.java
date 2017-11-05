package com.sergzubenko.movieland.service.impl.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.sergzubenko.movieland.persistence",
        "com.sergzubenko.movieland.service"
})
 public class ServiceConfig {
}
