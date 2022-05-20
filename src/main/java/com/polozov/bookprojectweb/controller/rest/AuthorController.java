package com.polozov.bookprojectweb.controller.rest;

import com.polozov.bookprojectweb.controller.rest.dto.AuthorDto;
import com.polozov.bookprojectweb.domain.Author;
import com.polozov.bookprojectweb.exception.AuthorNotFoundException;
import com.polozov.bookprojectweb.exception.ObjectNotFoundException;
import com.polozov.bookprojectweb.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/api/author")
    public List<AuthorDto> authorList() {
        return authorService.getAll().stream().map(AuthorDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/api/author/{id}")
    public AuthorDto getAuthor(@PathVariable("id") long id) {
        return authorService.getById(id).map(AuthorDto::toDto).orElseThrow(() -> new AuthorNotFoundException(id));
    }

    @PostMapping("/api/author")
    public AuthorDto saveAuthor(@RequestBody Author author) {
        return AuthorDto.toDto(authorService.add(author));
    }

    @PutMapping("/api/author/{id}")
    public AuthorDto updateAuthor(@RequestBody Author newAuthor, @PathVariable("id") long id) {
        return AuthorDto.toDto(authorService.getById(id).map(author -> {
            author.setName(newAuthor.getName());
            return authorService.update(author);
        }).orElseGet(() -> {
            newAuthor.setId(id);
            return authorService.add(newAuthor);
        }));
    }

    @DeleteMapping("/api/author/{id}")
    public void deleteAuthor(@PathVariable("id") long id) {
        authorService.deleteById(id);
    }
}
