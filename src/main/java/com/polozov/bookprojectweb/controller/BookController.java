package com.polozov.bookprojectweb.controller;

import com.polozov.bookprojectweb.domain.Author;
import com.polozov.bookprojectweb.domain.Book;
import com.polozov.bookprojectweb.domain.Genre;
import com.polozov.bookprojectweb.exception.ObjectNotFoundException;
import com.polozov.bookprojectweb.service.AuthorService;
import com.polozov.bookprojectweb.service.BookService;
import com.polozov.bookprojectweb.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping("/book")
    public String bookList(Model model) {
        List<Book> books = bookService.getAll();
        model.addAttribute("books", books);
        return "book";
    }

    @GetMapping("/book/edit/{id}")
    public String editBook(@PathVariable("id") int id, Model model) {
        Book book;
        if (id != 0) {
            book = bookService.getById(id).orElseThrow(ObjectNotFoundException::new);
        } else {
            book = new Book(0, "", new Author(0, ""), new Genre(0, ""));
        }
        model.addAttribute("book", book);
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("authors", authorService.getAll());
        return "book-edit";
    }

    @PostMapping("/book/edit")
    public String saveBook(Book book) {
        bookService.update(book);
        return "redirect:/book";
    }

    @DeleteMapping("/book/{id}")
    public String deleteGenre(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return "redirect:/book";
    }
}
