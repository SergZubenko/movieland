package com.sergzubenko.movieland.web.controller;


import com.sergzubenko.movieland.service.api.MovieService;
import com.sergzubenko.movieland.web.dto.movie.MovieCompactViewDto;
import com.sergzubenko.movieland.web.dto.movie.MovieRandomViewDto;
import com.sergzubenko.movieland.web.dto.movie.MovieSingleViewDto;
import com.sergzubenko.movieland.web.mapper.EntityDtoReflectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/movie", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MovieController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieService movieService;

    @RequestMapping(path = "/{movieId}", method = RequestMethod.GET)
    public MovieSingleViewDto getById(@PathVariable("movieId") Integer movieId,
                                      @RequestParam(name = "currency", required = false) String currency) {
        logger.debug("Invoked getById controller");
        return EntityDtoReflectionMapper.map(movieService.getById(movieId, currency), MovieSingleViewDto.class);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<MovieCompactViewDto> getAllMovies(
            @RequestParam(required = false) LinkedHashMap<String, String> params) {
        logger.debug("Invoked getAllMovies controller");
        return EntityDtoReflectionMapper.mapList(movieService.getAll(params), MovieCompactViewDto.class);
    }

    @RequestMapping(path = "/random", method = RequestMethod.GET)
    public List<MovieRandomViewDto> getRandomMovies() {
        logger.debug("Invoked getRandomMovies controller");
        return EntityDtoReflectionMapper.mapList(movieService.getRandomMovies(), MovieRandomViewDto.class);
    }

    @RequestMapping(path = "/genre/{genreId}", method = RequestMethod.GET)
    public List<MovieCompactViewDto> getMoviesByGenre(@PathVariable Integer genreId, @RequestParam(required = false) LinkedHashMap<String, String> params) {
        logger.debug("Invoked getMoviesByGenre controller");
        return EntityDtoReflectionMapper.mapList(movieService.getMoviesByGenre(genreId, params), MovieCompactViewDto.class);
    }
}
