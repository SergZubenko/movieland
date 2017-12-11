package com.sergzubenko.movieland.service.api;

import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.service.api.enrichment.MovieEnricher;

import java.util.List;

public interface GenreService extends MovieEnricher {

    List<Genre> getAll();

    void persistMovieGenres(Movie movie);

}
