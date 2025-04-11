package com.example.bookshop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {

    @GetMapping({"/index", "/index.html"})
    public String home(){
        return "index";
    }

    @GetMapping({"/widgets", "/widgets.html"})
    public String widget(){
        return "widgets";
    }

}

