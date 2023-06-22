package com.tim23.webproject.repository;

import com.tim23.webproject.entity.Recenzija;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecenzijaRepository extends JpaRepository<Recenzija, Long> {
    Recenzija findByTekst(String tekst);
}
