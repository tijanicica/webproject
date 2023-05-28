package com.tim23.webproject.repository;

import com.tim23.webproject.entity.Knjiga;
import com.tim23.webproject.entity.Recenzija;
import com.tim23.webproject.entity.StavkaPolice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StavkaPoliceRepository extends JpaRepository<StavkaPolice, Long> {
    StavkaPolice findByKnjiga(Knjiga knjiga);

    @Query("SELECT sp FROM StavkaPolice sp WHERE sp.recenzija = :recenzija")
    StavkaPolice findByRecenzija(@Param("recenzija") Recenzija recenzija);
}
