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
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class JdbcCountryDao implements CountryDao {
    private static final CountryMapper COUNTRY_MAPPER = new CountryMapper();

    private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());


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
        return jdbcTemplate.query(getAllCountiesSql, COUNTRY_MAPPER);
    }

    @Override
    public void enrichMovies(List<Movie> movies) {
        if (movies.size() == 1) {
            enrichMovie(movies.get(0));
            return;
        }
        Map<Integer, Movie> idMovieMap = movies.stream().collect(Collectors.toMap(Movie::getId, m -> m));
        SqlParameterSource parameters = new MapSqlParameterSource("ids", idMovieMap.keySet());
        namedParameterJdbcTemplate.query(getMoviesCountriesSql, parameters, rs -> {
            Movie movie = idMovieMap.get(rs.getInt("movie_id"));
            addCountryToMovie(movie, COUNTRY_MAPPER.mapRow(rs, 0));
        });
    }

    @Override
    public void enrichMovie(Movie movie) {
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("ids", movie.getId());
        namedParameterJdbcTemplate.query(getMoviesCountriesSql, parameters, rs -> {
            Country country = COUNTRY_MAPPER.mapRow(rs, 0);
            addCountryToMovie(movie, country);
        });
    }

    @Override
    public void persistCountriesForMovie(Movie movie) {
        log.debug("Start inserting countries into movies_countries for movie {}", movie);
        long startTime = System.currentTimeMillis();

        List<Country> countriesToSave = movie.getCountries();
        Integer movieId = movie.getId();

        if (countriesToSave == null || countriesToSave.isEmpty()) {
            deleteMovieCountryLinks(movieId);
        } else {
            List<Integer> countriesIdsToSave = countriesToSave.stream().map(Country::getId).collect(Collectors.toList());
            deleteMovieCountryLinks(movieId, countriesIdsToSave);
            saveMovieCountryLinks(movieId, countriesIdsToSave);
        }

        log.debug("Finish insert countries. It took : {}ms ", System.currentTimeMillis() - startTime);
    }

    private void saveMovieCountryLinks(Integer movieId, List<Integer> countriesIds) {
        BatchPreparedStatementSetter statementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, movieId);
                ps.setInt(2, countriesIds.get(i));
            }

            @Override
            public int getBatchSize() {
                return countriesIds.size();
            }
        };

        jdbcTemplate.batchUpdate(movieAddCountriesSql, statementSetter);
    }

    void deleteMovieCountryLinks(Integer movieId, List<Integer> excludeCountryIds) {
        if (excludeCountryIds.size() == 0){
            deleteMovieCountryLinks(movieId);
            return;
        }
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("movieId", movieId)
                .addValue("ids", excludeCountryIds);
        namedParameterJdbcTemplate.update(movieClearCountriesSqlEx, parameters);
    }

    void deleteMovieCountryLinks(Integer movieId) {
        jdbcTemplate.update(movieClearCountriesSql, movieId);
    }

    private void addCountryToMovie(Movie movie, Country country) {
        List<Country> countries = movie.getCountries();
        if (countries == null) {
            countries = new ArrayList<>();
            movie.setCountries(countries);
        }
        countries.add(country);
    }

}
