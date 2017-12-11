package com.sergzubenko.movieland.service.impl;

import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.GenreDao;
import com.sergzubenko.movieland.service.api.GenreService;
import com.sergzubenko.movieland.service.api.enrichment.EnrichmentType;
import com.sergzubenko.movieland.service.impl.cache.GenreCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GenreServiceImpl implements GenreService {
    private static final EnrichmentType ENRICHMENT_MODE = EnrichmentType.GENRE;

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private GenreCache genreCache;

    @Override
    public List<Genre> getAll() {
        return genreCache.getAll();
    }

    @Override
    public boolean isValidFor(Set<EnrichmentType> requestedModes) {
        return requestedModes.contains(ENRICHMENT_MODE);
    }

    @Override
    public void enrichMovies(List<Movie> movies) {
        genreDao.enrichMovies(movies);
    }

    @Override
    public void enrichMovie(Movie movie) {
        if (movie.getGenres() != null) {
            movie.getGenres().clear();
        }
        genreDao.enrichMovie(movie);
    }

    @Override
    public void persistMovieGenres(Movie movie) {
        genreDao.persistGenresForMovie(movie);
    }

}
