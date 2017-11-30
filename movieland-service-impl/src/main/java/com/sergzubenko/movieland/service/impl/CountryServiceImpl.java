package com.sergzubenko.movieland.service.impl;

import com.sergzubenko.movieland.entity.Country;
import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.CountryDao;
import com.sergzubenko.movieland.service.api.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryDao countryDao;

    @Override
    public List<Country> getAll() {
        return countryDao.getAll();
    }

    @Override
    public void enrichMovies(List<Movie> movies) {
        countryDao.enrichMovies(movies);
    }

    @Override
    public void enrichMovie(Movie movie) {
        countryDao.enrichMovies(Collections.singletonList(movie));
    }

    @Override
    public void persistMovieCountries(Movie movie) {
        countryDao.persistMovieCountries(movie);
    }

}
