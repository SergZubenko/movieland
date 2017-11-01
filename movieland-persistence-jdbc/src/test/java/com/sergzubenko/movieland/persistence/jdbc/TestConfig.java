package com.sergzubenko.movieland.persistence.jdbc;


import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.mockito.Mockito.mock;

@Configuration
@EnableScheduling
@ComponentScan(basePackages = "com.sergzubenko.movieland.persistence.jdbc",
        excludeFilters = {@ComponentScan.Filter( type = FilterType.ASSIGNABLE_TYPE, classes = PersistenceConfig.class)})
@PropertySource(value = {"classpath:dao.test.properties","classpath:mysql.sql.templates.properties"})
class TestConfig {

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return mock(JdbcTemplate.class);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return mock(NamedParameterJdbcTemplate.class);
    }
}