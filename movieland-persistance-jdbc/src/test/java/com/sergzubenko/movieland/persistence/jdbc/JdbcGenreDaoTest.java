package com.sergzubenko.movieland.persistance.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class JdbcGenreDaoTest {

    @Autowired
    private JdbcGenreDao jdbcGenreDao;

    @Test
    public void getGenres() throws Exception {
        System.out.println(jdbcGenreDao.getGenres());
    }


}
