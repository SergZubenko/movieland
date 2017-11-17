package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.persistence.jdbc.config.PersistenceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class)
@DirtiesContext
public class JdbcGenreDaoITest {

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

}
