package com.usmb.td2biblio.controller;

import com.usmb.td2biblio.entity.Auteur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
public class AuteurControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url(String path) {
        return "http://localhost:" + port + "/biblio/auteur" + path;
    }

    // GET all : la liste contient bien les auteurs semés
    @Test
    void testGetAllAuteurs() {
        Auteur[] auteurs = restTemplate.getForObject(url("/"), Auteur[].class);

        assertThat(auteurs).isNotNull();
        assertThat(auteurs).isNotEmpty();
        assertThat(auteurs[0].getNom()).isEqualTo("Hugo");
    }

    // GET by id : l'auteur 1 est bien Victor Hugo
    @Test
    void testGetAuteurById() {
        Auteur auteur = restTemplate.getForObject(url("/1"), Auteur.class);

        assertThat(auteur).isNotNull();
        assertThat(auteur.getNom()).isEqualTo("Hugo");
        assertThat(auteur.getPrenom()).isEqualTo("Victor");
    }

    // GET /nom/{nom}
    @Test
    void testGetAuteursByNom() {
        Auteur[] auteurs = restTemplate.getForObject(url("/nom/Hugo"), Auteur[].class);

        assertThat(auteurs).isNotNull();
        assertThat(auteurs).hasSizeGreaterThanOrEqualTo(1);
        assertThat(auteurs[0].getPrenom()).isEqualTo("Victor");
    }

    // GET /search?nom=&prenom=  (égalité stricte)
    @Test
    void testSearchByNomAndPrenom() {
        Auteur[] auteurs = restTemplate.getForObject(
                url("/search?nom=Hugo&prenom=Victor"), Auteur[].class);

        assertThat(auteurs).isNotNull();
        assertThat(auteurs).hasSizeGreaterThanOrEqualTo(1);
        assertThat(auteurs[0].getNationalite()).isEqualTo("Français");
    }

    // GET /searchLike?nom=Hug&prenom=Vict  (recherche partielle)
    @Test
    void testSearchLike() {
        Auteur[] auteurs = restTemplate.getForObject(
                url("/searchLike?nom=Hug&prenom=Vict"), Auteur[].class);

        assertThat(auteurs).isNotNull();
        assertThat(auteurs).anySatisfy(a -> assertThat(a.getNom()).isEqualTo("Hugo"));
    }

    // POST : ajoute un auteur et vérifie qu'il est bien créé
    @Test
    void testSaveAuteur() {
        Auteur nouveau = new Auteur();
        nouveau.setNom("Camus");
        nouveau.setPrenom("Albert");
        nouveau.setNationalite("Français");
        nouveau.setDateNaissance(LocalDate.of(1913, 11, 7));
        nouveau.setDateDeces(LocalDate.of(1960, 1, 4));

        Auteur cree = restTemplate.postForObject(url("/"), nouveau, Auteur.class);

        assertThat(cree).isNotNull();
        assertThat(cree.getId()).isNotNull();        // id auto-généré par la base
        assertThat(cree.getNom()).isEqualTo("Camus");

        // on relit l'auteur via son id généré (pas d'id en dur)
        Auteur relu = restTemplate.getForObject(url("/" + cree.getId()), Auteur.class);
        assertThat(relu).isNotNull();
        assertThat(relu.getPrenom()).isEqualTo("Albert");
    }

    // PUT : modifie un auteur
    @Test
    void testUpdateAuteur() {
        // on crée l'auteur à modifier pour ne pas dépendre des données semées
        Auteur base = new Auteur();
        base.setNom("Test");
        base.setPrenom("Initial");
        Auteur cree = restTemplate.postForObject(url("/"), base, Auteur.class);

        cree.setPrenom("Modifié");
        restTemplate.put(url("/"), cree);

        Auteur modifie = restTemplate.getForObject(url("/" + cree.getId()), Auteur.class);
        assertThat(modifie).isNotNull();
        assertThat(modifie.getPrenom()).isEqualTo("Modifié");
    }

    // DELETE : supprime un auteur puis vérifie qu'il n'existe plus
    @Test
    void testDeleteAuteur() {
        Auteur base = new Auteur();
        base.setNom("ASupprimer");
        Auteur cree = restTemplate.postForObject(url("/"), base, Auteur.class);
        Integer id = cree.getId();

        restTemplate.delete(url("/" + id));

        // getAuteurById renvoie null quand l'auteur est absent
        Auteur apres = restTemplate.getForObject(url("/" + id), Auteur.class);
        assertThat(apres).isNull();
    }
}