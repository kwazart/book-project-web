package com.polozov.bookprojectweb.controller.rest;

import com.polozov.bookprojectweb.controller.rest.dto.AuthorDto;
import com.polozov.bookprojectweb.controller.rest.dto.BookDto;
import com.polozov.bookprojectweb.domain.Author;
import com.polozov.bookprojectweb.domain.Book;
import com.polozov.bookprojectweb.domain.Genre;
import com.polozov.bookprojectweb.exception.AuthorNotFoundException;
import com.polozov.bookprojectweb.exception.BookNotFoundException;
import com.polozov.bookprojectweb.exception.GenreNotFoundException;
import com.polozov.bookprojectweb.exception.ObjectNotFoundException;
import com.polozov.bookprojectweb.service.AuthorService;
import com.polozov.bookprojectweb.service.BookService;
import com.polozov.bookprojectweb.service.CommentService;
import com.polozov.bookprojectweb.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    @GetMapping("/api/book")
    public List<BookDto> bookList() {
        return bookService.getAll().stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/api/book/{id}")
    public BookDto getBook(@PathVariable("id") long id) {
        return  bookService.getById(id).map(BookDto::toDto).orElseThrow(() -> new BookNotFoundException(id));
    }

    @PostMapping("/api/book")
    public BookDto saveBook(@RequestBody BookDto bookDto) {
        return BookDto.toDto(bookService.add(BookDto.fromDto(bookDto)));
    }

    @PutMapping("/api/book/{id}")
    public BookDto updateBook(@RequestBody BookDto newBookDto, @PathVariable("id") long id) {
        return BookDto.toDto(bookService.getById(id).map(book -> {
            book.setAuthor(authorService
                    .getById(newBookDto.getAuthorDto().getId())
                    .orElseThrow(() -> new AuthorNotFoundException(newBookDto.getAuthorDto().getId()))
            );

            book.setGenre(genreService
                    .getById(newBookDto.getGenreDto().getId())
                    .orElseThrow(() -> new GenreNotFoundException(newBookDto.getGenreDto().getId())));
            book.setName(newBookDto.getBookName());
            return bookService.update(book);
        }).orElseGet(() -> {
            Book newBook = BookDto.fromDto(newBookDto);
            newBook.setId(id);
            return bookService.add(newBook);
        }));
    }

    @DeleteMapping("/api/book/{id}")
    public void deleteGenre(@PathVariable("id") long id) {
        bookService.deleteById(id);
    }
}
