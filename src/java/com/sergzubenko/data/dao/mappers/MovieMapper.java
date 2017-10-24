package com.sergzubenko.data.dao.mappers;

import com.sergzubenko.data.repository.Movie;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sergz on 24.10.2017.
 */
@Component
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
        return movie;
    }

}
