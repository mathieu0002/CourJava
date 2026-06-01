package com.usmb.td2biblio.controller;

import com.usmb.td2biblio.entity.Livre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
public class LivreControllerSansAssertj {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // Teste que la liste contient bien les bons livres
    @Test
    void testGetAllLivres() {
        Livre[] livres = restTemplate.getForObject(
                "http://localhost:" + port + "/biblio/livre/", Livre[].class);

        assertNotNull(livres);
        assertTrue(livres.length > 0);
        assertEquals("Titre 1", livres[0].getTitre());
        assertEquals("Titre 2", livres[1].getTitre());
    }

    // Teste qu'on récupère bien le livre avec l'id 1
    @Test
    void testGetLivreById() {
        Livre livre = restTemplate.getForObject(
                "http://localhost:" + port + "/biblio/livre/1", Livre.class);

        assertNotNull(livre);
        assertEquals("Titre 1", livre.getTitre());
    }

    // Ce test est VOLONTAIREMENT là pour montrer un test qui échoue
    @Test
    void testGetLivreById_FAIL() {
        Livre livre = restTemplate.getForObject(
                "http://localhost:" + port + "/biblio/livre/1", Livre.class);

        assertNotNull(livre);
        assertNotEquals("Titre", livre.getTitre());
    }

    // Teste l'ajout d'un livre
    @Test
    void testSaveLivre() {
        Livre livre = new Livre();
        livre.setTitre("Spring");
        livre.setAuteur("Test");
        livre.setNbPages(2);
        livre.setEditeur("Java");
        livre.setDatePublication(LocalDate.of(1762, 4, 3));

        restTemplate.postForObject(
                "http://localhost:" + port + "/biblio/livre/", livre, String.class);

        String response = restTemplate.getForObject(
                "http://localhost:" + port + "/biblio/livre/3", String.class);

        assertNotNull(response);
        assertTrue(response.contains("Spring"));
    }

    // Teste la modification d'un livre
    @Test
    void testUpdateLivre() {
        Livre livre = restTemplate.getForObject(
                "http://localhost:" + port + "/biblio/livre/3", Livre.class);

        assertNotNull(livre);
        int newNbPages = livre.getNbPages() + 1;
        livre.setNbPages(newNbPages);

        restTemplate.put("http://localhost:" + port + "/biblio/livre/", livre);

        Livre livreUpdated = restTemplate.getForObject(
                "http://localhost:" + port + "/biblio/livre/3", Livre.class);

        assertNotNull(livreUpdated);
        assertEquals(newNbPages, livreUpdated.getNbPages());
    }
}