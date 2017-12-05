package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.GenreDao;
import com.sergzubenko.movieland.persistence.jdbc.mapper.GenreMapper;
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
public class JdbcGenreDao implements GenreDao {

    private static final GenreMapper GENRE_MAPPER = new GenreMapper();

    private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${sql.genre.allGenres}")
    private String getAllGenresSql;

    @Value("${sql.genre.movie.moviesGenres}")
    private String getMoviesGenresSql;

    @Value("${sql.genre.movie.addGenres}")
    private String movieAddGenresSql;

    @Value("${sql.genre.movie.clearGenresEx}")
    private String movieClearGenresSqlEx;

    @Value("${sql.genre.movie.clearGenres}")
    private String movieClearGenresSql;

    @Override
    public List<Genre> getAll() {
        return jdbcTemplate.query(getAllGenresSql, GENRE_MAPPER);
    }

    @Override
    public void enrichMovies(List<Movie> movies) {
        if (movies.size() == 1) {
            enrichMovie(movies.get(0));
            return;
        }
        Map<Integer, Movie> idMovieMap = movies.stream().collect(Collectors.toMap(Movie::getId, m -> m));
        SqlParameterSource parameters = new MapSqlParameterSource("ids", idMovieMap.keySet());
        namedParameterJdbcTemplate.query(getMoviesGenresSql, parameters, rs -> {
            Movie movie = idMovieMap.get(rs.getInt("movie_id"));
            addGenreToMovie(movie, GENRE_MAPPER.mapRow(rs, 0));
        });
    }

    @Override
    public void enrichMovie(Movie movie) {
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("ids", movie.getId());
        namedParameterJdbcTemplate.query(getMoviesGenresSql, parameters, rs -> {
            Genre genre = GENRE_MAPPER.mapRow(rs, 0);
            addGenreToMovie(movie, genre);
        });
    }

    @Override
    public void persistGenresForMovie(Movie movie) {
        log.debug("Start inserting genres into movies_genres for movie {}", movie);
        long startTime = System.currentTimeMillis();

        List<Genre> genresToSave = movie.getGenres();
        Integer movieId = movie.getId();

        if (genresToSave == null || genresToSave.isEmpty()) {
            deleteMovieGenreLinks(movieId);
        } else {
            List<Integer> genresIdsToSave = genresToSave.stream().map(Genre::getId).collect(Collectors.toList());
            deleteMovieGenreLinks(movieId, genresIdsToSave);
            saveMovieGenreLinks(movieId, genresIdsToSave);
        }

        log.debug("Finish insert genres. It took : {}ms ", System.currentTimeMillis() - startTime);
    }

    private void saveMovieGenreLinks(Integer movieId, List<Integer> genresIds) {
        BatchPreparedStatementSetter statementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, movieId);
                ps.setInt(2, genresIds.get(i));
            }

            @Override
            public int getBatchSize() {
                return genresIds.size();
            }
        };

        jdbcTemplate.batchUpdate(movieAddGenresSql, statementSetter);
    }

    void deleteMovieGenreLinks(Integer movieId, List<Integer> excludeGenreIds) {
        if (excludeGenreIds.size() == 0){
            deleteMovieGenreLinks(movieId);
            return;
        }
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("movieId", movieId)
                .addValue("ids", excludeGenreIds);
        namedParameterJdbcTemplate.update(movieClearGenresSqlEx, parameters);
    }

    void deleteMovieGenreLinks(Integer movieId) {
        jdbcTemplate.update(movieClearGenresSql, movieId);
    }

    private void addGenreToMovie(Movie movie, Genre Genre) {
        List<Genre> genres = movie.getGenres();
        if (genres == null) {
            genres = new ArrayList<>();
            movie.setGenres(genres);
        }
        genres.add(Genre);
    }


}