package com.sergzubenko.movieland.service.api.enrichment;

import com.sergzubenko.movieland.entity.Movie;

import java.util.List;
import java.util.Set;

public interface MovieEnricher {

    boolean isValidFor(Set<EnrichmentType> requestedModes);

    void enrichMovies(List<Movie> movies);

    void enrichMovie(Movie movie);
}
