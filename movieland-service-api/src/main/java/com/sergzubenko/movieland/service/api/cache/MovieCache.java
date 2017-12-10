package com.sergzubenko.movieland.service.api.cache;

import com.sergzubenko.movieland.entity.Movie;

public interface MovieCache {
    void addToCache(Movie movie);

    Movie getFromCache(Integer id);

}
