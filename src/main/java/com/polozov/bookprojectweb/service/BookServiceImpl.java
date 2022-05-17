package com.polozov.bookprojectweb.service;

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
    public Book add(String bookName, String authorName, String genreName) {
        Author author = authorService.getByName(authorName);
        Genre genre = genreService.getByName(genreName);
        if (author == null || genre == null) {
            throw new ObjectNotFoundException("Object not found");
        }

        return repository.save(new Book(0, bookName, author, genre));
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
