package com.sergzubenko.movieland.service.impl;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.MovieDao;
import com.sergzubenko.movieland.service.api.*;
import com.sergzubenko.movieland.service.api.cache.MovieCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDao movieDao;

    @Autowired
    private  MovieCache cache;

    @Autowired
    private CountryService countryService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private MovieRatingService ratingService;

    @Override
    public List<Movie> getAll(Map<String, String> params) {
        List<Movie> movies = movieDao.getAll(params);
        ratingService.enrichMovies(movies);
        return movies;
    }

    @Override
    public List<Movie> getRandomMovies() {
        List<Movie> movies = movieDao.getRandomMovies();
        countryService.enrichMovies(movies);
        genreService.enrichMovies(movies);
        ratingService.enrichMovies(movies);
        return movies;
    }

    @Override
    public List<Movie> getMoviesByGenre(Integer genreId, Map<String, String> params) {
        List<Movie> movies = movieDao.getMoviesByGenre(genreId, params);
        ratingService.enrichMovies(movies);
        return movies;
    }

    @Override
    public Movie getById(Integer id, String currency) {
        Movie movie = getById(id);
        currencyService.setMoviePrice(movie, currency);
        return movie;
    }

    @Override
    public Movie getById(Integer id) {
        Movie movie = cache.getFromCache(id);
        reviewService.enrichMovie(movie);
        countryService.enrichMovie(movie);
        genreService.enrichMovie(movie);
        ratingService.enrichMovie(movie);
        return movie;
    }

    @Override
    public void persist(Movie movie) {
        movieDao.persist(movie);
        genreService.persistMovieGenres(movie);
        countryService.persistMovieCountries(movie);
        cache.addToCache(movie);
        countryService.enrichMovie(movie);
        genreService.enrichMovie(movie);
    }
}
