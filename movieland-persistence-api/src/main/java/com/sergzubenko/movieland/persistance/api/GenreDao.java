package com.sergzubenko.movieland.persistance.api;

import com.sergzubenko.movieland.entity.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> getGenres();
}