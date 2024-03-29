package com.tim23.webproject.repository;

import com.tim23.webproject.entity.Polica;
import com.tim23.webproject.entity.StavkaPolice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicaRepository extends JpaRepository<Polica, Long> {

    Polica findByStavkaPolice(StavkaPolice stavkaPolice);
    Polica findByNaziv(String naziv);


}
