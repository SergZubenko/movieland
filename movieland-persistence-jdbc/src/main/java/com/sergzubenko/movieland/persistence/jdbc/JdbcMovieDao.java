package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.MovieDao;
import com.sergzubenko.movieland.persistence.jdbc.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.sergzubenko.movieland.persistence.jdbc.util.OrderByAppender.prepareOrderedQuery;

@Repository
public class JdbcMovieDao implements MovieDao {

    private static final MovieMapper movieMapper = new MovieMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sql.movie.movieByID}")
    private String movieByIdSql;

    @Value("${sql.movie.allMovies}")
    private String getMoviesSql;

    @Value("${sql.movie.randomThreeMovies}")
    private String getRandomThreeMoviesSql;

    @Value("${sql.movie.moviesByGenre}")
    private String getMoviesByGenreSql;

    @Value("${sql.common.orderBy}")
    private String orderBySql;

    @Override
    public List<Movie> getRandomMovies() {
        return jdbcTemplate.query(getRandomThreeMoviesSql, movieMapper);
    }

    @Override
    public List<Movie> getMovies(Map<String, String> params) {
        String sql = prepareOrderedQuery(getMoviesSql, orderBySql, Movie.class, params);
        return jdbcTemplate.query(sql, movieMapper);
    }

    @Override
    public Movie getMovieById(Integer id) {
        return  jdbcTemplate.queryForObject(movieByIdSql, new Object[]{id}, movieMapper);
    }

    @Override
    public List<Movie> getMoviesByGenre(Integer genreId, Map<String, String> params) {
        String sql = prepareOrderedQuery(getMoviesByGenreSql, orderBySql, Movie.class, params);
        return jdbcTemplate.query(sql, new Object[]{genreId}, movieMapper);
    }
}
