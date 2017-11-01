package com.sergzubenko.movieland.persistance.jdbc;


import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.persistance.jdbc.mappers.GenreMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
@ComponentScan(basePackages = "com.sergzubenko.movieland.persistance.jdbc")
class TestConfig {

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        List<Genre> genres = new ArrayList<>(1);
        Genre genre = new Genre();
        genre.setId(10);
        genre.setName("test genre");
        genres.add(genre);

        String GET_ALL = "select id, genre from genres";

        System.out.println(genres);

        when(jdbcTemplate.query(eq(GET_ALL), any(GenreMapper.class))).thenReturn(genres);
        System.out.println("initialized");
        return jdbcTemplate;
    }

    @Bean
    JdbcGenreDao jdbcGenreDao(){
        return new JdbcGenreDao();
    }
}