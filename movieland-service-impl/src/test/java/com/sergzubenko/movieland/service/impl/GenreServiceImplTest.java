package com.sergzubenko.movieland.service.impl;

import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.persistance.api.GenreDao;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;



public class GenreServiceImplTest {


    @Test
    public void getGenres() throws Exception {



    }

    @Configuration
    @ComponentScan(basePackages = "com.sergzubenko.movieland.service.impl")
    class TestConfg{

        @Bean
        GenreDao genreDao(){
            GenreDao genreDao = Mockito.mock(GenreDao.class);
            List<Genre> genres = new ArrayList<>();
            Genre genre = new Genre(11,"test genre");
            genres.add(genre);
            when(genreDao.getGenres()).thenReturn(genres);
            return  genreDao;
        };

    }
}