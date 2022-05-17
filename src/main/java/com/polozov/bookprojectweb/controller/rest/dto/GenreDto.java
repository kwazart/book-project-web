package com.polozov.bookprojectweb.controller.rest.dto;

import com.polozov.bookprojectweb.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenreDto {

    private long id;
    private String name;

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

    public static Genre fromDto(GenreDto dto) {
        return new Genre(dto.getId(), dto.getName());
    }
}
