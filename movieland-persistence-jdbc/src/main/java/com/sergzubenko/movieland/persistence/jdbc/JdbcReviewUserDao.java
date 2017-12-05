package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.entity.Review;
import com.sergzubenko.movieland.persistance.api.ReviewUserDao;
import com.sergzubenko.movieland.persistence.jdbc.mapper.UserReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class JdbcReviewUserDao implements ReviewUserDao {

    private static final UserReviewMapper USER_REVIEW_MAPPER = new UserReviewMapper();

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sql.review.save}")
    String upsertReviewSql;

    @Value("${sql.review.reviewWithUser}")
    String getReviewsSql;

    @Override
    public void enrichMovies(List<Movie> movies) {
        if (movies.size() == 1) {
            enrichMovie(movies.get(0));
            return;
        }
        Map<Integer, Movie> idMovieMap = movies.stream().collect(Collectors.toMap(Movie::getId, m -> m));
        SqlParameterSource parameters = new MapSqlParameterSource("ids", idMovieMap.keySet());
        namedParameterJdbcTemplate.query(getReviewsSql, parameters, rs -> {
            Movie movie = idMovieMap.get(rs.getInt("movie_id"));
            Review review = USER_REVIEW_MAPPER.mapRow(rs, 0);
            addReviewToMovie(movie, review);
        });
    }

    @Override
    public void enrichMovie(Movie movie) {
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("ids", movie.getId());
        namedParameterJdbcTemplate.query(getReviewsSql, parameters, rs -> {
            Review review = USER_REVIEW_MAPPER.mapRow(rs, 0);
            addReviewToMovie(movie, review);
        });
    }

    @Override
    public void save(Review review) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator creator = con -> {
            PreparedStatement preparedStatement = con.prepareStatement(upsertReviewSql);
            preparedStatement.setObject(1, review.getId());
            preparedStatement.setInt(2, review.getUser().getId());
            preparedStatement.setString(3, review.getText());
            preparedStatement.setInt(4, review.getMovie().getId());
            return preparedStatement;
        };

        jdbcTemplate.update(creator, keyHolder);
        if (review.getId() == null) {
            review.setId(keyHolder.getKey().intValue());
        }
    }

    private void addReviewToMovie(Movie movie, Review review) {
        review.setMovie(movie);
        List<Review> reviews = movie.getReviews();
        if (reviews == null) {
            reviews = new ArrayList<>();
            movie.setReviews(reviews);
        }
        reviews.add(review);
    }
}
