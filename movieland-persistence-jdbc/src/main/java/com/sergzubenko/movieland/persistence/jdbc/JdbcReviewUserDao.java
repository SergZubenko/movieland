package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.entity.Review;
import com.sergzubenko.movieland.persistance.api.ReviewUserDao;
import com.sergzubenko.movieland.persistence.jdbc.mapper.ReviewUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.sergzubenko.movieland.persistence.jdbc.util.MovieListUtils.findInListById;
import static com.sergzubenko.movieland.persistence.jdbc.util.MovieListUtils.getIds;

@Repository
public class JdbcReviewUserDao implements ReviewUserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sql.review.save}")
    String upsertReviewSql;

    private static final ReviewUserMapper reviewUserMapper = new ReviewUserMapper();

    @Value("${sql.review.reviewWithUser}")
    String UserReviewSql;

    @Override
    public void enrichMovies(List<Movie> movies) {
        namedParameterJdbcTemplate.query(UserReviewSql, getIds(movies), rs -> {
            Movie movie = findInListById(rs.getInt("movie_id"), movies);
            List<Review> subCollection = movie.getReviews();
            if (subCollection == null) {
                subCollection = new ArrayList<>();
                movie.setReviews(subCollection);
            }
            Review review = reviewUserMapper.mapRow(rs, 0);
            review.setMovie(movie);
            subCollection.add(review);
        });
    }

    @Override
    public void enrichMovie(Movie movie) {
        enrichMovies(Collections.singletonList(movie));
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



}
