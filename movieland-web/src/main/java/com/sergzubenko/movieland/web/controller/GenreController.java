package com.sergzubenko.movieland.web.controller;

import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.service.api.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path="/genre",  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GenreController {

    @Autowired
    private GenreService genreService;

    @RequestMapping
    public List<Genre> getGenres() {
        return genreService.getAll();
    }
}
