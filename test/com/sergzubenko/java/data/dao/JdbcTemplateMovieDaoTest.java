package com.sergzubenko.java.data.dao;

import com.sergzubenko.data.config.ApplicationConfig;
import com.sergzubenko.data.dao.MovieDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by sergz on 24.10.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class JdbcTemplateMovieDaoTest {
    @Autowired
    MovieDao movieDao;

    @Test
    public void getMovieById() throws Exception {
        System.out.println(movieDao.getMovieById(7));
    }

    @Test
    public void getAllMovies() throws Exception {
        System.out.println(movieDao.getAllMovies());
    }

}