package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.entity.Review;
import com.sergzubenko.movieland.persistance.api.ReviewUserDao;
import com.sergzubenko.movieland.persistence.jdbc.mapper.ReviewUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.sergzubenko.movieland.persistence.jdbc.util.MovieListUtils.findInListById;
import static com.sergzubenko.movieland.persistence.jdbc.util.MovieListUtils.getIds;

@Repository
public class JdbcReviewUserDao implements ReviewUserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
            subCollection.add(review);
        });
    }

    @Override
    public void enrichMovie(Movie movie) {
        enrichMovies(Collections.singletonList(movie));
    }

}
