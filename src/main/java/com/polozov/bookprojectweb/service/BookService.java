package com.polozov.bookprojectweb.service;

import com.polozov.bookprojectweb.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<Book> getById(long id);
    List<Book> getByName(String name);
    List<Book> getByAuthorName(String name);
    List<Book> getByGenreName(String name);
    List<Book> getAll();
    Book add(String bookName, String authorName, String genreName);
    Book update(Book book);
    void deleteById(long id);
}
