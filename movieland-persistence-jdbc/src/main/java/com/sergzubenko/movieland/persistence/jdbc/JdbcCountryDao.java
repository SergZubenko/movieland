package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Country;
import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.CountryDao;
import com.sergzubenko.movieland.persistence.jdbc.mapper.CountryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.sergzubenko.movieland.persistence.jdbc.util.MovieListUtils.findInListById;
import static com.sergzubenko.movieland.persistence.jdbc.util.MovieListUtils.getIds;

@Repository
public class JdbcCountryDao implements CountryDao {
    private static final CountryMapper countryMapper = new CountryMapper();

    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${sql.country.allCountries}")
    private String getAllCountiesSql;

    @Value("${sql.country.movie.moviesCountries}")
    String getMoviesCountriesSql;

    @Value("${sql.country.movie.clearCountriesEx}")
    private String movieClearCountriesSqlEx;

    @Value("${sql.country.movie.clearCountries}")
    private String movieClearCountriesSql;

    @Value("${sql.country.movie.addCountries}")
    private String movieAddCountriesSql;

    @Override
    public List<Country> getAll() {
        return jdbcTemplate.query(getAllCountiesSql, countryMapper);
    }

    @Override
    public void enrichMovies(List<Movie> movies) {
        namedParameterJdbcTemplate.query(getMoviesCountriesSql, getIds(movies), rs -> {
            Movie movie = findInListById(rs.getInt("movie_id"), movies);
            List<Country> subCollection = movie.getCountries();
            if (subCollection == null) {
                subCollection = new ArrayList<>();
                movie.setCountries(subCollection);
            }
            subCollection.add(countryMapper.mapRow(rs, 0));
        });
    }

    @Override
    public void enrichMovie(Movie movie) {
        enrichMovies(Collections.singletonList(movie));
    }

    @Override
    public void persistMovieCountries(Movie movie) {
        logger.debug("Start inserting countries into movies_countries for movie {}", movie);
        long startTime = System.currentTimeMillis();

        List<Integer> ids = extractIds(movie);
        deleteMovieCountries(movie, ids);

        jdbcTemplate.batchUpdate(movieAddCountriesSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, movie.getId());
                ps.setInt(2, ids.get(i));
            }

            @Override
            public int getBatchSize() {
                return ids.size();
            }
        });

        logger.info("Finish insert countries. It took : {}ms ", System.currentTimeMillis() - startTime);
    }


    void deleteMovieCountries(Movie movie, List<Integer> excludeIds) {
        logger.debug("start deleting countries from  movie {} except ids {}", movie, excludeIds);
        long startTime = System.currentTimeMillis();

        if (excludeIds.size() == 0) {
            jdbcTemplate.update(movieClearCountriesSql, movie.getId());
        } else {
            MapSqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("movieId", movie.getId())
                    .addValue("ids", excludeIds);
            namedParameterJdbcTemplate.update(movieClearCountriesSqlEx, parameters);
        }
        logger.info("Finish delete countries. It took : {}ms ", System.currentTimeMillis() - startTime);
    }

    private List<Integer> extractIds(Movie movie) {
        List<Integer> ids;
        if (movie.getCountries() == null) {
            ids = new ArrayList<>(1);
        } else {
            ids = movie.getCountries().stream().map(Country::getId).collect(Collectors.toList());
        }
        return ids;
    }
}
