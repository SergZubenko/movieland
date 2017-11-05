package com.sergzubenko.movieland.service.impl;

import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.persistence.jdbc.CachedGenreDao;
import com.sergzubenko.movieland.service.api.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private CachedGenreDao genreCachedDao;

    @Override
    public List<Genre> getGenres() {
        return genreCachedDao.getGenres();
    }
}
