package com.sergzubenko.web.controllers;

import com.sergzubenko.data.repository.Movie;
import com.sergzubenko.data.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by sergz on 24.10.2017.
 */
@RestController
@RequestMapping(path = "/v1/movie")
public class MoviesController {

    @Autowired
    MovieService movieService;


    @RequestMapping(path = "")
    public List<Movie> getAllMovies(){
        return  movieService.getAllMovies();
    }

    @RequestMapping(path = "/random")
    public List<Movie> getRandomMovies(){
        return  movieService.getRandomMovies();
    }

    @RequestMapping(path = "/genre/{genreId}")
    public List<Movie> getByGenre(@PathVariable Integer genreId){
        return  movieService.getByGenre(genreId);
    }


}
