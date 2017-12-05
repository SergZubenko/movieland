package com.sergzubenko.movieland.web.controller;


import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.entity.UserRole;
import com.sergzubenko.movieland.service.api.MovieService;
import com.sergzubenko.movieland.service.api.security.UserPrincipal;
import com.sergzubenko.movieland.web.dto.movie.MovieCompactViewDto;
import com.sergzubenko.movieland.web.dto.movie.MoviePersistenceDto;
import com.sergzubenko.movieland.web.dto.movie.MovieRandomViewDto;
import com.sergzubenko.movieland.web.dto.movie.MovieSingleViewDto;
import com.sergzubenko.movieland.web.mapper.ObjectTransformer;
import com.sergzubenko.movieland.web.security.annotation.HasRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@CrossOrigin
@RequestMapping(path = "/movie", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MovieController {

    private static final Logger log = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;

    @RequestMapping(method = RequestMethod.GET)
    public List<MovieCompactViewDto> getAllMovies(
            @RequestParam(required = false) LinkedHashMap<String, String> params) {
        log.debug("Invoked getAllMovies controller");
        return ObjectTransformer.transformList(movieService.getAll(params), MovieCompactViewDto.class);
    }

    @RequestMapping(path = "/{movieId}", method = RequestMethod.GET)
    public MovieSingleViewDto getById(@PathVariable("movieId") Integer movieId,
                                      @RequestParam(name = "currency", required = false) String currency) {
        log.debug("Invoked getById controller");
        Movie movie;
        if (currency == null) {
            movie = movieService.getById(movieId);
        } else {
            movie = movieService.getById(movieId, currency);
        }
        return ObjectTransformer.transform(movie, MovieSingleViewDto.class);
    }

    @RequestMapping(path = "/random", method = RequestMethod.GET)
    public List<MovieRandomViewDto> getRandomMovies() {
        log.debug("Invoked getRandomMovies controller");
        return ObjectTransformer.transformList(movieService.getRandomMovies(), MovieRandomViewDto.class);
    }

    @RequestMapping(path = "/genre/{genreId}", method = RequestMethod.GET)
    public List<MovieCompactViewDto> getMoviesByGenre(@PathVariable Integer genreId, @RequestParam(required = false) LinkedHashMap<String, String> params) {
        log.debug("Invoked getMoviesByGenre controller");
        return ObjectTransformer.transformList(movieService.getMoviesByGenre(genreId, params), MovieCompactViewDto.class);
    }

    @RequestMapping(method = POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @HasRole(UserRole.ADMIN)
    public Movie addMovie(@RequestBody MoviePersistenceDto movieDto, UserPrincipal principal) {

        if (movieDto.getId() != null) {
            log.error("User {} tried to add  already existing movie {}", principal.getUser(), movieDto);
            throw new InvalidParameterException("Attempt to add existing movie");
        }

        return persistMovie(movieDto);
    }

    @RequestMapping(path = "/{movieId}", method = PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @HasRole(UserRole.ADMIN)
    public void updateMovie(@PathVariable Integer movieId, @RequestBody MoviePersistenceDto movieDto, UserPrincipal principal) {
        movieDto.setId(movieId);
        persistMovie(movieDto);
    }


    private Movie persistMovie(MoviePersistenceDto movieDto) {

        log.info("Sending request to persist movie");
        long startTime = System.currentTimeMillis();

        Movie movie = ObjectTransformer.transform(movieDto, Movie.class);
        movieService.persist(movie);

        log.info("Movie {} was stored. It took {} ms", movie, System.currentTimeMillis() - startTime);
        return movie;
    }


}
