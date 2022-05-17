package com.polozov.bookprojectweb.service;

import com.polozov.bookprojectweb.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> getById(long id);
    List<Comment> getByBookId(long bookId);
    Comment add(String text, long bookId);
    Comment update(Comment comment);
    void deleteById(long id);
}
