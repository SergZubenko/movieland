package com.sergzubenko.movieland.persistance.api;

import com.sergzubenko.movieland.entity.Movie;

import java.util.List;
import java.util.Map;

public interface MovieDao {

    List<Movie> getAllMovies();

    List<Movie> getRandomMovies();

    List<Movie> getByGenre(Integer genreId, Map<String, String> params);

    List<Movie> getAllMovies(Map<String, String> params);
}