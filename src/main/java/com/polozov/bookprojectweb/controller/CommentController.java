package com.polozov.bookprojectweb.controller;

import com.polozov.bookprojectweb.domain.Author;
import com.polozov.bookprojectweb.domain.Book;
import com.polozov.bookprojectweb.domain.Comment;
import com.polozov.bookprojectweb.domain.Genre;
import com.polozov.bookprojectweb.exception.ObjectNotFoundException;
import com.polozov.bookprojectweb.service.BookService;
import com.polozov.bookprojectweb.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final BookService bookService;

    @GetMapping("/comment")
    public String commentListByBookId(@RequestParam(value = "id", defaultValue = "0") int id, Model model) {
        Book book;
        System.out.println("BOOK ID = " + id);
        if (id != 0) {
            book = bookService.getById(id).orElseThrow(ObjectNotFoundException::new);
        } else {
            book = new Book();
        }
        List<Comment> comments = commentService.getByBookId(id);
        model.addAttribute("books", bookService.getAll());
        model.addAttribute("comments", comments);
        model.addAttribute("book", book);
        return "comment";
    }

    @GetMapping("/comment/edit/{id}")
    public String editComment(@PathVariable("id") int id, Model model) {
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

    @PostMapping("/comment/edit")
    public String saveComment(Comment comment) {
        long bookId = comment.getBook().getId();
        commentService.update(comment);
        return "redirect:/comment/" + bookId;
    }

    @DeleteMapping("/comment/{id}")
    public String deleteGenre(@PathVariable("id") long id) {
        Optional<Comment> commentOptional = commentService.getById(id);
        long bookId = 0;
        if (commentOptional.isPresent()) {
            bookId = commentOptional.get().getBook().getId();
        }
        commentService.deleteById(id);
        return "redirect:/comment/" + bookId;
    }
}
