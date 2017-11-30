package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.persistance.api.GenreDao;
import com.sergzubenko.movieland.persistence.jdbc.mapper.GenreMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class JdbcGenreDaoTest {

    @InjectMocks
    private GenreDao jdbcGenreDao = new JdbcGenreDao();

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Before
    public void prepareMockedJDBCTemplate() {
        MockitoAnnotations.initMocks(this);
        List<Genre> genres = new ArrayList<>(1);
        Genre genre = new Genre(10, "test genre");
        genres.add(genre);

        when(jdbcTemplate.query(any(String.class), any(GenreMapper.class))).thenReturn(genres);
    }

    @Test
    public void getGenres() throws Exception {
        List<Genre> genres = jdbcGenreDao.getAll();
        System.out.println(genres);
        assertEquals(genres.get(0).getId().intValue(), 10);
        assertEquals(genres.get(0).getName(), "test genre");
    }
}
