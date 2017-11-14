package com.sergzubenko.movieland.service.api;

import com.sergzubenko.movieland.entity.Country;
import com.sergzubenko.movieland.entity.Movie;

import java.util.List;

public interface CountryService {

    List<Country> getAll();

    void enrichMovies(List<Movie> movies);

    void enrichMovie(Movie movies);
}
