package com.sergzubenko.movieland.service.api;

import com.sergzubenko.movieland.service.api.enrichment.MovieEnricher;

public interface RatingService extends MovieEnricher {

    void rateMovie(Integer userId, Integer movieId, double rating);

}
