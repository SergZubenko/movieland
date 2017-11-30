package com.sergzubenko.movieland.service.api;

import com.sergzubenko.movieland.entity.Movie;

import java.util.List;
import java.util.Map;

public interface MovieService {

    List<Movie> getRandomMovies();

    List<Movie> getMoviesByGenre(Integer genreId, Map<String, String> params);

    List<Movie> getAll(Map<String, String> params);

    Movie getById(Integer id, String currency);

    Movie getById(Integer id);

    void persist(Movie movie);
}
