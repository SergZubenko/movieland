package com.sergzubenko.movieland.service.impl;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.service.api.MovieRatingService;
import com.sergzubenko.movieland.service.impl.cache.MovieRatingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MovieRatingServiceImpl implements MovieRatingService {

    @Autowired
    private MovieRatingCache ratingCache;

    @Override
    public void enrichMovies(List<Movie> movies) {
        ratingCache.enrichMovies(movies);
    }

    @Override
    public void enrichMovie(Movie movie) {
        ratingCache.enrichMovie(movie);
    }

    @Override
    public void rateMovie(Integer userId, Integer movieId, double rating) {
        ratingCache.rateMovie(userId, movieId, rating);
    }

}
