package com.sergzubenko.movieland.service.impl;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.MovieDao;
import com.sergzubenko.movieland.service.api.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDao movieDao;

    @Override
    public Movie getMovieById(Integer id) {
        return movieDao.getMovieById(id);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieDao.getAllMovies();
    }

    @Override
    public List<Movie> getRandomMovies() {
        return movieDao.getRandomMovies();
    }

    @Override
    public List<Movie> getByGenre(Integer genreId) {
        return movieDao.getByGenre(genreId);
    }

    @Override
    public List<Movie> getAllMovies(Map<String, String> params) {
        return movieDao.getAllMovies(params);
    }
}
