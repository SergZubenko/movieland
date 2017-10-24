package com.sergzubenko.data.dao;

import com.sergzubenko.data.repository.Movie;

import java.util.List;
import java.util.Map;

/**
 * Created by sergz on 24.10.2017.
 */
public interface MovieDao {
    Movie getMovieById(Integer id);
    List<Movie> getAllMovies();
    List<Movie> getRandomMovies();

    List<Movie> getByGenre(Integer genreId);

    List<Movie> getAllMovies(Map<String, String> params);
}
