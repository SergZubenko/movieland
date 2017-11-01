package com.sergzubenko.movieland.dto;

import com.sergzubenko.movieland.entity.Movie;

import java.util.List;
import java.util.stream.Collectors;

public class MovieDtoMapper {

    public static MovieDto map(Movie movie){
        MovieDto dto = new MovieDto();
        dto.setId(movie.getId());
        dto.setNameNative(movie.getNameNative());
        dto.setYearOfRelease(movie.getYearOfRelease());
        dto.setDescription(movie.getDescription());
        dto.setPicturePath(movie.getPicturePath());
        dto.setRating(movie.getRating());
        dto.setPrice(movie.getPrice());
        dto.setCountries(movie.getCountries());
        dto.setGenres(movie.getGenres());
        dto.setNameRussian(movie.getNameRussian());
        return dto;
    }

    public static List<MovieDto> map(List<Movie> movies){
        return movies.stream().map(MovieDtoMapper::map).collect(Collectors.toList());
    }

}
