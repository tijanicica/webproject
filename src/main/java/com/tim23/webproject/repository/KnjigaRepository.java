package com.tim23.webproject.repository;

import com.tim23.webproject.entity.Knjiga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KnjigaRepository extends JpaRepository<Knjiga, String> {

    List<Knjiga> findByNaslov(String naslov);
}
