package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Country;
import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.enums.SortOrder;
import com.sergzubenko.movieland.persistance.api.MovieDao;
import com.sergzubenko.movieland.persistence.jdbc.mappers.MovieMapper;
import com.sergzubenko.movieland.utils.SortedColumnMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JdbcMovieDao implements MovieDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private MovieMapper movieMapper = new MovieMapper();

    private SortedColumnMapper sortedColumnMapper = new SortedColumnMapper();

    @Value("${sql.movie.movieByID}")
    private String movieByIdSQL;

    @Value("${sql.movie.allMovies}")
    private String getAllMoviesSQL;

    @Value("${sql.movie.randomThreeMovies}")
    private String getRandomThreeMoviesSQL;

    @Value("${sql.movie.moviesByGenre}")
    private String getMovieByGenreSQL;

    @Value("${sql.common.orderBy}")
    private String orderBySQL;

    @Value("${sql.movie.moviesGenres}")
    String getMoviesGenresSQL;

    @Value("${sql.movie.moviesCountries}")
    String getMoviesCountriesSQL;

    @Override
    public Movie getMovieById(Integer id) {
        return fillSubEntities(jdbcTemplate.queryForObject(movieByIdSQL, new Object[]{id}, movieMapper));
    }

    @Override
    public List<Movie> getAllMovies() {
        return fillSubEntities(jdbcTemplate.query(getAllMoviesSQL, movieMapper));
    }

    @Override
    public List<Movie> getRandomMovies() {
        return fillSubEntities(jdbcTemplate.query(getRandomThreeMoviesSQL, movieMapper));
    }

    @Override
    public List<Movie> getByGenre(Integer genreId) {
        return fillSubEntities(jdbcTemplate.query(getMovieByGenreSQL, new Object[]{genreId}, movieMapper));
    }

    @Override
    public List<Movie> getAllMovies(Map<String, String> params) {
        if (params == null || params.size() == 0) {
            return getAllMovies();
        }
        return fillSubEntities(jdbcTemplate.query(prepareOrderedQuery(params), movieMapper));
    }

    String prepareOrderedQuery(Map<String, String> params) {

        StringBuilder stringBuilder = new StringBuilder(getAllMoviesSQL);
        stringBuilder.append(' ');
        stringBuilder.append(orderBySQL);
        stringBuilder.append(' ');

        StringJoiner stringJoiner = new StringJoiner(",");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            try {
                String columnName = sortedColumnMapper.getColumnName(Movie.class, entry.getKey());
                String ascDesc = SortOrder.getByName(entry.getValue()).name();
                stringJoiner.add(columnName + " " + ascDesc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        stringBuilder.append(stringJoiner.toString());
        return stringBuilder.toString();
    }

    private List<Movie> fillSubEntities(List<Movie> movies) {
        if (movies.size() == 0) {
            return movies;
        }
        fillCountries(movies);
        fillGenres(movies);
        return movies;
    }


    private Movie fillSubEntities(Movie movie) {
        List<Movie> movies = new ArrayList<>(1);
        movies.add(movie);
        fillCountries(movies);
        fillGenres(movies);
        return movies.get(0);
    }

    private Set<Integer> extractIds(List<Movie> movies) {
        Set<Integer> ids = new HashSet<>();
        for (Movie movie : movies) {
            ids.add(movie.getId());
        }
        return ids;
    }

    private Movie findMovieInList(Integer id, List<Movie> movies) {
        for (Movie movie : movies) {
            if (movie.getId().equals(id)) {
                return movie;
            }
        }
        throw new RuntimeException("Cant find movie by id " + id);
    }

    private void fillCountries(List<Movie> movies) {
        Map<String, ?> idsMap = Collections.singletonMap("ids", extractIds(movies));
        namedParameterJdbcTemplate.query(getMoviesCountriesSQL, idsMap, (rs) -> {
            Movie movie = findMovieInList(rs.getInt("movie_id"), movies);
            movie.getCountries().add(new Country(rs.getInt("id"), rs.getString("country")));
        });
    }

    private void fillGenres(List<Movie> movies) {
        Map<String, ?> idsMap = Collections.singletonMap("ids", extractIds(movies));
        namedParameterJdbcTemplate.query(getMoviesGenresSQL, idsMap, (rs) -> {
            Movie movie = findMovieInList(rs.getInt("movie_id"), movies);
            movie.getGenres().add(new Genre(rs.getInt("id"), rs.getString("genre")));
        });
    }

}
