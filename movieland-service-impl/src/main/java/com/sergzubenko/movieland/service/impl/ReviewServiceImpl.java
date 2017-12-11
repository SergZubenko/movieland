package com.sergzubenko.movieland.service.impl;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.entity.Review;
import com.sergzubenko.movieland.persistance.api.ReviewUserDao;
import com.sergzubenko.movieland.service.api.ReviewService;
import com.sergzubenko.movieland.service.api.enrichment.EnrichmentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private static final EnrichmentType ENRICHMENT_MODE = EnrichmentType.REVIEW;

    @Autowired
    private ReviewUserDao reviewUserDao;

    @Override
    public boolean isValidFor(Set<EnrichmentType> requestedModes) {
        return requestedModes.contains(ENRICHMENT_MODE);
    }
    @Override
    public void enrichMovies(List<Movie> movies) {
        reviewUserDao.enrichMovies(movies);
    }

    @Override
    public void enrichMovie(Movie movie) {
        reviewUserDao.enrichMovie(movie);
    }

    @Override
    public void persist(Review review) {
        reviewUserDao.save(review);
    }
}
