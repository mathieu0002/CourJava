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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
public class LivreControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // Teste que la liste contient bien les bons livres
    @Test
    void testGetAllLivres() {
        assertThat(this.restTemplate.getForObject(
                "http://localhost:" + port + "/biblio/livre/", Livre[].class))
                .satisfies(livres -> {
                    assertThat(livres).isNotEmpty();
                    assertThat(livres[0].getTitre()).isEqualTo("Titre 1");
                    assertThat(livres[1].getTitre()).isEqualTo("Titre 2");
                });
    }

    // Teste qu'on récupère bien le livre avec l'id 1
    @Test
    void testGetLivreById() {
        assertThat(restTemplate.getForObject(
                "http://localhost:" + port + "/biblio/livre/1", Livre.class))
                .satisfies(livre -> livre.getTitre().equals("Titre 1"));
    }

    // Ce test est VOLONTAIREMENT là pour montrer un test qui échoue
    @Test
    void testGetLivreById_FAIL() {
        Livre livre = restTemplate.getForObject(
                "http://localhost:" + port + "/biblio/livre/1", Livre.class);
        assertFalse(livre.getTitre().equals("Titre"));
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

        this.restTemplate.postForObject(
                "http://localhost:" + port + "/biblio/livre/", livre, String.class);

        assertThat(this.restTemplate.getForObject(
                "http://localhost:" + port + "/biblio/livre/3", String.class))
                .contains("Spring");
    }

    // Teste la modification d'un livre
    @Test
    void testUpdateLivre() {
        Livre livre = restTemplate.getForObject(
                "http://localhost:" + port + "/biblio/livre/3", Livre.class);
        int newNbPages = livre.getNbPages() + 1;
        livre.setNbPages(newNbPages);

        restTemplate.put("http://localhost:" + port + "/biblio/livre/", livre);

        Livre livreUpdated = restTemplate.getForObject(
                "http://localhost:" + port + "/biblio/livre/3", Livre.class);
        assertEquals(newNbPages, livreUpdated.getNbPages());
    }
}