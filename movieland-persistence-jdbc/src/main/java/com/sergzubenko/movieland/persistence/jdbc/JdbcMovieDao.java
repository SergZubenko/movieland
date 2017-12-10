package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.MovieDao;
import com.sergzubenko.movieland.persistence.jdbc.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.sergzubenko.movieland.persistence.jdbc.util.OrderByAppender.prepareOrderedQuery;

@Repository
public class JdbcMovieDao implements MovieDao {

    private static final MovieMapper MOVIE_MAPPER = new MovieMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${sql.movie.movieByID}")
    private String movieByIdSql;

    @Value("${sql.movie.allMovies}")
    private String getMoviesSql;

    @Value("${sql.movie.randomThreeMovies}")
    private String getRandomThreeMoviesSql;

    @Value("${sql.movie.moviesByGenre}")
    private String getMoviesByGenreSql;

    @Value("${sql.movie.save}")
    private String upsertMovieSql;


    @Value("${sql.common.orderBy}")
    private String orderBySql;

    @Override
    public List<Movie> getRandomMovies() {
        return jdbcTemplate.query(getRandomThreeMoviesSql, MOVIE_MAPPER);
    }

    @Override
    public List<Movie> getAll(Map<String, String> params) {
        String sql = prepareOrderedQuery(getMoviesSql, orderBySql, Movie.class, params);
        return jdbcTemplate.query(sql, MOVIE_MAPPER);
    }

    @Override
    public Movie getMovieById(Integer id) {
        return jdbcTemplate.queryForObject(movieByIdSql, MOVIE_MAPPER, id);
    }

    @Override
    public List<Movie> getMoviesByGenre(Integer genreId, Map<String, String> params) {
        String sql = prepareOrderedQuery(getMoviesByGenreSql, orderBySql, Movie.class, params);
        return jdbcTemplate.query(sql, new Object[]{genreId}, MOVIE_MAPPER);
    }

    @Override
    public void persist(Movie movie) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", movie.getId())
                .addValue("movie_name", movie.getNameRussian())
                .addValue("movie_name_origin", movie.getNameNative())
                .addValue("year", movie.getYearOfRelease())
                .addValue("description", movie.getDescription())
                .addValue("rating", movie.getRating())
                .addValue("price", movie.getPrice())
                .addValue("picture_path", movie.getPicturePath());

        namedParameterJdbcTemplate.update(upsertMovieSql, parameters, keyHolder);
        if (movie.getId() == null) {
            movie.setId(keyHolder.getKey().intValue());
        }
    }


}
