package com.sergzubenko.data.service;

import com.sergzubenko.data.repository.Genre;

import java.util.List;

/**
 * Created by sergz on 24.10.2017.
 */
public interface GenreService {
    List<Genre> getGenres();
}
