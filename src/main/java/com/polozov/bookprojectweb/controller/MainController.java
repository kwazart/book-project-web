package com.polozov.bookprojectweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/index", "/", "/index.html"})
public class MainController {

    @GetMapping
    public String getMainPage() {
        return "index";
    }
}
