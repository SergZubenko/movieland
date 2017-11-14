package com.sergzubenko.movieland.web.controller;


import com.sergzubenko.movieland.service.api.MovieService;
import com.sergzubenko.movieland.web.dto.MovieCompactViewDto;
import com.sergzubenko.movieland.web.dto.MovieRandomViewDto;
import com.sergzubenko.movieland.web.dto.MovieSingleViewDto;
import com.sergzubenko.movieland.web.util.EntityDtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings({"SpringAutowiredFieldsWarningInspection", "SpringJavaAutowiringInspection"})
@RestController
@CrossOrigin
@RequestMapping(path = "/movie", produces = "application/json; charset=utf-8")
public class MovieController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieService movieService;

    @RequestMapping(path = "/{movieId}", method = RequestMethod.GET)
    public MovieSingleViewDto getById(@PathVariable("movieId") Integer movieId,
                                      @RequestParam(name = "currency", required = false) String currency) {
        logger.debug("Invoked getById controller");
        return EntityDtoMapper.map(movieService.getById(movieId, currency), MovieSingleViewDto.class);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<MovieCompactViewDto> getAllMovies(
            @RequestParam(required = false) LinkedHashMap<String, String> params) {
        logger.debug("Invoked getAllMovies controller");
        return EntityDtoMapper.mapList(movieService.getAll(params), MovieCompactViewDto.class);
    }

    @RequestMapping(path = "/random", method = RequestMethod.GET)
    public List<MovieRandomViewDto> getRandomMovies() {
        logger.debug("Invoked getRandomMovies controller");
        return EntityDtoMapper.mapList(movieService.getRandomMovies(), MovieRandomViewDto.class);
    }

    @RequestMapping(path = "/genre/{genreId}", method = RequestMethod.GET)
    public List<MovieCompactViewDto> getMoviesByGenre(@PathVariable Integer genreId, @RequestParam(required = false) LinkedHashMap<String, String> params) {
        logger.debug("Invoked getMoviesByGenre controller");
        return EntityDtoMapper.mapList(movieService.getMoviesByGenre(genreId, params), MovieCompactViewDto.class);
    }


}
