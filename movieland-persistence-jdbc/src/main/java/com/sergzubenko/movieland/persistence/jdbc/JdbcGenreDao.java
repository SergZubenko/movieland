package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.persistance.api.GenreDao;
import com.sergzubenko.movieland.persistence.jdbc.mappers.GenreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JdbcGenreDao implements GenreDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private GenreMapper mapper = new GenreMapper();

    @Value("${sql.genre.allGenres}")
    private String getAllGenresSQL;

    @Override
    public List<Genre> getGenres() {
        return jdbcTemplate.query(getAllGenresSQL, mapper);
    }
}