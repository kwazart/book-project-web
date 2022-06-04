package com.polozov.bookprojectweb.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.polozov.bookprojectweb.domain.Author;
import com.polozov.bookprojectweb.repository.AuthorRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Author controller")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthorControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository repository;

    private long idForDeleting;
    private long idForGetting;
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
    @DisplayName("Получение списка авторов доступно для разрешения author:read")
    @Test
    public void shouldGettingAllAuthorsIsAvailableForAuthorReader() throws Exception {
        mockMvc.perform(get("/api/author"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "book:read")
    @DisplayName("Получение списка авторов недоступно для разрешения book:read")
    @Test
    public void shouldGettingAllAuthorsIsNotAvailableForBookReader() throws Exception {
        mockMvc.perform(get("/api/author"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "genre:read")
    @DisplayName("Получение списка авторов недоступно для разрешения genre:read")
    @Test
    public void shouldGettingAllAuthorsIsNotAvailableForGenreReader() throws Exception {
        mockMvc.perform(get("/api/author"))
                .andExpect(status().isForbidden());
    }

    // -------------------------------- GET by ID --------------------------------
    @WithMockUser(authorities = "author:read")
    @DisplayName("Получение автора по id доступно для разрешения author:read")
    @Test
    public void shouldGettingOneAuthorIsNotAvailableForAuthorReader() throws Exception {
        Author author1 = new Author();
        author1.setName("Тестовый автор-1");
        idForGetting = repository.save(author1).getId();

        mockMvc.perform(get("/api/author/" + idForGetting))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "book:read")
    @DisplayName("Получение автора по id недоступно для разрешения book:read")
    @Test
    public void shouldGettingOneAuthorIsNotAvailableForBookReader() throws Exception {
        Author author1 = new Author();
        author1.setName("Тестовый автор-1");
        idForGetting = repository.save(author1).getId();

        mockMvc.perform(get("/api/author/" + idForGetting))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "genre:read")
    @DisplayName("Получение автора по id недоступно для разрешения genre:read")
    @Test
    public void shouldGettingOneAuthorIsNotAvailableForGenreReader() throws Exception {
        Author author1 = new Author();
        author1.setName("Тестовый автор-1");
        idForGetting = repository.save(author1).getId();

        mockMvc.perform(get("/api/author/" + idForGetting))
                .andExpect(status().isForbidden());
    }

    // -------------------------------- CREATING --------------------------------
    @WithMockUser(authorities = "author:write")
    @DisplayName("Создание автора доступно для разрешения author:write")
    @Test
    public void shouldAuthorCreatingIsAvailableForAuthorWriter() throws Exception {
        Author author = new Author();
        author.setName("Новый автор");

        mockMvc.perform(post("/api/author")
                        .content(convertObjectToJsonString(author))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "book:write")
    @DisplayName("Создание автора недоступно для разрешения book:write")
    @Test
    public void shouldAuthorCreatingIsNotAvailableForBookWriter() throws Exception {
        Author author = new Author();
        author.setName("Новый автор");

        mockMvc.perform(post("/api/author")
                        .content(convertObjectToJsonString(author))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "genre:write")
    @DisplayName("Создание автора недоступно доступно для разрешения genre:write")
    @Test
    public void shouldAuthorCreatingIsNotAvailableForGenreWriter() throws Exception {
        Author author = new Author();
        author.setName("Новый автор");

        mockMvc.perform(post("/api/author")
                        .content(convertObjectToJsonString(author))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    // -------------------------------- DELETING --------------------------------
    @WithMockUser(authorities = "author:write")
    @DisplayName("Удаление автора по id доступно для разрешения author:write")
    @Test
    public void shouldDeletingAuthorIsAvailableForAuthorWriter() throws Exception {
        Author author = new Author();
        author.setName("Тестовый автор-1");
        idForDeleting = repository.save(author).getId();

        mockMvc.perform(delete("/api/author/" + idForDeleting))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "book:write")
    @DisplayName("Удаление автора по id недоступно для разрешения book:write")
    @Test
    public void shouldDeletingAuthorIsNotAvailableForBookWriter() throws Exception {
        Author author = new Author();
        author.setName("Тестовый автор-1");
        idForDeleting = repository.save(author).getId();

        mockMvc.perform(delete("/api/author/" + idForDeleting))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "genre:write")
    @DisplayName("Удаление автора по id недоступно для разрешения genre:write")
    @Test
    public void shouldDeletingAuthorIsNotAvailableForGenreWriter() throws Exception {
        Author author = new Author();
        author.setName("Тестовый автор-1");
        idForDeleting = repository.save(author).getId();

        mockMvc.perform(delete("/api/author/" + idForDeleting))
                .andExpect(status().isForbidden());
    }

    // -------------------------------- UPDATING --------------------------------
    @WithMockUser(authorities = "author:write")
    @DisplayName("Обновление автора по id доступно для разрешения author:write")
    @Test
    public void shouldUpdatingAuthorIsAvailableForAuthorWriter() throws Exception {
        Author author = new Author();
        author.setName("Тестовый автор-2");
        idForPutting = repository.save(author).getId();

        Author newAuthor = new Author();
        newAuthor.setName("Новый автор-3");

        mockMvc.perform(put("/api/author/" + idForPutting)
                        .content(convertObjectToJsonString(newAuthor))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "book:write")
    @DisplayName("Обновление автора по id недоступно для разрешения book:write")
    @Test
    public void shouldUpdatingAuthorIsNotAvailableForBookWriter() throws Exception {
        Author author = new Author();
        author.setName("Тестовый автор-2");
        idForPutting = repository.save(author).getId();

        Author newAuthor = new Author();
        newAuthor.setName("Новый автор-3");

        mockMvc.perform(put("/api/author/" + idForPutting)
                        .content(convertObjectToJsonString(author))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "genre:write")
    @DisplayName("Обновление автора по id недоступно для разрешения genre:write")
    @Test
    public void shouldUpdatingAuthorIsNotAvailableForGenreWriter() throws Exception {
        Author author = new Author();
        author.setName("Тестовый автор-2");
        idForPutting = repository.save(author).getId();

        Author newAuthor = new Author();
        newAuthor.setName("Новый автор-3");

        mockMvc.perform(put("/api/author/" + idForPutting)
                        .content(convertObjectToJsonString(author))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    //Converts Object to Json String
    private String convertObjectToJsonString(Author author) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(author);
    }
}
