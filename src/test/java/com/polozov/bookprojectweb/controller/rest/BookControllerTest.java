package com.polozov.bookprojectweb.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.polozov.bookprojectweb.controller.rest.dto.AuthorDto;
import com.polozov.bookprojectweb.controller.rest.dto.BookDto;
import com.polozov.bookprojectweb.controller.rest.dto.GenreDto;
import com.polozov.bookprojectweb.domain.Author;
import com.polozov.bookprojectweb.domain.Genre;
import com.polozov.bookprojectweb.repository.AuthorRepository;
import com.polozov.bookprojectweb.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Book controller")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class BookControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(authorities = {"author:read", "genre:read", "author:write", "book:write", "genre:write"})
    @DisplayName("Пользователь с правами author:read/write, genre:read/write, book:write не имеет доступ к чтению книг")
    @Test
    public void shouldGettingBooksIsNotAvailableForNoBookReader() throws Exception {
        // GET ALL
        mockMvc.perform(get("/api/book"))
                .andExpect(status().isForbidden());

        // GET BY ID
        mockMvc.perform(get("/api/book/1"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = {"book:read"})
    @DisplayName("Пользователь с правами book:read имеет доступ к чтению книг")
    @Test
    public void shouldGettingBooksIsAvailableForBookReader() throws Exception {
        // GET ALL
        mockMvc.perform(get("/api/book"))
                .andExpect(status().isOk());

        // GET BY ID
        mockMvc.perform(get("/api/book/1"))
                .andExpect(status().isOk());
    }


    @WithMockUser(authorities = {"book:read", "author:read", "genre:write", "author:write", "genre:read"})
    @DisplayName("Пользователь с правами author:read/write, genre:read/write, book:read не имеют доступ к созданию и изменению книг")
    @Test
    public void shouldAuthorChangingIsNotAvailableForNoAuthorWriter() throws Exception {
        Author author = authorRepository.save(new Author(0, "Автор-1"));
        Genre genre = genreRepository.save(new Genre(0, "Жанр-1"));
        // CREATING
        BookDto bookDto1 = new BookDto(
                1,
                "Новое название",
                new AuthorDto(author.getId(), author.getName()),
                new GenreDto(genre.getId(), genre.getName())
        );

        mockMvc.perform(post("/api/book")
                        .content(convertObjectToJsonString(bookDto1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        // DELETING
        mockMvc.perform(delete("/api/book/1"))
                .andExpect(status().isForbidden());

        // UPDATING
        BookDto bookDto2 = new BookDto(
                1,
                "Новое название",
                new AuthorDto(author.getId(), author.getName()),
                new GenreDto(genre.getId(), genre.getName())
        );

        mockMvc.perform(put("/api/book/" + 1)
                        .content(convertObjectToJsonString(bookDto2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = {"book:write"})
    @DisplayName("Пользователь с правами book:write имеет доступ к созданию и изменению книг")
    @Test
    public void shouldAuthorChangingIsAvailableForAuthorWriter() throws Exception {
        Author author = authorRepository.save(new Author(0, "Автор-1"));
        Genre genre = genreRepository.save(new Genre(0, "Жанр-1"));
        // CREATING
        BookDto bookDto1 = new BookDto(
                1,
                "Новое название",
                new AuthorDto(author.getId(), author.getName()),
                new GenreDto(genre.getId(), genre.getName())
        );

        mockMvc.perform(post("/api/book")
                        .content(convertObjectToJsonString(bookDto1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // DELETING
        mockMvc.perform(delete("/api/book/1"))
                .andExpect(status().isOk());

        // UPDATING
        BookDto bookDto2 = new BookDto(
                1,
                "Новое название",
                new AuthorDto(author.getId(), author.getName()),
                new GenreDto(genre.getId(), genre.getName())
        );

        mockMvc.perform(put("/api/book/" + 1)
                        .content(convertObjectToJsonString(bookDto2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithAnonymousUser
    @DisplayName("Анонимный пользователь не имеет доступа к любым REST методам. Всегда происходит redirect")
    @Test
    public void shouldRedirectAfterAnyRestMethodForAnonymousUser() throws Exception {
        // GET ALL
        mockMvc.perform(get("/api/book"))
                .andExpect(status().is3xxRedirection());

        // GET BY ID
        mockMvc.perform(get("/api/book/1"))
                .andExpect(status().is3xxRedirection());

        Author author = authorRepository.save(new Author(0, "Автор-1"));
        Genre genre = genreRepository.save(new Genre(0, "Жанр-1"));
        // CREATING
        BookDto bookDto1 = new BookDto(
                1,
                "Новое название",
                new AuthorDto(author.getId(), author.getName()),
                new GenreDto(genre.getId(), genre.getName())
        );

        mockMvc.perform(post("/api/book")
                        .content(convertObjectToJsonString(bookDto1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());

        // DELETING
        mockMvc.perform(delete("/api/book/1"))
                .andExpect(status().is3xxRedirection());

        // UPDATING
        BookDto bookDto2 = new BookDto(
                1,
                "Новое название",
                new AuthorDto(author.getId(), author.getName()),
                new GenreDto(genre.getId(), genre.getName())
        );

        mockMvc.perform(put("/api/book/" + 1)
                        .content(convertObjectToJsonString(bookDto2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }

    //Converts Object to Json String
    private String convertObjectToJsonString(BookDto book) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(book);
    }
}
