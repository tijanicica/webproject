package com.tim23.webproject.repository;

import com.tim23.webproject.dto.KnjigaDto;
import com.tim23.webproject.dto.ZanrDto;
import com.tim23.webproject.entity.Knjiga;
import com.tim23.webproject.entity.Zanr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KnjigaRepository extends JpaRepository<Knjiga, Long> {

    List<Knjiga> findByNaslov(String naslov);

    @Query("SELECT k FROM Knjiga k INNER JOIN k.zanr z WHERE z.naziv = :nazivZanra")
    List<Knjiga> findByZanr(@Param("nazivZanra") String nazivZanra);

    List<Knjiga> findByOcena(int ocena);



}
