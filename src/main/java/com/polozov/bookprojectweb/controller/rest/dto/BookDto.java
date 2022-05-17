package com.polozov.bookprojectweb.controller.rest.dto;

import com.polozov.bookprojectweb.domain.Author;
import com.polozov.bookprojectweb.domain.Book;
import com.polozov.bookprojectweb.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {

    private long id;
    private String bookName;
    private AuthorDto authorDto;
    private GenreDto genreDto;

    public static BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getName(),
                new AuthorDto(
                        book.getAuthor().getId(),
                        book.getAuthor().getName()),
                new GenreDto(
                        book.getGenre().getId(),
                        book.getGenre().getName()
                ));
    }

    public static Book fromDto(BookDto dto) {
        return new Book(
                dto.getId(),
                dto.getBookName(),
                new Author(dto.getAuthorDto().getId(), ""),
                new Genre(dto.getGenreDto().getId(), ""));
    }
}
