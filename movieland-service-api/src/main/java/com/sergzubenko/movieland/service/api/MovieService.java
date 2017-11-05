package com.sergzubenko.movieland.service.api;

import com.sergzubenko.movieland.entity.Movie;

import java.util.List;
import java.util.Map;

public interface MovieService {

    List<Movie> getRandomMovies();

    List<Movie> getMoviesByGenre(Integer genreId, Map<String, String> params);

    List<Movie> getMovies(Map<String, String> params);
}
