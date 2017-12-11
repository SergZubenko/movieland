package com.sergzubenko.movieland.service.impl;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.service.api.RatingService;
import com.sergzubenko.movieland.service.api.enrichment.EnrichmentType;
import com.sergzubenko.movieland.service.impl.cache.MovieRatingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {
    private static final EnrichmentType ENRICHMENT_MODE = EnrichmentType.RATING;

    @Autowired
    private MovieRatingCache ratingCache;

    @Override
    public boolean isValidFor(Set<EnrichmentType> requestedModes) {
        return requestedModes.contains(ENRICHMENT_MODE);
    }

    @Override
    public void enrichMovies(List<Movie> movies) {
        if (movies.size() == 0) {
            ratingCache.enrichMovies(movies);
        }
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
