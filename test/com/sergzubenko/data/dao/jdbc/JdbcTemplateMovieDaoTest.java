package com.sergzubenko.data.dao.jdbc;

import com.sergzubenko.data.config.ApplicationConfig;
import com.sergzubenko.data.dao.MovieDao;
import com.sergzubenko.data.repository.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by sergz on 24.10.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class JdbcTemplateMovieDaoTest {


    @Autowired
    MovieDao movieDao;


    @Test
    public void getAllMoviesSorted() throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("rating", "asc");
        params.put("price", "desc");
        for (Movie movie : movieDao.getAllMovies(params)) {
            System.out.println(movie);
        }

    }

    @Test
    public void getMovieById() throws Exception {
        System.out.println(movieDao.getMovieById(7));
    }

    @Test
    public void getAllMovies() throws Exception {
        System.out.println(movieDao.getAllMovies());
    }

}