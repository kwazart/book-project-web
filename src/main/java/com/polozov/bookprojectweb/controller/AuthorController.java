package com.polozov.bookprojectweb.controller;

import com.polozov.bookprojectweb.domain.Author;
import com.polozov.bookprojectweb.domain.Genre;
import com.polozov.bookprojectweb.exception.ObjectNotFoundException;
import com.polozov.bookprojectweb.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public String authorList(Model model) {
        List<Author> authors = authorService.getAll();
        model.addAttribute("authors", authors);
        return "author";
    }

    @GetMapping("/edit")
    public String editAuthor(@RequestParam(value = "id", defaultValue = "0") int id, Model model) {
        Author author;
        if (id != 0) {
            author = authorService.getById(id).orElseThrow(ObjectNotFoundException::new);
        } else {
            author = new Author(0, "");
        }
        model.addAttribute("author", author);
        return "author-edit";
    }

    @PostMapping("/edit")
    public String saveAuthor(Author author) {
        authorService.update(author);
        return "redirect:/author";
    }

    @GetMapping("/remove")
    public String deleteAuthor(@RequestParam("id") long id) {
        authorService.deleteById(id);
        return "redirect:/author";
    }
}
