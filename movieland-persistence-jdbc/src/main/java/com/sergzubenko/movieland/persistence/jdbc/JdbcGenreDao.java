package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.GenreDao;
import com.sergzubenko.movieland.persistence.jdbc.mapper.GenreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JdbcGenreDao implements GenreDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${sql.genre.allGenres}")
    private String getAllGenresSQL;

    @Value("${sql.movie.moviesGenres}")
    String getMoviesGenresSQL;

    private GenreMapper genreMapper = new GenreMapper();

    @Override
    public List<Genre> getGenres() {
        return jdbcTemplate.query(getAllGenresSQL, genreMapper);
    }

    private Movie findMovieInList(Integer id, List<Movie> movies) {
        return movies.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
    }

    void enrichMovies(List<Movie> movies, Set<Integer> ids) {
        Map<String, ?> idsMap = Collections.singletonMap("ids", ids);
        namedParameterJdbcTemplate.query(getMoviesGenresSQL, idsMap, (rs) -> {
            Movie movie = findMovieInList(rs.getInt("movie_id"), movies);
            List<Genre> genres = movie.getGenres();
            if (genres == null){
                genres = new ArrayList<>();
                movie.setGenres(genres);
            }
            genres.add(genreMapper.mapRow(rs,0));

        });
    }
}