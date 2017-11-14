package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.GenreDao;
import com.sergzubenko.movieland.persistence.jdbc.mapper.GenreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.sergzubenko.movieland.persistence.jdbc.util.MovieListUtils.findInListById;
import static com.sergzubenko.movieland.persistence.jdbc.util.MovieListUtils.getIds;

@Repository
public class JdbcGenreDao implements GenreDao {

    private static final GenreMapper genreMapper = new GenreMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${sql.genre.allGenres}")
    private String getAllGenresSql;

    @Value("${sql.movie.moviesGenres}")
    String getMoviesGenresSql;

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
}