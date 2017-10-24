package com.sergzubenko.data.service;

import com.sergzubenko.data.dao.MovieDao;
import com.sergzubenko.data.repository.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sergz on 24.10.2017.
 */
@Service
@Transactional
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieDao movieDao;

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
}
