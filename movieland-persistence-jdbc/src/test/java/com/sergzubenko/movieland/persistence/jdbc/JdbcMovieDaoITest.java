package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistence.jdbc.config.PersistenceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class})
@DirtiesContext
public class JdbcMovieDaoITest {

    @Autowired
    private JdbcMovieDao jdbcMovieDao;

    private void checkListNotNull(List<Movie> movies) {
        Movie movie;
        for (int i = 0; i < movies.size() && i < 10; i++) {
            movie = movies.get(i);
            assertNotNull(movie.getId());
            assertNotNull(movie.getYearOfRelease());
            assertNotNull(movie.getNameNative());
            assertNotNull(movie.getNameRussian());
            assertNotNull(movie.getDescription());
        }
    }

    @Test
    public void getAllMovies() throws Exception {
        List<Movie> movies = jdbcMovieDao.getAll(null);
        checkListNotNull(movies);
    }

    @Test
    public void getRandomMovies() throws Exception {
        List<Movie> movies = jdbcMovieDao.getRandomMovies();
        checkListNotNull(movies);
    }

    @Test
    public void getByGenre() throws Exception {
        List<Movie> movies = jdbcMovieDao.getMoviesByGenre(1, null);
        checkListNotNull(movies);
    }

    @Test
    public void getByMissingGenre() throws Exception {
        List<Movie> movies = jdbcMovieDao.getMoviesByGenre(-1, null);
        assertEquals(0, movies.size());
    }


    @Test
    @Transactional
    public void save() throws Exception {
        Movie movie = new Movie();
        movie.setNameRussian("Name russian");
        movie.setNameNative("Name native");
        movie.setPrice(100.11);
        movie.setPicturePath("picPath");
        movie.setRating(10.1);
        movie.setDescription("description");
        jdbcMovieDao.persist(movie);
        assertNotNull(movie.getId());
    }

}


