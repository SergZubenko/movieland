package com.sergzubenko.movieland.service.impl;

import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.persistence.jdbc.GenreCachedDao;
import com.sergzubenko.movieland.service.api.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreCachedDao eenreCachedDao;

    @Override
    public List<Genre> getGenres() {
        return eenreCachedDao.getGenres();
    }
}
