package com.sergzubenko.movieland.controllers;

import com.sergzubenko.movieland.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sergzubenko.movieland.service.api.GenreService;

import java.util.List;

@Controller
@RequestMapping(value = "/genre")
@SuppressWarnings({"SpringAutowiredFieldsWarningInspection","SpringJavaAutowiringInspection"})
public class GenreController {

    @Autowired
    private GenreService genreService;

    @RequestMapping(value = "")
    @ResponseBody
    public List<Genre> getAll(){
        return genreService.getGenres();
    }

}
