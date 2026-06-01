package com.usmb.td2biblio.service;

import com.usmb.td2biblio.entity.Auteur;
import com.usmb.td2biblio.repository.AuteurRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuteurService {
    private final AuteurRepo auteurRepo;

    public List<Auteur> getAllAuteurs() {
        return auteurRepo.findAll();
    }

    public Auteur getAuteurById(Integer id) {
        return auteurRepo.findById(id).orElse(null);
    }

    public Auteur saveAuteur(Auteur auteur) {
        return auteurRepo.save(auteur);
    }

    public Auteur updateAuteur(Auteur auteur) {
        return auteurRepo.save(auteur);
    }

    public void deleteAuteurById(Integer id) {
        auteurRepo.deleteById(id);
    }

    public List<Auteur> getAuteursByNom(String nom) {
        return auteurRepo.findByNom(nom);
    }

    public List<Auteur> getAuteursByNomAndPrenom(String nom, String prenom) {
        return auteurRepo.findByNomAndPrenom(nom, prenom);
    }

    public List<Auteur> getAuteursByNomLikeAndPrenomLike(String nom, String prenom) {
        return auteurRepo.findByNomLikeAndPrenomLike("%" + nom + "%", "%" + prenom + "%");
    }
}