package com.polozov.bookprojectweb.controller.rest;

import com.polozov.bookprojectweb.controller.rest.dto.GenreDto;
import com.polozov.bookprojectweb.domain.Genre;
import com.polozov.bookprojectweb.exception.GenreNotFoundException;
import com.polozov.bookprojectweb.exception.ObjectNotFoundException;
import com.polozov.bookprojectweb.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PreAuthorize("hasAuthority('genre:read')")
    @GetMapping("/api/genre")
    public List<GenreDto> genreList() {
        return genreService.getAll().stream().map(GenreDto::toDto).collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('genre:read')")
    @GetMapping("/api/genre/{id}")
    public GenreDto getGenre(@PathVariable("id") long id) {
        return genreService.getById(id).map(GenreDto::toDto).orElseThrow(() -> new GenreNotFoundException(id));
    }

    @PreAuthorize("hasAuthority('genre:write')")
    @PostMapping("/api/genre")
    public GenreDto saveGenre(@RequestBody Genre genre) {
        return GenreDto.toDto(genreService.add(genre));
    }

    @PreAuthorize("hasAuthority('genre:write')")
    @PutMapping("/api/genre/{id}")
    public GenreDto updateGenre(@RequestBody Genre newGenre, @PathVariable("id") long id) {
        return GenreDto.toDto(genreService.getById(id).map(genre -> {
            genre.setName(newGenre.getName());
            return genreService.update(genre);
        }).orElseGet(() -> {
            newGenre.setId(id);
            return genreService.add(newGenre);
        }));
    }

    @PreAuthorize("hasAuthority('genre:write')")
    @DeleteMapping("/api/genre/{id}")
    public void deleteGenre(@PathVariable("id") long id) {
        genreService.deleteById(id);
    }
}
