package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.MovieRating;
import com.sergzubenko.movieland.entity.MovieUserVote;
import com.sergzubenko.movieland.persistance.api.MovieRatingDao;
import com.sergzubenko.movieland.persistence.jdbc.mapper.MovieUserRatingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Repository
public class jdbcMovieRatingDao implements MovieRatingDao {
    private static final MovieUserRatingMapper MOVIE_USER_RATING_STAT_MAPPER = new MovieUserRatingMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Value("${sql.rates.save}")
    private String upsertRatesSql;

    @Value("${sql.rates.getRateStats}")
    private String getMovieRateStatsSql;

    @Override
    public void persistVotes(List<MovieUserVote> votes) {
        BatchPreparedStatementSetter statementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                MovieUserVote rate = votes.get(i);
                ps.setInt(1, rate.getMovieId());
                ps.setInt(2, rate.getUserId());
                ps.setDouble(3, rate.getRating());
            }

            @Override
            public int getBatchSize() {
                return votes.size();
            }
        };

        jdbcTemplate.batchUpdate(upsertRatesSql, statementSetter);
    }

    @Override
    public List<MovieRating> getRating(List<Integer> ids) {
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("ids", ids);
        return namedParameterJdbcTemplate.query(getMovieRateStatsSql, parameters, MOVIE_USER_RATING_STAT_MAPPER);
    }

    @Override
    public Optional<MovieRating> getRating(Integer id) {
        try {
            SqlParameterSource parameters = new MapSqlParameterSource().addValue("ids", id);
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(getMovieRateStatsSql, parameters ,MOVIE_USER_RATING_STAT_MAPPER));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }
}
