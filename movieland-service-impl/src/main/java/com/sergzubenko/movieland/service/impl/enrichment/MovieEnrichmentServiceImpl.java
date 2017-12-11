package com.sergzubenko.movieland.service.impl.enrichment;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.service.api.enrichment.EnrichmentType;
import com.sergzubenko.movieland.service.api.enrichment.MovieEnricher;
import com.sergzubenko.movieland.service.api.enrichment.MovieEnrichmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MovieEnrichmentServiceImpl implements MovieEnrichmentService {

    @Autowired
    private List<MovieEnricher> enrichers;

    @Override
    public void enrichMovie(Movie movie, EnrichmentType... requestedTypes) {
        Set<EnrichmentType> types = prepareRequestedModes(requestedTypes);
        enrichers.parallelStream()
                .filter(enricher -> enricher.isValidFor(types))
                .forEach(enricher -> enricher.enrichMovie(movie));
    }

    @Override
    public void enrichMovies(List<Movie> movies, EnrichmentType... requestedTypes) {
        Set<EnrichmentType> types = prepareRequestedModes(requestedTypes);
        enrichers.parallelStream()
                .filter(enricher -> enricher.isValidFor(types))
                .forEach(enricher -> enricher.enrichMovies(movies));
    }

    private Set<EnrichmentType> prepareRequestedModes(EnrichmentType[] requestedTypes) {
        Set<EnrichmentType> typeSet = new HashSet<>();
        typeSet.addAll(Arrays.asList(requestedTypes));
        return typeSet;
    }
}
