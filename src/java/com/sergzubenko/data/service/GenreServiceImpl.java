package com.sergzubenko.data.service;

import com.sergzubenko.data.dao.GenreDao;
import com.sergzubenko.data.repository.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sergz on 24.10.2017.
 */
@Service
public class GenreServiceImpl implements GenreService{

    @Autowired
    @Qualifier("cached")
    private GenreDao genreDao;

    @Override
    public List<Genre> getGenres() {
        return genreDao.getGenres();
    }
}
