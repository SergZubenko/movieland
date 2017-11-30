package com.sergzubenko.movieland.web.controller;


import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.entity.UserRole;
import com.sergzubenko.movieland.service.api.MovieService;
import com.sergzubenko.movieland.service.api.security.UserPrincipal;
import com.sergzubenko.movieland.web.dto.movie.MovieCompactViewDto;
import com.sergzubenko.movieland.web.dto.movie.MoviePersistenceDto;
import com.sergzubenko.movieland.web.dto.movie.MovieRandomViewDto;
import com.sergzubenko.movieland.web.dto.movie.MovieSingleViewDto;
import com.sergzubenko.movieland.web.mapper.EntityDtoReflectionMapper;
import com.sergzubenko.movieland.web.security.annotation.HasRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

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

    @RequestMapping(method = POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @HasRole(UserRole.ADMIN)
    public Movie addMovie(@RequestBody MoviePersistenceDto movieDto, UserPrincipal principal) {

        if (movieDto.getId() != null)
        {
            logger.error("User {} tried to add  already existing movie {}", principal.getUser(), movieDto);
            throw new IllegalArgumentException("Attempt to add existing movie");
        }
        return persistMovie(movieDto, null);
    }

    @RequestMapping(path = "/{movieId}", method = PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @HasRole(UserRole.ADMIN)
    public void updateMovie(@PathVariable Integer movieId, @RequestBody MoviePersistenceDto movieDto, UserPrincipal principal) {
        persistMovie(movieDto, movieId);
    }

    private Movie persistMovie(MoviePersistenceDto movieDto, Integer movieId){
        logger.info("Sending request to persist movie");
        long startTime = System.currentTimeMillis();

        Movie movie = EntityDtoReflectionMapper.map(movieDto, Movie.class);
        movie.setId(movieId);
        movieService.persist(movie);

        logger.info("Movie {} was stored. It took {} ms", movie, System.currentTimeMillis() - startTime);
         return movie;
    }


}
