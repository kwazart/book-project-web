package com.polozov.bookprojectweb.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.polozov.bookprojectweb.controller.rest.dto.AuthorDto;
import com.polozov.bookprojectweb.controller.rest.dto.BookDto;
import com.polozov.bookprojectweb.controller.rest.dto.GenreDto;
import com.polozov.bookprojectweb.domain.Author;
import com.polozov.bookprojectweb.domain.Book;
import com.polozov.bookprojectweb.domain.Genre;
import com.polozov.bookprojectweb.repository.AuthorRepository;
import com.polozov.bookprojectweb.repository.BookRepository;
import com.polozov.bookprojectweb.repository.GenreRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Book controller")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private BookRepository repository;

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

    // -------------------------------- GET ALL --------------------------------
    @WithMockUser(authorities = "author:read")
    @DisplayName("Получение списка книг недоступно для разрешения author:read")
    @Test
    public void shouldGettingAllBooksIsNotAvailableForAuthorReader() throws Exception {

        mockMvc.perform(get("/api/book"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "book:read")
    @DisplayName("Получение списка книг доступно для разрешения book:read")
    @Test
    public void shouldGettingAllBooksIsAvailableForBookReader() throws Exception {
        mockMvc.perform(get("/api/book"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "genre:read")
    @DisplayName("Получение списка книг недоступно для разрешения genre:read")
    @Test
    public void shouldGettingAllBooksIsNotAvailableForGenreReader() throws Exception {
        mockMvc.perform(get("/api/book"))
                .andExpect(status().isForbidden());
    }

    // -------------------------------- GET by ID --------------------------------
    @WithMockUser(authorities = "author:read")
    @DisplayName("Получение книги по id недоступно для разрешения author:read")
    @Test
    public void shouldGettingOneBookIsNotAvailableForAuthorReader() throws Exception {
        mockMvc.perform(get("/api/book/1"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "book:read")
    @DisplayName("Получение жанра по id доступно для разрешения book:read")
    @Test
    public void shouldGettingOneBookIsAvailableForBookReader() throws Exception {
        mockMvc.perform(get("/api/book/1"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "genre:read")
    @DisplayName("Получение жанра по id недоступно для разрешения genre:read")
    @Test
    public void shouldGettingOneBookIsNotAvailableForGenreReader() throws Exception {
        mockMvc.perform(get("/api/book/1"))
                .andExpect(status().isForbidden());
    }

    // -------------------------------- CREATING --------------------------------
    @WithMockUser(authorities = "author:write")
    @DisplayName("Создание книги недоступно для разрешения author:write")
    @Test
    public void shouldBookCreatingIsNotAvailableForAuthorWriter() throws Exception {
        Author author = authorRepository.save(new Author(0, "Автор-1"));
        Genre genre = genreRepository.save(new Genre(0, "Жанр-1"));

        BookDto bookDto = new BookDto(
                1,
                "Новое название",
                new AuthorDto(author.getId(), author.getName()),
                new GenreDto(genre.getId(), genre.getName())
        );

        mockMvc.perform(post("/api/book")
                        .content(convertObjectToJsonString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "book:write")
    @DisplayName("Создание книги доступно для разрешения book:write")
    @Test
    public void shouldBookCreatingIsAvailableForBookWriter() throws Exception {
        Author author = authorRepository.save(new Author(0, "Автор-1"));
        Genre genre = genreRepository.save(new Genre(0, "Жанр-1"));

        BookDto bookDto = new BookDto(
                1,
                "Новое название",
                new AuthorDto(author.getId(), author.getName()),
                new GenreDto(genre.getId(), genre.getName())
        );

        mockMvc.perform(post("/api/book")
                        .content(convertObjectToJsonString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "genre:write")
    @DisplayName("Создание книги недоступно для разрешения genre:write")
    @Test
    public void shouldBookCreatingIsNotAvailableForGenreWriter() throws Exception {
        Author author = authorRepository.save(new Author(0, "Автор-1"));
        Genre genre = genreRepository.save(new Genre(0, "Жанр-1"));

        BookDto bookDto = new BookDto(
                1,
                "Новое название",
                new AuthorDto(author.getId(), author.getName()),
                new GenreDto(genre.getId(), genre.getName())
        );

        mockMvc.perform(post("/api/book")
                        .content(convertObjectToJsonString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    // -------------------------------- DELETING --------------------------------
    @WithMockUser(authorities = "author:write")
    @DisplayName("Удаление книги по id недоступно для разрешения author:write")
    @Test
    public void shouldDeletingBookIsNotAvailableForAuthorWriter() throws Exception {
        mockMvc.perform(delete("/api/book/1"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "book:write")
    @DisplayName("Удаление книги по id доступно для разрешения book:write")
    @Test
    public void shouldDeletingBookIsNotAvailableForBookWriter() throws Exception {
        mockMvc.perform(delete("/api/book/2"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "genre:write")
    @DisplayName("Удаление жанра по id недоступно для разрешения genre:write")
    @Test
    public void shouldDeletingGenreIsNotAvailableForGenreWriter() throws Exception {
        mockMvc.perform(delete("/api/book/3"))
                .andExpect(status().isForbidden());
    }

    // -------------------------------- UPDATING --------------------------------
    @WithMockUser(authorities = "author:write")
    @DisplayName("Обновление книги по id недоступно для разрешения author:write")
    @Test
    public void shouldUpdatingBookIsNotAvailableForAuthorWriter() throws Exception {
        Author author = authorRepository.save(new Author(0, "Автор-1"));
        Genre genre = genreRepository.save(new Genre(0, "Жанр-1"));

        BookDto bookDto = new BookDto(
                1,
                "Новое название",
                new AuthorDto(author.getId(), author.getName()),
                new GenreDto(genre.getId(), genre.getName())
        );

        mockMvc.perform(put("/api/book/" + 1)
                        .content(convertObjectToJsonString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "book:write")
    @DisplayName("Обновление книги по id доступно для разрешения book:write")
    @Test
    public void shouldUpdatingBookIsAvailableForBookWriter() throws Exception {
        Author author = authorRepository.save(new Author(0, "Автор-1"));
        Genre genre = genreRepository.save(new Genre(0, "Жанр-1"));

        BookDto bookDto = new BookDto(
                1,
                "Новое название",
                new AuthorDto(author.getId(), author.getName()),
                new GenreDto(genre.getId(), genre.getName())
        );

        mockMvc.perform(put("/api/book/" + 2)
                        .content(convertObjectToJsonString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "genre:write")
    @DisplayName("Обновление книги по id недоступно для разрешения genre:write")
    @Test
    public void shouldUpdatingBookIsNotAvailableForGenreWriter() throws Exception {
        Author author = authorRepository.save(new Author(0, "Автор-1"));
        Genre genre = genreRepository.save(new Genre(0, "Жанр-1"));

        BookDto bookDto = new BookDto(
                1,
                "Новое название",
                new AuthorDto(author.getId(), author.getName()),
                new GenreDto(genre.getId(), genre.getName())
        );

        mockMvc.perform(put("/api/book/" + 3)
                        .content(convertObjectToJsonString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    //Converts Object to Json String
    private String convertObjectToJsonString(BookDto book) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(book);
    }
}
