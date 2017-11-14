package com.sergzubenko.movieland.service.impl;

import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.GenreDao;
import com.sergzubenko.movieland.service.api.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreDao genreDao;

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    public void enrichMovies(List<Movie> movies) {
        genreDao.enrichMovies(movies);
    }

    @Override
    public void enrichMovie(Movie movie) {
        genreDao.enrichMovies(Collections.singletonList(movie));
    }
}
