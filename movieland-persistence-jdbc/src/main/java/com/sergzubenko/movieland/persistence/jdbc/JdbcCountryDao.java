package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Country;
import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.CountryDao;
import com.sergzubenko.movieland.persistence.jdbc.mapper.CountryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JdbcCountryDao implements CountryDao {
    private CountryMapper countryMapper = new CountryMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${sql.movie.moviesCountries}")
    String getMoviesCountriesSQL;

    @Value("${sql.country.countiesByIds}")
    private String getCountiesByIdsSQL;

    @Value("${sql.country.allCountries}")
    private String getAllCountiesSQL;

    @Override
    public List<Country> getCountries() {
        return jdbcTemplate.query(getAllCountiesSQL, countryMapper);
    }

    private Movie findMovieInList(Integer id, List<Movie> movies) {
        for (Movie movie : movies) {
            if(movie.getId().equals(id)){
                return movie;
            }
        }
        throw new RuntimeException("No movie found with ID "+id);
    }

    void enrichMovies(List<Movie> movies, List<Integer> ids) {
        Map<String, ?> idsMap = Collections.singletonMap("ids", ids);
        namedParameterJdbcTemplate.query(getMoviesCountriesSQL, idsMap, (rs) -> {
            Movie movie = findMovieInList(rs.getInt("movie_id"), movies);
            List<Country> countries = movie.getCountries();
            if (countries == null){
                countries = new ArrayList<>();
                movie.setCountries(countries);
            }
            countries.add(countryMapper.mapRow(rs,0));
        });
    }

}
