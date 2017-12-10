package com.sergzubenko.movieland.service.api;

import com.sergzubenko.movieland.entity.Movie;

import java.util.List;

public interface MovieRatingService {

    void enrichMovies(List<Movie> movies);

    void enrichMovie(Movie movie);

    void rateMovie(Integer userId, Integer movieId, double rating);


}
