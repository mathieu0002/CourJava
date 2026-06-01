package com.usmb.td2biblio.controller;

import com.usmb.td2biblio.entity.Livre;
import com.usmb.td2biblio.service.LivreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biblio/livre")  // ← était "/biblio/v1"
@RequiredArgsConstructor
@Validated
public class LivreController {
    private final LivreService livreService;

    /**
     * Récupère tous les livres
     * @return liste de tous les livres
     */
    @GetMapping("/")
    public ResponseEntity<List<Livre>> getAllLivre() {
        return ResponseEntity.ok(livreService.getAllLivres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livre> getLivreById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(livreService.getLivreById(id));
    }

    @PostMapping("/")           // ← était "/post"
    public ResponseEntity<Livre> saveLivre(@RequestBody Livre livre) {
        return ResponseEntity.ok().body(livreService.saveLivre(livre));
    }

    @PutMapping("/")            // ← était "/update"
    public ResponseEntity<Livre> updateLivre(@RequestBody Livre livre) {
        return ResponseEntity.ok().body(livreService.updateLivre(livre));
    }

    @DeleteMapping("/{id}")     // ← était "/delete/{id}"
    public ResponseEntity<String> deleteLivreById(@PathVariable Integer id) {
        livreService.deleteLivre(id);
        return ResponseEntity.ok().body("Livre successfully deleted");
    }

    // /biblio/livre/auteur/{auteurId}
    @GetMapping("/auteur/{auteurId}")
    public ResponseEntity<List<Livre>> getLivresByAuteurId(@PathVariable Integer auteurId) {
        return ResponseEntity.ok().body(livreService.getByAuteurId(auteurId));
    }

    // /biblio/livre/search?titre=miséra
    @GetMapping("/search")
    public ResponseEntity<List<Livre>> getLivresByTitreContaining(@RequestParam String titre) {
        return ResponseEntity.ok().body(livreService.getByTitreContainingIgnoreCase(titre));
    }
}