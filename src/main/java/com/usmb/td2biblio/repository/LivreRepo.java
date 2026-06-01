package com.usmb.td2biblio.repository;

import com.usmb.td2biblio.entity.Livre;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LivreRepo extends JpaRepository<Livre, Integer> {
    List<Livre> findByAuteurId(Integer auteurId);
    List<Livre> findByTitreContainingIgnoreCase(String titre);
}
