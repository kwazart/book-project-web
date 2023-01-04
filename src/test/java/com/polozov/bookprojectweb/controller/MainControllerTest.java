package com.polozov.bookprojectweb.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Main controller")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MainControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @WithAnonymousUser
    @DisplayName("Главная страница доступна")
    @Test
    public void shouldMainPageIsAvailable() throws Exception {
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk());
    }

    @WithAnonymousUser
    @DisplayName("Страница логина доступна")
    @Test
    public void shouldLoginPageIsAvailable() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }
}
