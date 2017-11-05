package com.sergzubenko.movieland.persistence.jdbc.mapper;

import com.sergzubenko.movieland.entity.Genre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Genre(resultSet.getInt("id"), resultSet.getString("genre"));
    }
}
