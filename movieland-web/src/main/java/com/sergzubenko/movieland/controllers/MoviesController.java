package com.sergzubenko.movieland.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.sergzubenko.movieland.dto.MovieDtoMapper;
import com.sergzubenko.movieland.service.api.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@SuppressWarnings({"SpringAutowiredFieldsWarningInspection", "SpringJavaAutowiringInspection"})
@RestController
@RequestMapping(path = "/movie",produces = "application/json; charset=utf-8")
public class MoviesController {

    @Autowired
    private MovieService movieService;

    public String getJson(Object object, boolean applyFilter) {

        SimpleBeanPropertyFilter filter;

        if (applyFilter) {
            filter = SimpleBeanPropertyFilter.serializeAllExcept("genres", "countries","description");
        } else {
            filter = SimpleBeanPropertyFilter.serializeAll();
        }

        FilterProvider filters = new SimpleFilterProvider().addFilter("allMoviesFilter", filter);
        try {
            return (new ObjectMapper())
                    .writer(filters)
                    .writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(path = "")
    public String getAllMovies(@RequestParam(required = false) LinkedHashMap<String, String> params) {
        return getJson(MovieDtoMapper.map(movieService.getAllMovies(params)), true);
}

    @RequestMapping(path = "/random")
    public String getRandomMovies() {
        return getJson(MovieDtoMapper.map(movieService.getRandomMovies()), false);
    }

    @RequestMapping(path = "/genre/{genreId}")
    public String getByGenre(@PathVariable Integer genreId) {
        return getJson(MovieDtoMapper.map(movieService.getByGenre(genreId)), true);
    }

}
