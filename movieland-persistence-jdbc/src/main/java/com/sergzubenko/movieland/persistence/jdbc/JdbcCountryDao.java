package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Country;
import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.CountryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.sergzubenko.movieland.persistence.jdbc.mapper.CountryMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.sergzubenko.movieland.persistence.jdbc.util.MovieListUtils.findInListById;
import static com.sergzubenko.movieland.persistence.jdbc.util.MovieListUtils.getIds;

@Repository
public class JdbcCountryDao implements CountryDao {
    private static final CountryMapper countryMapper = new CountryMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${sql.movie.moviesCountries}")
    String getMoviesCountriesSql;

    @Value("${sql.country.allCountries}")
    private String getAllCountiesSql;

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
}
