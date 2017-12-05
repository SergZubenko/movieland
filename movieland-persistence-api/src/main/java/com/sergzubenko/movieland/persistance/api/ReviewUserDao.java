package com.sergzubenko.movieland.persistance.api;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.entity.Review;

import java.util.List;

public interface ReviewUserDao {

    void enrichMovies(List<Movie> movies);

    void enrichMovie(Movie movie);

    void save(Review review);
}
