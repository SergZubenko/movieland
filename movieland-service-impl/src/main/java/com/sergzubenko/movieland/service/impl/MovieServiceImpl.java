package com.sergzubenko.movieland.service.impl;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.MovieDao;
import com.sergzubenko.movieland.service.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.ROUND_UP;

@Service
@Transactional
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
    public List<Movie> getAll(Map<String, String> params) {
        return movieDao.getAll(params);
    }

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
    public Movie getById(Integer id, String currency) {
        Movie movie = movieDao.getMovieById(id);
        if (currency != null) {
            double rate = currencyService.getRate(currency);
            if (rate != 0) {
                movie.setPrice((new BigDecimal(movie.getPrice())).divide(new BigDecimal(rate), 2, ROUND_UP).doubleValue());
            }
            else
            {
                movie.setPrice(0d);
            }
        }
        reviewService.enrichMovie(movie);
        countryService.enrichMovie(movie);
        genreService.enrichMovie(movie);
        return movie;
    }

    @Override
    public Movie getById(Integer id) {
        return getById(id, null);
    }

    @Override
    public void persist(Movie movie) {
        movieDao.persist(movie);
        genreService.persistMovieGenres(movie);
        countryService.persistMovieCountries(movie);

        //prepare to enrichment
        if (movie.getCountries() != null) {
            movie.getCountries().clear();
        }
        if (movie.getGenres() != null) {
            movie.getGenres().clear();
        }

        countryService.enrichMovie(movie);
        genreService.enrichMovie(movie);
    }
}
