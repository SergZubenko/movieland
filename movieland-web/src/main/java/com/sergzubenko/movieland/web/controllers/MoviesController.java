package com.sergzubenko.movieland.web.controllers;


import com.sergzubenko.movieland.service.api.MovieService;
import com.sergzubenko.movieland.web.dto.MovieCompactViewDto;
import com.sergzubenko.movieland.web.dto.MovieDtoMapper;
import com.sergzubenko.movieland.web.dto.MovieRandomViewDto;
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
    public List<MovieCompactViewDto>  getAllMovies(@RequestParam(required = false) LinkedHashMap<String, String> params) {
        return MovieDtoMapper.mapToCompactViewDto(movieService.getMovies(params));
    }

    @RequestMapping(path = "/random",method = RequestMethod.GET)
    public List<MovieRandomViewDto>  getRandomMovies() {
        return MovieDtoMapper.mapToRandomViewDto(movieService.getRandomMovies());
    }

    @RequestMapping(path = "/genre/{genreId}",method = RequestMethod.GET)
    public List<MovieCompactViewDto> getMoviesByGenre(@PathVariable Integer genreId, @RequestParam(required = false) LinkedHashMap<String, String> params) {
        return MovieDtoMapper.mapToCompactViewDto(movieService.getMoviesByGenre(genreId, params));
    }

}
