package com.polozov.bookprojectweb.service;

import com.polozov.bookprojectweb.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Optional<Author> getById(long id);
    Author getByName(String name);
    List<Author> getAll();
    Author add(Author author);
    Author update(Author author);
    void deleteById(long id);
}