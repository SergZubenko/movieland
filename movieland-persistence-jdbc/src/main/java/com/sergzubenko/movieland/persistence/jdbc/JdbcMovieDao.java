package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.MovieDao;
import com.sergzubenko.movieland.persistence.jdbc.mapper.MovieMapper;
import com.sergzubenko.movieland.persistence.jdbc.util.OrderByAppender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JdbcMovieDao implements MovieDao {

    private MovieMapper movieMapper = new MovieMapper();

    private OrderByAppender orderByAppender = new OrderByAppender();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcCountryDao countryDao;

    @Autowired
    private JdbcGenreDao genreDao;

    @Value("${sql.movie.movieByID}")
    private String movieByIdSQL;

    @Value("${sql.movie.allMovies}")
    private String getMoviesSQL;

    @Value("${sql.movie.randomThreeMovies}")
    private String getRandomThreeMoviesSQL;

    @Value("${sql.movie.moviesByGenre}")
    private String getMoviesByGenreSQL;

    @Value("${sql.common.orderBy}")
    private String orderBySQL;

    @Override
    public List<Movie> getRandomMovies() {
        return fillSubEntities(jdbcTemplate.query(getRandomThreeMoviesSQL, movieMapper));
    }

    @Override
    public List<Movie> getMovies(Map<String, String> params) {
        String sql = orderByAppender.prepareOrderedQuery(getMoviesSQL, orderBySQL, Movie.class, params);
        return jdbcTemplate.query(sql, movieMapper);
    }

    @Override
    public List<Movie> getMoviesByGenre(Integer genreId, Map<String, String> params) {
        String sql = orderByAppender.prepareOrderedQuery(getMoviesByGenreSQL,  orderBySQL, Movie.class, params);
        return jdbcTemplate.query(sql, new Object[]{genreId}, movieMapper);
    }

    private List<Movie> fillSubEntities(List<Movie> movies) {
        if (movies.size() == 0) {
            return movies;
        }
        List<Integer> ids = extractIdsFromList(movies);
        countryDao.enrichMovies(movies, ids);
        genreDao.enrichMovies(movies, ids);
        return movies;
    }

    //to avoid extra loop in each enrichment
    private List<Integer> extractIdsFromList(List<Movie> movies) {
        List<Integer> ids = new ArrayList<>(movies.size());
        movies.forEach(movie -> ids.add(movie.getId()));
        return ids;
    }
}
