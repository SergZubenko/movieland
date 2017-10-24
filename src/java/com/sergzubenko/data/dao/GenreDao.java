package com.sergzubenko.data.dao;

import com.sergzubenko.data.repository.Genre;

import java.util.List;

/**
 * Created by sergz on 24.10.2017.
 */
public interface GenreDao {
    List<Genre> getGenres();
}
