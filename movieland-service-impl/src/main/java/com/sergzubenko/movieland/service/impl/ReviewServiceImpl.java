package com.sergzubenko.movieland.service.impl;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.entity.Review;
import com.sergzubenko.movieland.persistance.api.ReviewUserDao;
import com.sergzubenko.movieland.service.api.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewUserDao reviewUserDao;

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
