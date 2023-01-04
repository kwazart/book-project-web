package com.polozov.bookprojectweb.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.polozov.bookprojectweb.domain.Author;
import com.polozov.bookprojectweb.repository.AuthorRepository;
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

@DisplayName("Author controller")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class AuthorControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private AuthorRepository repository;

    private long idForDeleting;
    private long idForPutting;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(authorities = {"book:read", "genre:read", "author:write", "book:write", "genre:write"})
    @DisplayName("Пользователь с правами book:read/write, genre:read/write, author:write не имеет доступ к чтению авторов")
    @Test
    public void shouldGettingAuthorIsNotAvailableForNoAuthorReader() throws Exception {
        // GET ALL
        mockMvc.perform(get("/api/author"))
                .andExpect(status().isForbidden());

        when(repository.findById(1L)).thenReturn(Optional.empty());
        // GET BY ID
        mockMvc.perform(get("/api/author/1"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "author:read")
    @DisplayName("Права author:read имеют доступ к чтению данных")
    @Test
    public void shouldGettingAuthorIsAvailableForAuthorReader() throws Exception {
        // GET ALL
        mockMvc.perform(get("/api/author"))
                .andExpect(status().isOk());

        when(repository.findById(1L)).thenReturn(Optional.empty());
        // GET BY ID
        mockMvc.perform(get("/api/author/1"))
                .andExpect(status().isOk());
    }


    @WithMockUser(authorities = {"book:read", "author:read", "genre:write", "book:write", "genre:read"})
    @DisplayName("Пользователь с правами book:read/write, genre:read/write, author:read не имеют доступ к созданию и изменению авторов")
    @Test
    public void shouldAuthorChangingIsNotAvailableForNoAuthorWriter() throws Exception {
        // CREATING
        Author author = new Author();
        author.setName("Новый автор");

        mockMvc.perform(post("/api/author")
                        .content(convertObjectToJsonString(author))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        // DELETING
        Author author1 = new Author();
        author1.setName("Тестовый автор-1");
        idForDeleting = repository.save(author1).getId();

        mockMvc.perform(delete("/api/author/" + idForDeleting))
                .andExpect(status().isForbidden());

        // UPDATING
        Author author2 = new Author();
        author2.setName("Тестовый автор-2");
        idForPutting = repository.save(author2).getId();

        Author newAuthor = new Author();
        newAuthor.setName("Новый автор-3");

        mockMvc.perform(put("/api/author/" + idForPutting)
                        .content(convertObjectToJsonString(newAuthor))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = {"author:write"})
    @DisplayName("Пользователь с правами author:write имеет доступ к созданию и изменению авторов")
    @Test
    public void shouldAuthorCreatingIsAvailableForAuthorWriter() throws Exception {
        // CREATING
        Author author = new Author();
        author.setName("Новый автор");

        mockMvc.perform(post("/api/author")
                        .content(convertObjectToJsonString(author))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // DELETING
        Author author1 = new Author();
        author1.setName("Тестовый автор-1");
        idForDeleting = repository.save(author1).getId();

        mockMvc.perform(delete("/api/author/" + idForDeleting))
                .andExpect(status().isOk());

        // UPDATING
        Author author2 = new Author();
        author2.setName("Тестовый автор-2");
        idForPutting = repository.save(author2).getId();

        Author newAuthor = new Author();
        newAuthor.setName("Новый автор-3");

        mockMvc.perform(put("/api/author/" + idForPutting)
                        .content(convertObjectToJsonString(newAuthor))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithAnonymousUser
    @DisplayName("Анонимный пользователь не имеет доступа к любым REST методам. Всегда происходит redirect")
    @Test
    public void shouldRedirectAfterAnyRestMethodForAnonymousUser() throws Exception {
        // GET ALL
        mockMvc.perform(get("/api/author"))
                .andExpect(status().is3xxRedirection());

        // GET BY ID
        mockMvc.perform(get("/api/author/1"))
                .andExpect(status().is3xxRedirection());

        // CREATING
        Author author1 = new Author();
        author1.setName("Новый автор");

        mockMvc.perform(post("/api/author")
                        .content(convertObjectToJsonString(author1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());

        // DELETING
        Author author2 = new Author();
        author2.setName("Тестовый автор-1");
        idForDeleting = repository.save(author2).getId();

        mockMvc.perform(delete("/api/author/" + idForDeleting))
                .andExpect(status().is3xxRedirection());

        // UPDATING
        Author author3 = new Author();
        author3.setName("Тестовый автор-2");
        idForPutting = repository.save(author3).getId();

        Author newAuthor = new Author();
        newAuthor.setName("Новый автор-3");

        mockMvc.perform(put("/api/author/" + idForPutting)
                        .content(convertObjectToJsonString(newAuthor))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }

    //Converts Object to Json String
    private String convertObjectToJsonString(Author author) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(author);
    }
}
