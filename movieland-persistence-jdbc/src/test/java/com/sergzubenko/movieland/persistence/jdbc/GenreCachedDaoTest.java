package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.persistence.jdbc.mappers.GenreMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext
public class GenreCachedDaoTest {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private GenreCachedDao genreCachedDao;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private  JdbcTemplate jdbcTemplate;

    @Value("${sql.genre.allGenres}")
    private String getAllGenresSQL;

    @Before
    public void prepareMockedJDBCtemplate(){
        List<Genre> genres = new ArrayList<>(1);
        Genre genre = new Genre(10, "test genre");
        genres.add(genre);
        when(jdbcTemplate.query(matches(getAllGenresSQL), any(GenreMapper.class))).thenReturn(genres);
    }


    private void updateMockedGenres(){
        List<Genre> genres = new ArrayList<>(1);
        Genre genre = new Genre(100, "test genre updated");

        genres.add(genre);
        genres.add(genre);
        when(jdbcTemplate.query(matches(getAllGenresSQL), any(GenreMapper.class))).thenReturn(genres);
    }

    @Test
    public void getGenres() throws Exception {
        List<Genre> genres = genreCachedDao.getGenres();
        System.out.println(genres);

        assertEquals(1, genres.size());
        assertEquals("test genre",genres.get(0).getName());

        updateMockedGenres();
        System.out.println("genres updated");
        genres = genreCachedDao.getGenres();

        assertEquals("test genre",genres.get(0).getName());
        assertEquals(1, genres.size());

        Thread.sleep(3000);
        genres = genreCachedDao.getGenres();

        assertEquals(2, genres.size());
        assertEquals("test genre updated",genres.get(0).getName());
    }



}