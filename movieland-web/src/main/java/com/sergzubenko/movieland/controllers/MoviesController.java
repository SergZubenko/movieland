package com.sergzubenko.movieland.controllers;


import com.sergzubenko.movieland.dto.MovieDtoMapper;
import com.sergzubenko.movieland.serialize.JSONConverter;
import com.sergzubenko.movieland.service.api.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@SuppressWarnings({"SpringAutowiredFieldsWarningInspection", "SpringJavaAutowiringInspection"})
@RestController
@RequestMapping(path = "/movie", produces = "application/json; charset=utf-8")
public class MoviesController {

    @Autowired
    private MovieService movieService;

    @Value("${mvc.view.movie.allMovies.excludeColumns}")
    String exColumnsAllMovies;

    @Autowired
    private JSONConverter jsonConverter;

    @RequestMapping(path = "")
    public String getAllMovies(@RequestParam(required = false) LinkedHashMap<String, String> params) {
        System.out.println("!!!!!!!!!!!!!!!!!!" + exColumnsAllMovies);
        return jsonConverter.getJson(MovieDtoMapper.map(movieService.getAllMovies(params)), exColumnsAllMovies);
    }

    @RequestMapping(path = "/random")
    public String getRandomMovies() {
        return jsonConverter.getJson(MovieDtoMapper.map(movieService.getRandomMovies()), "");
    }

    @RequestMapping(path = "/genre/{genreId}")
    public String getByGenre(@PathVariable Integer genreId) {
        return jsonConverter.getJson(MovieDtoMapper.map(movieService.getByGenre(genreId)), exColumnsAllMovies);
    }

}
