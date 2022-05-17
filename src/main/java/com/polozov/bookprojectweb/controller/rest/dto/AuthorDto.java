package com.polozov.bookprojectweb.controller.rest.dto;

import com.polozov.bookprojectweb.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorDto {

    private long id;
    private String name;

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }

    public static Author fromDto(AuthorDto dto) {
        return new Author(dto.getId(), dto.getName());
    }
}
