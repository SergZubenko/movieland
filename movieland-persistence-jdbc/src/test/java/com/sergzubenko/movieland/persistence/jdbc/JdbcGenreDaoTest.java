package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.persistance.api.GenreDao;
import com.sergzubenko.movieland.persistence.jdbc.mappers.GenreMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext
public class JdbcGenreDaoTest {

    @Autowired
    private GenreDao jdbcGenreDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Before
    public void prepareMockedJDBCtemplate(){
        List<Genre> genres = new ArrayList<>(1);
        Genre genre = new Genre(10, "test genre");
        genres.add(genre);
        when(jdbcTemplate.query(matches("select id, genre from genres"), any(GenreMapper.class))).thenReturn(genres);
    }

    @Test
    public void getGenres() throws Exception {
        List<Genre> genres =jdbcGenreDao.getGenres();
        System.out.println(genres);
        assertEquals(genres.get(0).getId().intValue(), 10);
        assertEquals(genres.get(0).getName(), "test genre");
    }
}
