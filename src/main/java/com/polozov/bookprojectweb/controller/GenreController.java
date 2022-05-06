package com.polozov.bookprojectweb.controller;

import com.polozov.bookprojectweb.domain.Genre;
import com.polozov.bookprojectweb.exception.ObjectNotFoundException;
import com.polozov.bookprojectweb.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/genre")
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public String genreList(Model model) {
        List<Genre> genres = genreService.getAll();
        model.addAttribute("genres", genres);
        return "genre";
    }

    @GetMapping("/edit")
    public String editGenre(@RequestParam(value = "id", defaultValue = "0") int id, Model model) {
        Genre genre;
        if (id != 0) {
            genre = genreService.getById(id).orElseThrow(ObjectNotFoundException::new);
        } else {
           genre = new Genre(0, "");
        }
        model.addAttribute("genre", genre);
        return "genre-edit";
    }

    @PostMapping("/edit")
    public String saveGenre(Genre genre) {
        genreService.update(genre);
        return "redirect:/genre";
    }

    @GetMapping("/remove")
    public String deleteGenre(@RequestParam("id") long id) {
        genreService.deleteById(id);
        return "redirect:/genre";
    }
}
