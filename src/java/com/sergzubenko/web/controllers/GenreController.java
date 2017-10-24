package com.sergzubenko.web.controllers;

import com.sergzubenko.data.repository.Genre;
import com.sergzubenko.data.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by sergz on 24.10.2017.
 */
@Controller
@RequestMapping(value = "/v1/genre")
public class GenreController {

    @Autowired
    GenreService genreService;

    @RequestMapping(value = "")
    @ResponseBody
    public List<Genre> getAll(){
        return genreService.getGenres();
    }

}
