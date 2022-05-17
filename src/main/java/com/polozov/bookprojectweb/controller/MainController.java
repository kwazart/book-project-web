package com.polozov.bookprojectweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping({"/index", "/", "/index.html"})
    public String getMainPage() {
        return "index";
    }
}
