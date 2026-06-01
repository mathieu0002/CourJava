package com.usmb.td2biblio.repository;

import com.usmb.td2biblio.entity.Auteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuteurRepo extends JpaRepository<Auteur, Integer> {
    List<Auteur> findByNom(String nom);
    List<Auteur> findByNomAndPrenom(String nom, String prenom);
    List<Auteur> findByNomLikeAndPrenomLike(String nom, String prenom);
}