package com.sergzubenko.movieland.service.impl;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.MovieDao;
import com.sergzubenko.movieland.service.api.CountryService;
import com.sergzubenko.movieland.service.api.CurrencyService;
import com.sergzubenko.movieland.service.api.GenreService;
import com.sergzubenko.movieland.service.api.MovieService;
import com.sergzubenko.movieland.service.api.cache.MovieCache;
import com.sergzubenko.movieland.service.api.enrichment.MovieEnrichmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.sergzubenko.movieland.service.api.enrichment.EnrichmentType.*;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDao movieDao;

    @Autowired
    private MovieCache cache;


    @Autowired
    private CountryService countryService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private
    MovieEnrichmentService enrichmentService;

    @Autowired
    private CurrencyService currencyService;

    @Override
    public List<Movie> getAll(Map<String, String> params) {
        List<Movie> movies = movieDao.getAll(params);
        enrichmentService.enrichMovies(movies, RATING);
        return movies;
    }

    @Override
    public List<Movie> getRandomMovies() {
        List<Movie> movies = movieDao.getRandomMovies();
        enrichmentService.enrichMovies(movies, RATING, COUNTRY, GENRE);
        return movies;
    }

    @Override
    public List<Movie> getMoviesByGenre(Integer genreId, Map<String, String> params) {
        List<Movie> movies = movieDao.getMoviesByGenre(genreId, params);
        enrichmentService.enrichMovies(movies, RATING);
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
        enrichmentService.enrichMovie(movie, RATING, REVIEW, COUNTRY, GENRE);
        return movie;
    }

    @Override
    public void persist(Movie movie) {
        movieDao.persist(movie);
        genreService.persistMovieGenres(movie);
        countryService.persistMovieCountries(movie);
        cache.addToCache(movie);
        enrichmentService.enrichMovie(movie, COUNTRY, GENRE);
    }
}
