package com.polozov.bookprojectweb.service;

import com.polozov.bookprojectweb.exception.AuthorNotFoundException;
import com.polozov.bookprojectweb.exception.GenreNotFoundException;
import com.polozov.bookprojectweb.repository.BookRepository;
import com.polozov.bookprojectweb.domain.Author;
import com.polozov.bookprojectweb.domain.Book;
import com.polozov.bookprojectweb.domain.Genre;
import com.polozov.bookprojectweb.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public Optional<Book> getById(long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public List<Book> getByName(String name) {
        return repository.findByBookName(name);
    }

    @Transactional
    @Override
    public List<Book> getByAuthorName(String authorName) {
        return repository.findAllByAuthorName(authorName);
    }

    @Transactional
    @Override
    public List<Book> getByGenreName(String genreName) {
        return repository.findAllByGenreName(genreName);
    }

    @Transactional
    @Override
    public List<Book> getAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Book add(Book book) {
        Optional<Author> optionalAuthor = authorService.getById(book.getAuthor().getId());
        Optional<Genre> optionalGenre = genreService.getById(book.getGenre().getId());
        if (optionalAuthor.isEmpty()) {
            throw new AuthorNotFoundException(book.getAuthor().getId());
        }
        if (optionalGenre.isEmpty()) {
            throw new GenreNotFoundException(book.getGenre().getId());
        }
        book.setAuthor(optionalAuthor.get());
        book.setGenre(optionalGenre.get());
        return repository.save(book);
    }

    @Transactional
    @Override
    public Book update(Book book) {
        return repository.save(book);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
