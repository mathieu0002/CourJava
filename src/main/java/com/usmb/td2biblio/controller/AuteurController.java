package com.usmb.td2biblio.controller;

import com.usmb.td2biblio.entity.Auteur;
import com.usmb.td2biblio.service.AuteurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biblio/auteur")
@RequiredArgsConstructor
@Validated
public class AuteurController {
    private final AuteurService auteurService;

    @GetMapping("/")
    public ResponseEntity<List<Auteur>> getAllAuteurs() {
        return ResponseEntity.ok().body(auteurService.getAllAuteurs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auteur> getAuteurById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(auteurService.getAuteurById(id));
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<List<Auteur>> getAuteursByNom(@PathVariable String nom) {
        return ResponseEntity.ok().body(auteurService.getAuteursByNom(nom));
    }

    // /biblio/auteur/search?nom=Hugo&prenom=Victor
    @GetMapping("/search")
    public ResponseEntity<List<Auteur>> getAuteursByNomAndPrenom(
            @RequestParam String nom, @RequestParam String prenom) {
        return ResponseEntity.ok().body(auteurService.getAuteursByNomAndPrenom(nom, prenom));
    }

    // /biblio/auteur/searchLike?nom=Hug&prenom=Vict
    @GetMapping("/searchLike")
    public ResponseEntity<List<Auteur>> getAuteursByNomLikeAndPrenomLike(
            @RequestParam String nom, @RequestParam String prenom) {
        return ResponseEntity.ok().body(auteurService.getAuteursByNomLikeAndPrenomLike(nom, prenom));
    }

    @PostMapping("/")
    public ResponseEntity<Auteur> saveAuteur(@RequestBody Auteur auteur) {
        return ResponseEntity.ok().body(auteurService.saveAuteur(auteur));
    }

    @PutMapping("/")
    public ResponseEntity<Auteur> updateAuteur(@RequestBody Auteur auteur) {
        return ResponseEntity.ok().body(auteurService.updateAuteur(auteur));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuteurById(@PathVariable Integer id) {
        auteurService.deleteAuteurById(id);
        return ResponseEntity.ok().body("Deleted auteur successfully");
    }
}