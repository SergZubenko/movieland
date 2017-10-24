package com.sergzubenko.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sergz on 24.10.2017.
 */
@Controller
@RequestMapping(value = "/")
public class WelcomeController {

    @RequestMapping(value = "/")
    public String welcome(){
        return "index";
    }

}
