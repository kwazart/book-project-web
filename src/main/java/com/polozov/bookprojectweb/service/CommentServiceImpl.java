package com.polozov.bookprojectweb.service;

import com.polozov.bookprojectweb.repository.BookRepository;
import com.polozov.bookprojectweb.repository.CommentRepository;
import com.polozov.bookprojectweb.domain.Book;
import com.polozov.bookprojectweb.domain.Comment;
import com.polozov.bookprojectweb.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final BookRepository bookRepository;

    @Override
    public Optional<Comment> getById(long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public List<Comment> getByBookId(long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        return bookOptional.map(repository::findByBook).orElse(new ArrayList<>());
    }

    @Transactional
    @Override
    public Comment add(String text, long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) {
            return null;
        }
        return repository.save(new Comment(0, text, bookOptional.get()));
    }

    @Transactional
    @Override
    public Comment update(Comment comment) {
        return repository.save(comment);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
