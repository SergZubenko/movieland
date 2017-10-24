package com.sergzubenko.data.dao.jdbc;

import com.sergzubenko.data.dao.GenreDao;
import com.sergzubenko.data.dao.mappers.GenreMapper;
import com.sergzubenko.data.repository.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sergz on 24.10.2017.
 */
@Repository(value = "JdbcGenreDao")
public class JdbcGenreDao implements GenreDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    GenreMapper mapper;

    final String GET_ALL = "select id, genre from genres";

    @Override
    public List<Genre> getGenres() {
        return jdbcTemplate.query(GET_ALL, mapper);
    }
}
