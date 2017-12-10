package com.sergzubenko.movieland.service.impl;

import com.sergzubenko.movieland.entity.Country;
import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.service.api.MovieService;
import com.sergzubenko.movieland.service.impl.config.ServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class})
@EnableTransactionManagement
public class MovieServiceImplTest {

    @Autowired
    private MovieService movieService;


    @Test
    @Transactional
    public void persist() throws Exception {
        Movie movie = new Movie();
        movie.setNameRussian("Name russian ");
        movie.setNameNative("Name native ");
        movie.setPrice(100.11);
        movie.setPicturePath("picPath");
        movie.setRating(10.1);
        movie.setDescription("description");
        List<Country> countries = new ArrayList<>(2);
        countries.add(new Country(1, ""));
        countries.add(new Country(2, ""));
        movie.setCountries(countries);

        List<Genre> genres = new ArrayList<>(2);
        genres.add(new Genre(1, ""));
        genres.add(new Genre(2, ""));
        movie.setGenres(genres);

        movieService.persist(movie);
        assertNotNull(movie.getId());

        Movie restored = movieService.getById(movie.getId());
        assertEquals(movie.getNameNative(), restored.getNameNative());
        assertEquals(movie.getNameRussian(), restored.getNameRussian());
        assertEquals(movie.getRating(), restored.getRating());

    }

    @Test
    public void getById() throws Exception {
        List<Movie> movies = movieService.getRandomMovies();
        Movie movie = movieService.getById(movies.get(0).getId());
        System.out.println(movie);
    }

}