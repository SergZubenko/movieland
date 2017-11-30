package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Country;
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
public class JdbcCountryDaoITest {
    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Autowired
    private JdbcCountryDao jdbcCountryDao;

    @Test
    public void getAllCountries() {
        List<Country> country = jdbcCountryDao.getAll();
        assertNotNull(country.get(0).getId());
        assertNotNull(country.get(0).getName());
    }

    @Test
    @Transactional
    public void clearCountries() {
        logger.info("Start test for clearcountries()");

        Movie movie = new Movie();
        movie.setId(1);
        jdbcCountryDao.enrichMovie(movie);
        logger.info("Found movie {}", movie);

        List<Integer> ids = movie.getCountries().stream().map(Country::getId).collect(Collectors.toList());
        logger.info("Initial size of countries is {}", ids.size());
        ids.remove(0);
        jdbcCountryDao.deleteMovieCountries(movie, ids);
        movie.setCountries(null);
        jdbcCountryDao.enrichMovie(movie);
        logger.info("Current size of countries are {}", ids.size());
        if (ids.size() == 0) {
            assertNull(movie.getCountries());
        } else {
            assertEquals(ids.size(), movie.getCountries().size());
        }
    }

    @Test
    @Transactional
    public void addCountries() {
        logger.info("Start test for clearCountries()");

        Movie movie = new Movie();
        movie.setId(1);
        jdbcCountryDao.enrichMovie(movie);
        logger.info("Found movie {}", movie);

        Integer initSize = movie.getCountries().size();
        logger.info("Initial size of countries is {}", initSize);
        Country country = new Country(3, "");
        movie.getCountries().add(country);

        jdbcCountryDao.persistMovieCountries(movie);
        movie.setCountries(null);
        jdbcCountryDao.enrichMovie(movie);

        logger.info("Current size of countries are {}", movie.getCountries().size());
        logger.info("Restored movie {}", movie);
        assertEquals(initSize + 1, movie.getCountries().size());

    }
}
