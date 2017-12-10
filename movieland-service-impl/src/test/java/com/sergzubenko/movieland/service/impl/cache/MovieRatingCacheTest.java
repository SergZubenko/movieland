package com.sergzubenko.movieland.service.impl.cache;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.entity.MovieRating;
import com.sergzubenko.movieland.persistance.api.MovieRatingDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MovieRatingCacheTest.class)
public class MovieRatingCacheTest {

    @Autowired
    private MovieRatingCache ratingCache;

    @Autowired
    private MovieRatingDao movieRatingDao;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        MovieRating movieRating = new MovieRating();
        movieRating.setMovieId(1);
        movieRating.setRating(9.5);
        movieRating.setVotes(1);
        when(movieRatingDao.getRating(anyInt())).thenReturn(Optional.of(movieRating));
    }

    @Test
    @DirtiesContext
    public void rateMovie() throws Exception {

        Movie movie = new Movie();
        movie.setId(1);
        ratingCache.enrichMovie(movie);
        assertEquals(9.5, movie.getRating(), 0.0d);
        ratingCache.rateMovie(1, 1, 9.3);

        ratingCache.enrichMovie(movie);
        assertEquals(9.4, movie.getRating(), 0.0d);
    }


    @Test
    @DirtiesContext
    public void rateMovieTwice() throws Exception {
        Movie movie = new Movie();
        movie.setId(1);
        ratingCache.enrichMovie(movie);
        assertEquals(9.5, movie.getRating(), 0.0d);
    }


    @Bean
    @Primary
    public MovieRatingDao ratingDao() {
        return mock(MovieRatingDao.class);
    }

    @Bean
    MovieRatingCache movieRatingCache(){
        return new MovieRatingCache();
    }
}