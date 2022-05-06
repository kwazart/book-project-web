package com.polozov.bookprojectweb.service;

import com.polozov.bookprojectweb.repository.AuthorRepository;
import com.polozov.bookprojectweb.domain.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    @Override
    public Optional<Author> getById(long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public Author getByName(String name) {
        return repository.findByName(name);
    }

    @Transactional
    @Override
    public List<Author> getAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Author add(String name) {
        return repository.save(new Author(0, name));
    }

    @Transactional
    @Override
    public Author update(Author author) {
        return repository.save(author);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
