package com.sergzubenko.data.dao.mappers;

import com.sergzubenko.data.repository.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sergz on 24.10.2017.
 */
@Component
public class GenreMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
        Genre genre = new Genre();
        genre.setName(resultSet.getString("genre"));
        return genre;
    }
}
