package com.sergzubenko.movieland.web.dto;

import com.sergzubenko.movieland.entity.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieDtoMapper {

    private static MovieCompactViewDto mapToCompactViewDto(Movie movie){
        MovieCompactViewDto dto = new MovieCompactViewDto();
        dto.setId(movie.getId());
        dto.setNameNative(movie.getNameNative());
        dto.setYearOfRelease(movie.getYearOfRelease());
        dto.setPicturePath(movie.getPicturePath());
        dto.setRating(movie.getRating());
        dto.setPrice(movie.getPrice());
        dto.setNameRussian(movie.getNameRussian());
        return dto;
    }

    public static List<MovieCompactViewDto> mapToCompactViewDto(List<Movie> movies){
        return movies.stream().map(MovieDtoMapper::mapToCompactViewDto).collect(Collectors.toList());
    }

    private static MovieRandomViewDto mapToRandomViewDto(Movie movie){
        MovieRandomViewDto dto = new MovieRandomViewDto();
        dto.setId(movie.getId());
        dto.setNameNative(movie.getNameNative());
        dto.setYearOfRelease(movie.getYearOfRelease());
        dto.setPicturePath(movie.getPicturePath());
        dto.setRating(movie.getRating());
        dto.setDescription(movie.getDescription());
        dto.setPrice(movie.getPrice());
        dto.setNameRussian(movie.getNameRussian());
        dto.setCountries(new ArrayList<>(movie.getCountries()));
        dto.setGenres(new ArrayList<>(movie.getGenres()));
        return dto;
    }

    public static List<MovieRandomViewDto> mapToRandomViewDto(List<Movie> movies){
        return movies.stream().map(MovieDtoMapper::mapToRandomViewDto).collect(Collectors.toList());
    }
}
