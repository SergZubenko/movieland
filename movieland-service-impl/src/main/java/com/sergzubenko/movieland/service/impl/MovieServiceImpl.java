package com.sergzubenko.movieland.service.impl;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.MovieDao;
import com.sergzubenko.movieland.service.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDao movieDao;

    @Autowired
    private CountryService countryService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CurrencyService currencyService;


    @Override
    public List<Movie> getRandomMovies() {

        List<Movie> movies = movieDao.getRandomMovies();
        countryService.enrichMovies(movies);
        genreService.enrichMovies(movies);
        return movies;
    }

    @Override
    public List<Movie> getMoviesByGenre(Integer genreId, Map<String, String> params) {
        return movieDao.getMoviesByGenre(genreId, params);
    }

    @Override
    public List<Movie> getAll(Map<String, String> params) {
        return movieDao.getMovies(params);
    }

    @Override
    public Movie getById(Integer id, String currency) {
        Movie movie = movieDao.getMovieById(id);
        if (currency !=null) {
            double rate = currencyService.getRate(currency);
            double price = movie.getPrice() / rate;
            movie.setPrice(((double)Math.round(price * 100))/100);
        }

        List<Movie> movies = Collections.singletonList(movie);
        reviewService.enrichMovies(movies);
        countryService.enrichMovies(movies);
        genreService.enrichMovies(movies);
        return movie;
    }

    @Override
    public Movie getById(Integer id) {
        return getById(id, null);
    }
}
