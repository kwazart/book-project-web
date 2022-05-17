package com.polozov.bookprojectweb.service;

import com.polozov.bookprojectweb.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    Optional<Genre> getById(long id);
    Genre getByName(String name);
    List<Genre> getAll();
    Genre add(Genre genre);
    Genre update(Genre genre);
    void deleteById(long id);
}
