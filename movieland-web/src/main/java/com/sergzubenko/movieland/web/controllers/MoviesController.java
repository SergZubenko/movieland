package com.sergzubenko.movieland.web.controllers;


import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.service.api.MovieService;
import com.sergzubenko.movieland.web.dto.MovieCompactDto;
import com.sergzubenko.movieland.web.dto.MovieDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings({"SpringAutowiredFieldsWarningInspection", "SpringJavaAutowiringInspection"})
@RestController
@RequestMapping(path = "/movie", produces = "application/json; charset=utf-8")
public class MoviesController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(method = RequestMethod.GET)
    public List<MovieCompactDto>  getAllMovies(@RequestParam(required = false) LinkedHashMap<String, String> params) {
        return MovieDtoMapper.mapToDTOCompact(movieService.getAllMovies(params));
    }

    @RequestMapping(path = "/random",method = RequestMethod.GET)
    public List<Movie>  getRandomMovies() {
        return movieService.getRandomMovies();
    }

    @RequestMapping(path = "/genre/{genreId}",method = RequestMethod.GET)
    public List<MovieCompactDto> getByGenre(@PathVariable Integer genreId, @RequestParam(required = false) LinkedHashMap<String, String> params) {
        return MovieDtoMapper.mapToDTOCompact(movieService.getByGenre(genreId, params));
    }

}
