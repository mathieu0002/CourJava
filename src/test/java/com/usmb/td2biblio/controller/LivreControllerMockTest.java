package com.usmb.td2biblio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmb.td2biblio.entity.Livre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class LivreControllerMockTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private MockMvcTester mvc;
    private final ObjectMapper objectMapper = new ObjectMapper(); // ← instanciation directe

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        mvc = MockMvcTester.create(mockMvc);
    }

    @Test
    void testWithMockMvcTester() {
        assertThat(mvc.get().uri("/biblio/livre/1"))
                .bodyText().contains("Les Misérables");
    }

    @Test
    void testGetLivreByIdWithMockMvc() throws Exception {
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/biblio/livre/1")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        Livre livre = objectMapper.readValue(json, Livre.class);
        assertThat(livre.getTitre()).isEqualTo("Les Misérables");
    }
}