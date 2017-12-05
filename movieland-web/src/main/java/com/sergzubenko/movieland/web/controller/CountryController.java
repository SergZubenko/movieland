package com.sergzubenko.movieland.web.controller;

import com.sergzubenko.movieland.entity.Country;
import com.sergzubenko.movieland.service.api.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/country", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CountryController {

    @Autowired
    private CountryService countryService;

    @RequestMapping
    public List<Country> getCountries() {
        return countryService.getAll();
    }
}
