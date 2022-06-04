package com.polozov.bookprojectweb.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.polozov.bookprojectweb.domain.Genre;
import com.polozov.bookprojectweb.repository.GenreRepository;
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

@DisplayName("Genre controller")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GenreControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private GenreRepository repository;

    private long idForGetting;
    private long idForDeleting;
    private long idForPutting;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    // -------------------------------- GET ALL --------------------------------
    @WithMockUser(authorities = "author:read")
    @DisplayName("Получение списка жанров недоступно для разрешения author:read")
    @Test
    public void shouldGettingAllGenreIsNotAvailableForAuthorReader() throws Exception {
        mockMvc.perform(get("/api/genre"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "book:read")
    @DisplayName("Получение списка жанров недоступно для разрешения book:read")
    @Test
    public void shouldGettingAllGenresIsNotAvailableForBookReader() throws Exception {
        mockMvc.perform(get("/api/genre"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "genre:read")
    @DisplayName("Получение списка жанров доступно для разрешения genre:read")
    @Test
    public void shouldGettingAllGenresIsAvailableForGenreReader() throws Exception {
        mockMvc.perform(get("/api/genre"))
                .andExpect(status().isOk());
    }

    // -------------------------------- GET by ID --------------------------------
    @WithMockUser(authorities = "author:read")
    @DisplayName("Получение жанра по id недоступно для разрешения author:read")
    @Test
    public void shouldGettingOneGenreIsNotAvailableForAuthorReader() throws Exception {
        Genre genre = new Genre();
        genre.setName("Тестовый жанр");
        idForGetting = repository.save(genre).getId();

        mockMvc.perform(get("/api/genre/" + idForGetting))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "book:read")
    @DisplayName("Получение жанра по id недоступно для разрешения book:read")
    @Test
    public void shouldGettingOneGenreIsNotAvailableForBookReader() throws Exception {
        Genre genre = new Genre();
        genre.setName("Тестовый жанр");
        idForGetting = repository.save(genre).getId();

        mockMvc.perform(get("/api/genre/" + idForGetting))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "genre:read")
    @DisplayName("Получение жанра по id доступно для разрешения genre:read")
    @Test
    public void shouldGettingOneGenreIsAvailableForGenreReader() throws Exception {
        Genre genre = new Genre();
        genre.setName("Тестовый жанр");
        idForGetting = repository.save(genre).getId();

        mockMvc.perform(get("/api/genre/" + idForGetting))
                .andExpect(status().isOk());
    }

    // -------------------------------- CREATING --------------------------------
    @WithMockUser(authorities = "author:write")
    @DisplayName("Создание жанра недоступно для разрешения author:write")
    @Test
    public void shouldGenreCreatingIsNotAvailableForAuthorWriter() throws Exception {
        Genre genre = new Genre();
        genre.setName("Новый жанр");

        mockMvc.perform(post("/api/genre")
                        .content(convertObjectToJsonString(genre))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "book:write")
    @DisplayName("Создание жанра недоступно для разрешения book:write")
    @Test
    public void shouldGenreCreatingIsNotAvailableForBookWriter() throws Exception {
        Genre genre = new Genre();
        genre.setName("Новый жанр");

        mockMvc.perform(post("/api/genre")
                        .content(convertObjectToJsonString(genre))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "genre:write")
    @DisplayName("Создание жанра доступно для разрешения genre:write")
    @Test
    public void shouldGenreCreatingIsAvailableForGenreWriter() throws Exception {
        Genre genre = new Genre();
        genre.setName("Новый жанр");

        mockMvc.perform(post("/api/genre")
                        .content(convertObjectToJsonString(genre))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // -------------------------------- DELETING --------------------------------
    @WithMockUser(authorities = "author:write")
    @DisplayName("Удаление жанра по id недоступно для разрешения author:write")
    @Test
    public void shouldDeletingGenreIsNotAvailableForAuthorWriter() throws Exception {
        Genre genre1 = new Genre();
        genre1.setName("Тестовый жанр-1");
        idForDeleting = repository.save(genre1).getId();

        mockMvc.perform(delete("/api/genre/" + idForDeleting))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "book:write")
    @DisplayName("Удаление жанра по id недоступно для разрешения book:write")
    @Test
    public void shouldDeletingGenreIsNotAvailableForBookWriter() throws Exception {
        Genre genre1 = new Genre();
        genre1.setName("Тестовый жанр-1");
        idForDeleting = repository.save(genre1).getId();

        mockMvc.perform(delete("/api/genre/" + idForDeleting))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "genre:write")
    @DisplayName("Удаление жанра по id доступно для разрешения genre:write")
    @Test
    public void shouldDeletingGenreIsAvailableForGenreWriter() throws Exception {
        Genre genre1 = new Genre();
        genre1.setName("Тестовый жанр-1");
        idForDeleting = repository.save(genre1).getId();

        mockMvc.perform(delete("/api/genre/" + idForDeleting))
                .andExpect(status().isOk());
    }

    // -------------------------------- UPDATING --------------------------------
    @WithMockUser(authorities = "author:write")
    @DisplayName("Обновление жанра по id доступно для разрешения author:write")
    @Test
    public void shouldUpdatingGenreIsAvailableForAuthorWriter() throws Exception {
        Genre genre2 = new Genre();
        genre2.setName("Тестовый жанр-2");
        idForPutting = repository.save(genre2).getId();

        Genre genre = new Genre();
        genre.setName("Новый жанр-3");

        mockMvc.perform(put("/api/genre/" + idForPutting)
                        .content(convertObjectToJsonString(genre))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "book:write")
    @DisplayName("Обновление жанра по id недоступно для разрешения book:write")
    @Test
    public void shouldUpdatingGenreIsNotAvailableForBookWriter() throws Exception {
        Genre genre2 = new Genre();
        genre2.setName("Тестовый жанр-2");
        idForPutting = repository.save(genre2).getId();

        Genre genre = new Genre();
        genre.setName("Новый жанр-3");

        mockMvc.perform(put("/api/genre/" + idForPutting)
                        .content(convertObjectToJsonString(genre))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "genre:write")
    @DisplayName("Обновление жанра по id недоступно для разрешения genre:write")
    @Test
    public void shouldUpdatingGenreIsNotAvailableForGenreWriter() throws Exception {
        Genre genre2 = new Genre();
        genre2.setName("Тестовый жанр-2");
        idForPutting = repository.save(genre2).getId();

        Genre genre = new Genre();
        genre.setName("Новый жанр-3");

        mockMvc.perform(put("/api/genre/" + idForPutting)
                        .content(convertObjectToJsonString(genre))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //Converts Object to Json String
    private String convertObjectToJsonString(Genre genre) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(genre);
    }
}
