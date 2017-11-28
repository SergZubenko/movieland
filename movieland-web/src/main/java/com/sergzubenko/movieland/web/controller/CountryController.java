package com.sergzubenko.movieland.web.controller;

import com.sergzubenko.movieland.entity.Country;
import com.sergzubenko.movieland.entity.UserRole;
import com.sergzubenko.movieland.service.api.CountryService;
import com.sergzubenko.movieland.web.security.annotation.HasRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping(path = "/country", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@HasRole(UserRole.USER)
public class CountryController {

    @Autowired
    private CountryService countryService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Country> getCountries() {
        return countryService.getAll();
    }

}
