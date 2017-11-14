package com.sergzubenko.movieland.persistance.api;

import com.sergzubenko.movieland.entity.Country;
import com.sergzubenko.movieland.entity.Movie;

import java.util.List;

public interface CountryDao {
    List<Country> getAll();

    void enrichMovies(List<Movie> movies);

    void enrichMovie(Movie movie);

}
