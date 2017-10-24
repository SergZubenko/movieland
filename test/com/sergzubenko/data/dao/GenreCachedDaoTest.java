package com.sergzubenko.data.dao;

import com.sergzubenko.data.config.ApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by sergz on 24.10.2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@DirtiesContext
public class GenreCachedDaoTest {
    @Autowired
    @Qualifier("cached")
    GenreDao dao;

    @Test
    public void getGenres() throws Exception {
        System.out.println(dao.getClass());
        System.out.println(dao.getGenres());
    }

}