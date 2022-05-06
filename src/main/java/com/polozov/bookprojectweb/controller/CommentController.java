package com.polozov.bookprojectweb.controller;

import com.polozov.bookprojectweb.domain.Author;
import com.polozov.bookprojectweb.domain.Book;
import com.polozov.bookprojectweb.domain.Comment;
import com.polozov.bookprojectweb.domain.Genre;
import com.polozov.bookprojectweb.exception.ObjectNotFoundException;
import com.polozov.bookprojectweb.service.AuthorService;
import com.polozov.bookprojectweb.service.BookService;
import com.polozov.bookprojectweb.service.CommentService;
import com.polozov.bookprojectweb.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final BookService bookService;

    @GetMapping
    public String commentListByBookId(@RequestParam(value = "id", defaultValue = "0") int bookId, Model model) {
        Book book;
        System.out.println("BOOK ID = " + bookId);
        if (bookId != 0) {
            book = bookService.getById(bookId).orElseThrow(ObjectNotFoundException::new);
        } else {
            book = new Book();
        }
        List<Comment> comments = commentService.getByBookId(bookId);
        model.addAttribute("books", bookService.getAll());
        model.addAttribute("comments", comments);
        model.addAttribute("book", book);
        return "comment";
    }

    @GetMapping("/edit")
    public String editComment(@RequestParam(value = "id", defaultValue = "0") int id, Model model) {
        Comment comment;
        if (id != 0) {
            comment = commentService.getById(id).orElseThrow(ObjectNotFoundException::new);
        } else {
            comment = new Comment(0, "", new Book(0, "", new Author(), new Genre()));
        }
        model.addAttribute("books", bookService.getAll());
        model.addAttribute("comment", comment);
        return "comment-edit";
    }

    @PostMapping("/edit")
    public String saveComment(Comment comment) {
        commentService.update(comment);
        return "redirect:/comment";
    }

    @GetMapping("/remove")
    public String deleteGenre(@RequestParam("id") long id) {
        Optional<Comment> commentOptional = commentService.getById(id);
        long bookId = 0;
        if (commentOptional.isPresent()) {
            bookId = commentOptional.get().getBook().getId();
        }
        commentService.deleteById(id);
        return "redirect:/comment?id=" + bookId;
    }
}
