package com.sergzubenko.movieland.service.api;

import com.sergzubenko.movieland.entity.Review;
import com.sergzubenko.movieland.service.api.enrichment.MovieEnricher;

public interface ReviewService  extends MovieEnricher {

    void persist(Review review);
}
