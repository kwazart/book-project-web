package com.polozov.bookprojectweb.controller.rest;

import com.polozov.bookprojectweb.controller.rest.dto.CommentDto;
import com.polozov.bookprojectweb.domain.Comment;
import com.polozov.bookprojectweb.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/api/comments/{id}")
    public List<CommentDto> getAllCommentsByBookId(@PathVariable("id") long bookId) {
        return commentService.getByBookId(bookId).stream().map(CommentDto::toDto).collect(Collectors.toList());
    }
}
