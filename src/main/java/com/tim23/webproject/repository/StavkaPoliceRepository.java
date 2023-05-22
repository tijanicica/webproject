package com.tim23.webproject.repository;

import com.tim23.webproject.entity.Knjiga;
import com.tim23.webproject.entity.StavkaPolice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StavkaPoliceRepository extends JpaRepository<StavkaPolice, Long> {

    StavkaPolice findByKnjiga(Knjiga knjiga);
}
