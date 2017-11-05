package com.sergzubenko.movieland.web.dto;

import com.sergzubenko.movieland.entity.Movie;

import java.util.List;
import java.util.stream.Collectors;

public class MovieDtoMapper {

    private static MovieCompactDto mapToDTOCompact(Movie movie){
        MovieCompactDto dto = new MovieCompactDto();
        dto.setId(movie.getId());
        dto.setNameNative(movie.getNameNative());
        dto.setYearOfRelease(movie.getYearOfRelease());
        dto.setPicturePath(movie.getPicturePath());
        dto.setRating(movie.getRating());
        dto.setPrice(movie.getPrice());
        dto.setNameRussian(movie.getNameRussian());
        return dto;
    }

    public static List<MovieCompactDto> mapToDTOCompact(List<Movie> movies){
        return movies.stream().map(MovieDtoMapper::mapToDTOCompact).collect(Collectors.toList());
    }
}
