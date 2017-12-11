package com.sergzubenko.movieland.service.api.enrichment;

import com.sergzubenko.movieland.entity.Movie;

import java.util.List;

public interface MovieEnrichmentService {

    void enrichMovie(Movie movie, EnrichmentType...mode);

    void enrichMovies(List<Movie> movies, EnrichmentType...mode);

}
