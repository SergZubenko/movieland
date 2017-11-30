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
public class JdbcGenreDao implements GenreDao {

    private static final GenreMapper genreMapper = new GenreMapper();

    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

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
        return jdbcTemplate.query(getAllGenresSql, genreMapper);
    }

    @Override
    public void enrichMovies(List<Movie> movies) {
        namedParameterJdbcTemplate.query(getMoviesGenresSql, getIds(movies), rs -> {
            Movie movie = findInListById(rs.getInt("movie_id"), movies);
            List<Genre> subCollection = movie.getGenres();
            if (subCollection == null) {
                subCollection = new ArrayList<>();
                movie.setGenres(subCollection);
            }
            subCollection.add(genreMapper.mapRow(rs, 0));
        });
    }

    @Override
    public void enrichMovie(Movie movie) {
        enrichMovies(Collections.singletonList(movie));
    }

    @Override
    public void persistMovieGenres(Movie movie) {

        logger.debug("Start inserting genres into movies_genres for movie {} ", movie);
        long startTime = System.currentTimeMillis();


        List<Integer> ids = extractIds(movie);
        deleteMovieGenres(movie, ids);


        jdbcTemplate.batchUpdate(movieAddGenresSql, new BatchPreparedStatementSetter() {
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

        logger.info("Finish insert genres. Spent : {}ms ", System.currentTimeMillis() - startTime);
    }

    void deleteMovieGenres(Movie movie, List<Integer> excludeIds) {
        logger.debug("start deleting genres from  movie {} except ids {}", movie, excludeIds);
        long startTime = System.currentTimeMillis();

        if (excludeIds.size() == 0) {
            jdbcTemplate.update(movieClearGenresSql, movie.getId());
        } else {

            MapSqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("movieId", movie.getId())
                    .addValue("ids", excludeIds);
            namedParameterJdbcTemplate.update(movieClearGenresSqlEx, parameters);
        }
        logger.info("Finish delete genres. Spent : {}ms ", System.currentTimeMillis() - startTime);
    }


    private List<Integer> extractIds(Movie movie) {
        List<Integer> ids;
        if (movie.getGenres() == null) {
            ids = new ArrayList<>(1);
        } else {
            ids = movie.getGenres().stream().map(Genre::getId).collect(Collectors.toList());
        }
        return ids;
    }

}