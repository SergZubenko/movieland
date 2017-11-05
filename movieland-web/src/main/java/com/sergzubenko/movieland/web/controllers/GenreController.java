package com.sergzubenko.movieland.web.controllers;

import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.service.api.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/genre")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Genre> getAll() {
        return genreService.getGenres();
    }

}
