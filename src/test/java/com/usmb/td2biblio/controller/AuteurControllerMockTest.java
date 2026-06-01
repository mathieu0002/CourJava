package com.usmb.td2biblio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmb.td2biblio.entity.Auteur;
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
public class AuteurControllerMockTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private MockMvcTester mvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        mvc = MockMvcTester.create(mockMvc);
    }

    // Style MockMvcTester : le body de l'auteur 1 contient bien "Hugo"
    @Test
    void testGetAuteurByIdWithTester() {
        assertThat(mvc.get().uri("/biblio/auteur/1"))
                .bodyText().contains("Hugo");
    }

    // Style MockMvcTester : GET all renvoie un statut 200
    @Test
    void testGetAllAuteursStatusOk() {
        assertThat(mvc.get().uri("/biblio/auteur/"))
                .hasStatusOk();
    }

    // Style MockMvcTester : recherche partielle via /searchLike
    @Test
    void testSearchLikeWithTester() {
        assertThat(mvc.get().uri("/biblio/auteur/searchLike")
                        .param("nom", "Hug")
                        .param("prenom", "Vict"))
                .bodyText().contains("Hugo");
    }

    // Style MockMvc classique + désérialisation JSON
    @Test
    void testGetAuteurByIdWithMockMvc() throws Exception {
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/biblio/auteur/1")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        Auteur auteur = objectMapper.readValue(json, Auteur.class);

        assertThat(auteur.getNom()).isEqualTo("Hugo");
        assertThat(auteur.getPrenom()).isEqualTo("Victor");
    }
}