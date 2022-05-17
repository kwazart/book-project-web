package com.polozov.bookprojectweb.controller;

import com.polozov.bookprojectweb.domain.Author;
import com.polozov.bookprojectweb.exception.ObjectNotFoundException;
import com.polozov.bookprojectweb.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/author")
    public String authorList(Model model) {
        List<Author> authors = authorService.getAll();
        model.addAttribute("authors", authors);
        return "author";
    }

    @GetMapping("/author/edit/{id}")
    public String editAuthor(@PathVariable("id") int id, Model model) {
        Author author;
        if (id != 0) {
            author = authorService.getById(id).orElseThrow(ObjectNotFoundException::new);
        } else {
            author = new Author(0, "");
        }
        model.addAttribute("author", author);
        return "author-edit";
    }

    @PostMapping("/author/edit")
    public String saveAuthor(Author author) {
        authorService.update(author);
        return "redirect:/author";
    }

    @DeleteMapping("/author/{id}")
    public String deleteAuthor(@PathVariable("id") long id) {
        authorService.deleteById(id);
        return "redirect:/author";
    }
}
