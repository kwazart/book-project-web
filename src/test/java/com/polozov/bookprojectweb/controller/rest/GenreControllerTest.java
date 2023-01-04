package com.polozov.bookprojectweb.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.polozov.bookprojectweb.domain.Genre;
import com.polozov.bookprojectweb.repository.GenreRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Genre controller")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class GenreControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

//    @Autowired
    @MockBean
    private GenreRepository repository;

    private long idForDeleting;
    private long idForPutting;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @WithMockUser(authorities = {"book:read", "author:read", "author:write", "book:write", "genre:write"})
    @DisplayName("Пользователь с правами book:read/write,author:read/write, genre:write не имеет доступ к чтению жанров")
    @Test
    public void shouldGettingGenresIsNotAvailableForNoGenreReader() throws Exception {
        // GET ALL
        mockMvc.perform(get("/api/genre"))
                .andExpect(status().isForbidden());

        when(repository.findById(1L)).thenReturn(Optional.of(new Genre(1, "")));
        // GET BY ID
        mockMvc.perform(get("/api/genre/1"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "genre:read")
    @DisplayName("Права genre:read имеют доступ к чтению данных")
    @Test
    public void shouldGettingGenresIsAvailableForGenreReader() throws Exception {
        // GET ALL
        mockMvc.perform(get("/api/genre"))
                .andExpect(status().isOk());

        when(repository.findById(1L)).thenReturn(Optional.of(new Genre(1, "")));
        // GET BY ID
        mockMvc.perform(get("/api/genre/1"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {"book:read", "author:read", "author:write", "book:write", "genre:read"})
    @DisplayName("Пользователь с правами book:read/write, author:read/write, genre:read не имеют доступ к созданию и изменению жанров")
    @Test
    public void shouldGenreChangingIsNotAvailableForNoGenreWriter() throws Exception {
        // CREATING
        Genre genre = new Genre();
        genre.setName("Новый жанр");

        when(repository.save(genre)).thenReturn(new Genre(100, "Тестовый жанр-100"));
        mockMvc.perform(post("/api/genre")
                        .content(convertObjectToJsonString(genre))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        // DELETING
        Genre genre1 = new Genre();
        genre1.setName("Тестовый жанр-1");
        when(repository.save(genre1)).thenReturn(new Genre(100, "Тестовый жанр-100"));

        idForDeleting = repository.save(genre1).getId();

        mockMvc.perform(delete("/api/genre/" + idForDeleting))
                .andExpect(status().isForbidden());

        // UPDATING
        Genre genre2 = new Genre();
        genre2.setName("Тестовый жанр-200");
        when(repository.save(genre2)).thenReturn(new Genre(200, "Тестовый жанр-200"));
        idForPutting = repository.save(genre2).getId();

        Genre genre3 = new Genre();
        genre3.setName("Новый жанр-3");

        mockMvc.perform(put("/api/genre/" + idForPutting)
                        .content(convertObjectToJsonString(genre3))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "genre:write")
    @DisplayName("Пользователь с правами genre:write имеет доступ к созданию и изменению жанров")
    @Test
    public void shouldGenreChangingIsAvailableForGenreWriter() throws Exception {
        // CREATING
        Genre genre = new Genre();
        genre.setName("Новый жанр");

        when(repository.save(genre)).thenReturn(new Genre(100, "Тестовый жанр-100"));
        mockMvc.perform(post("/api/genre")
                        .content(convertObjectToJsonString(genre))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // DELETING
        Genre genre1 = new Genre();
        genre1.setName("Тестовый жанр-1");
        when(repository.save(genre1)).thenReturn(new Genre(100, "Тестовый жанр-100"));

        mockMvc.perform(delete("/api/genre/" + idForDeleting))
                .andExpect(status().isOk());

        // UPDATING
        Genre genre2 = new Genre();
        genre2.setName("Тестовый жанр-200");
        when(repository.save(genre2)).thenReturn(new Genre(200, "Тестовый жанр-200"));
        idForPutting = repository.save(genre2).getId();

        Genre genre3 = new Genre();
        genre3.setName("Новый жанр-3");

        when(repository.save(genre3)).thenReturn(new Genre(300, "Тестовый жанр-300"));
        mockMvc.perform(put("/api/genre/" + idForPutting)
                        .content(convertObjectToJsonString(genre3))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithAnonymousUser
    @DisplayName("Анонимный пользователь не имеет доступа к любым REST методам. Всегда происходит redirect")
    @Test
    public void shouldRedirectAfterAnyRestMethodForAnonymousUser() throws Exception {
        // GET ALL
        mockMvc.perform(get("/api/genre"))
                .andExpect(status().is3xxRedirection());

        // GET BY ID
        mockMvc.perform(get("/api/genre/1"))
                .andExpect(status().is3xxRedirection());

        // CREATING
        Genre genre1 = new Genre();
        genre1.setName("Новый жанр");

        mockMvc.perform(post("/api/genre")
                        .content(convertObjectToJsonString(genre1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());

        // DELETING
        Genre genre2 = new Genre();
        genre2.setName("Тестовый жанр-1");
        when(repository.save(genre2)).thenReturn(new Genre(100, "Тестовый жанр-100"));
        idForDeleting = repository.save(genre2).getId();

        mockMvc.perform(delete("/api/genre/" + idForDeleting))
                .andExpect(status().is3xxRedirection());

        // UPDATING
        Genre genre3 = new Genre();
        genre3.setName("Тестовый жанр-200");
        when(repository.save(genre3)).thenReturn(new Genre(200, "Тестовый жанр-200"));
        idForPutting = repository.save(genre3).getId();

        Genre newGenre = new Genre();
        newGenre.setName("Новый жанр-3");

        mockMvc.perform(put("/api/genre/" + idForPutting)
                        .content(convertObjectToJsonString(newGenre))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }

    //Converts Object to Json String
    private String convertObjectToJsonString(Genre genre) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(genre);
    }
}
