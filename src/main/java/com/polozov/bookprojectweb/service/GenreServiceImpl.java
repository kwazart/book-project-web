package com.polozov.bookprojectweb.service;

import com.polozov.bookprojectweb.repository.GenreRepository;
import com.polozov.bookprojectweb.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository dao;

    @Override
    public Optional<Genre> getById(long id) {
        return dao.findById(id);
    }

    @Transactional
    @Override
    public Genre getByName(String name) {
        return dao.findByName(name);
    }

    @Transactional
    @Override
    public List<Genre> getAll() {
        return dao.findAll();
    }

    @Transactional
    @Override
    public Genre add(Genre genre) {
        return dao.save(genre);
    }

    @Transactional
    @Override
    public Genre update(Genre genre) {
        return dao.save(genre);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        dao.deleteById(id);
    }
}
