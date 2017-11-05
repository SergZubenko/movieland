package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.MovieDao;
import com.sergzubenko.movieland.persistence.jdbc.mapper.MovieMapper;
import com.sergzubenko.movieland.persistence.jdbc.util.OrderByAppender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class JdbcMovieDao implements MovieDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MovieMapper movieMapper = new MovieMapper();

    private OrderByAppender orderByAppender = new OrderByAppender();

    @Autowired
    private JdbcCountryDao countryDao;

    @Autowired
    private JdbcGenreDao genreDao;


    @Value("${sql.movie.movieByID}")
    private String movieByIdSQL;

    @Value("${sql.movie.allMovies}")
    private String getAllMoviesSQL;

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
    public List<Movie> getAllMovies() {
        return getAllMovies(null);
    }

    @Override
    public List<Movie> getAllMovies(Map<String, String> params) {
        String sql = orderByAppender.prepareOrderedQuery(getAllMoviesSQL, orderBySQL, Movie.class, params);
        return jdbcTemplate.query(sql, movieMapper);
    }

    @Override
    public List<Movie> getByGenre(Integer genreId, Map<String, String> params) {
        String sql = orderByAppender.prepareOrderedQuery(getMoviesByGenreSQL,  orderBySQL, Movie.class, params);
        return jdbcTemplate.query(sql, new Object[]{genreId}, movieMapper);
    }

    private List<Movie> fillSubEntities(List<Movie> movies) {
        if (movies.size() == 0) {
            return movies;
        }
        Set<Integer> ids = extractIdsFromList(movies);
        countryDao.enrichMovies(movies, ids);
        genreDao.enrichMovies(movies, ids);
        return movies;
    }

    private Set<Integer> extractIdsFromList(List<Movie> movies) {
        Set<Integer> ids = new HashSet<>();
        for (Movie movie : movies) {
            ids.add(movie.getId());
        }
        return ids;
    }


}
