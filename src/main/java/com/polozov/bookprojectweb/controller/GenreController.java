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
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genre")
    public String genreList(Model model) {
        List<Genre> genres = genreService.getAll();
        model.addAttribute("genres", genres);
        return "genre";
    }

    @GetMapping("/genre/edit/{id}")
    public String editGenre(@PathVariable("id") int id, Model model) {
        Genre genre;
        if (id != 0) {
            genre = genreService.getById(id).orElseThrow(ObjectNotFoundException::new);
        } else {
           genre = new Genre(0, "");
        }
        model.addAttribute("genre", genre);
        return "genre-edit";
    }

    @PostMapping("/genre/edit")
    public String saveGenre(Genre genre) {
        genreService.update(genre);
        return "redirect:/genre";
    }

    @DeleteMapping("/genre/{id}")
    public String deleteGenre(@PathVariable("id") long id) {
        genreService.deleteById(id);
        return "redirect:/genre";
    }
}
