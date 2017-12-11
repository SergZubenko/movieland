package com.sergzubenko.movieland.service.impl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.MBeanServerFactoryBean;

@Configuration
@EnableMBeanExport
public class JMXConfig {

    @Bean
    public MBeanServerFactoryBean server() throws Exception {
        return new MBeanServerFactoryBean();
    }
}
