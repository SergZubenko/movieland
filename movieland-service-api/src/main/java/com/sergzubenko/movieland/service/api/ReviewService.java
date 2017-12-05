package com.sergzubenko.movieland.service.api;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.entity.Review;

import java.util.List;

public interface ReviewService {

    void enrichMovies(List<Movie> movies);

    void enrichMovie(Movie movie);

    void persist(Review review);
}
