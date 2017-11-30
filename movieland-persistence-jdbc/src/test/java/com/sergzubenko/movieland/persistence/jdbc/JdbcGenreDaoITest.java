package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistence.jdbc.config.PersistenceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class)
@DirtiesContext
public class JdbcGenreDaoITest {

    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Autowired
    private JdbcGenreDao jdbcGenreDao;


    @Test
    public void getAllGenres() {
        List<Genre> genres = jdbcGenreDao.getAll();
        for (Genre genre : genres) {
            assertNotNull(genre.getId());
            assertNotNull(genre.getName());
        }
    }

    @Test
    @Transactional
    public void clearGenres() {
        logger.info("Start test for clearGenres()");

        Movie movie = new Movie();
        movie.setId(1);
        jdbcGenreDao.enrichMovie(movie);
        logger.info("Found movie {}", movie);

        List<Integer> ids = movie.getGenres().stream().map(Genre::getId).collect(Collectors.toList());
        logger.info("Initial size of genres is {}", ids.size());
        ids.remove(0);
        jdbcGenreDao.deleteMovieGenres(movie, ids);
        movie.setGenres(null);
        jdbcGenreDao.enrichMovie(movie);
        logger.info("Current size of genres are {}", ids.size());
        if (ids.size() == 0) {
            assertNull(movie.getGenres());
        } else {
            assertEquals(ids.size(), movie.getGenres().size());
        }
    }

    @Test
    @Transactional
    public void addGenres() {
        logger.info("Start test for clearGenres()");

        Movie movie = new Movie();
        movie.setId(1);
        jdbcGenreDao.enrichMovie(movie);
        logger.info("Found movie {}", movie);

        Integer initSize = movie.getGenres().size();

        logger.info("Initial size of genres is {}", initSize);

        Genre genre = new Genre(3, "");
        movie.getGenres().add(genre);

        jdbcGenreDao.persistMovieGenres(movie);
        movie.setGenres(null);
        jdbcGenreDao.enrichMovie(movie);
        logger.info("Current size of genres are {}", movie.getGenres().size());
        logger.info("Restored movie {}", movie);
        assertEquals(initSize + 1, movie.getGenres().size());
    }

}
