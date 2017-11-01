package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class)
@DirtiesContext
public class IJdbcMovieDaoTest {

    @Autowired
    JdbcMovieDao jdbcMovieDao;

    @Test
    public void getMovieById() throws Exception {
        Movie origMovie = jdbcMovieDao.getAllMovies().get(0);
        assertEquals(origMovie, jdbcMovieDao.getMovieById(origMovie.getId()));
        System.out.println(origMovie);
    }

       private void checkListNotNull(List<Movie> movies) {
        Movie movie;
        for (int i = 0; i < movies.size() && i < 10; i++) {
            movie = movies.get(i);
            assertNotNull(movie.getId());
            assertNotNull(movie.getNameNative());
            assertNotNull(movie.getDescription());
        }
    }


    @Test
    public void getAllMovies() throws Exception {
        List<Movie> movies = jdbcMovieDao.getAllMovies();
        checkListNotNull(movies);
    }

    @Test
    public void getRandomMovies() throws Exception {
        List<Movie> movies = jdbcMovieDao.getRandomMovies();
        checkListNotNull(movies);
    }

    @Test
    public void getByGenre() throws Exception {
        List<Movie> movies = jdbcMovieDao.getByGenre(1);
        checkListNotNull(movies);
    }

    @Test
    public void getByMissingGenre() throws Exception {
        List<Movie> movies = jdbcMovieDao.getByGenre(-1);
        assertEquals(0, movies.size());
    }


}


