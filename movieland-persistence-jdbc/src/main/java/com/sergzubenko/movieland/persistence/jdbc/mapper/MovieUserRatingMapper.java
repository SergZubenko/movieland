package com.sergzubenko.movieland.persistence.jdbc.mapper;

import com.sergzubenko.movieland.entity.MovieRating;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieUserRatingMapper implements RowMapper<MovieRating> {
    @Nullable
    @Override
    public MovieRating mapRow(ResultSet rs, int rowNum) throws SQLException {
        MovieRating rate = new MovieRating();
        rate.setMovieId(rs.getInt(1));
        rate.setRating(rs.getDouble(2));
        rate.setVotes(rs.getInt(3));
        return rate;
    }
}
