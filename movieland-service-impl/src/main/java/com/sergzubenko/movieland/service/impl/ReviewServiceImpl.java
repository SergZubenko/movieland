package com.sergzubenko.movieland.service.impl;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.ReviewUserDao;
import com.sergzubenko.movieland.service.api.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewUserDao reviewUserDao;

    @Override
    public void enrichMovies(List<Movie> movies) {
        reviewUserDao.enrichMovies(movies);
    }

    @Override
    public void enrichMovie(Movie movie) {
        reviewUserDao.enrichMovies(Collections.singletonList(movie));
    }
}
