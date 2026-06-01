package com.usmb.td2biblio.repository;

import com.usmb.td2biblio.entity.Livre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivreRepo extends JpaRepository<Livre, Integer> {
}
