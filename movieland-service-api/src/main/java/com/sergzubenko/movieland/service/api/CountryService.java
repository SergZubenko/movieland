package com.sergzubenko.movieland.service.api;

import com.sergzubenko.movieland.entity.Country;
import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.service.api.enrichment.MovieEnricher;

import java.util.List;

public interface CountryService  extends MovieEnricher {

    List<Country> getAll();

    void persistMovieCountries(Movie movie);
}
