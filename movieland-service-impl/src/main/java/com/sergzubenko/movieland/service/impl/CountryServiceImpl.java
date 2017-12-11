package com.sergzubenko.movieland.service.impl;

import com.sergzubenko.movieland.entity.Country;
import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.CountryDao;
import com.sergzubenko.movieland.service.api.CountryService;
import com.sergzubenko.movieland.service.api.enrichment.EnrichmentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CountryServiceImpl implements CountryService {
    private static final EnrichmentType ENRICHMENT_MODE = EnrichmentType.COUNTRY;

    @Autowired
    private CountryDao countryDao;

    @Override
    public List<Country> getAll() {
        return countryDao.getAll();
    }

    @Override
    public boolean isValidFor(Set<EnrichmentType> requestedModes) {
        return requestedModes.contains(ENRICHMENT_MODE);
    }

    @Override
    public void enrichMovies(List<Movie> movies) {
        countryDao.enrichMovies(movies);
    }

    @Override
    public void enrichMovie(Movie movie) {
        countryDao.enrichMovie(movie);
    }

    @Override
    public void persistMovieCountries(Movie movie) {
        countryDao.persistCountriesForMovie(movie);
    }

}
