package com.sergzubenko.movieland.service.api;

import com.sergzubenko.movieland.entity.Movie;

public interface CurrencyService {

    void setMoviePrice(Movie movie, String currency);


}

