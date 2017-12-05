package com.sergzubenko.movieland.persistance.api;

import com.sergzubenko.movieland.entity.Movie;

import java.util.List;
import java.util.Map;

public interface MovieDao {

    List<Movie> getRandomMovies();

    List<Movie> getMoviesByGenre(Integer genreId, Map<String, String> params);

    List<Movie> getMovies(Map<String, String> params);

    Movie getMovieById(Integer id);

    void persist(Movie movie);
}