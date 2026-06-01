package com.usmb.td2biblio.service;

import com.usmb.td2biblio.entity.Livre;
import com.usmb.td2biblio.repository.LivreRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LivreService {
    private final LivreRepo livreRepo;

    public List<Livre> getAllLivres() {
        return livreRepo.findAll();
    }

    public Livre getLivreById(Integer id) {
        Optional<Livre> optionalLivre = livreRepo.findById(id);
        if (optionalLivre.isPresent()) {
            return optionalLivre.get();
        }
        log.info("Livre id {} not found", id);
        return null;
    }

    public Livre saveLivre(Livre livre) {
        livre.setCreatedAt(LocalDateTime.now());
        livre.setUpdatedAt(LocalDateTime.now());
        Livre savedLivre = livreRepo.save(livre);

        log.info("Livre saved with id {}", savedLivre.getId());
        return savedLivre;
    }

    public Livre updateLivre(Livre livre) {
        Optional<Livre> existingLivre = livreRepo.findById(livre.getId());
        livre.setCreatedAt(existingLivre.get().getCreatedAt());
        livre.setUpdatedAt(LocalDateTime.now());

        Livre updatedLivre = livreRepo.save(livre);

        log.info("Livre updated with id {}", updatedLivre.getId());
        return updatedLivre;
    }

    public void deleteLivre(Integer id) {
        livreRepo.deleteById(id);
        log.info("Livre id {} deleted", id);
    }

    public List<Livre> getByAuteurId(Integer auteurId) {
        return livreRepo.findByAuteurId(auteurId);
    }

    public List<Livre> getByTitreContainingIgnoreCase(String titre) {
        return livreRepo.findByTitreContainingIgnoreCase(titre);
    }
}
