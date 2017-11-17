package com.sergzubenko.movieland.service.impl;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.service.api.MovieService;
import com.sergzubenko.movieland.service.impl.config.ServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class})
@DirtiesContext
public class MovieServiceImplTest {


    @Autowired
    private MovieService movieService;

    @Test
    public void getById() throws Exception {
        List<Movie> movies = movieService.getRandomMovies();
        Movie movie = movieService.getById(movies.get(0).getId());
        System.out.println(movie);
    }

}