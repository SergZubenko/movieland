package com.sergzubenko.movieland.persistence.jdbc.mappers;

import com.sergzubenko.movieland.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
        Movie movie = new Movie();
        movie.setId(resultSet.getInt("id"));
        movie.setNameNative(resultSet.getString("movie_name_origin"));
        movie.setNameRussian(resultSet.getString("movie_name"));
        movie.setRating(resultSet.getDouble("rating"));
        movie.setPrice(resultSet.getDouble("price"));
        movie.setPicturePath(resultSet.getString("picture_path"));
        movie.setYearOfRelease(resultSet.getInt("year"));
        movie.setDescription(resultSet.getString("description"));
        return movie;
    }
}
