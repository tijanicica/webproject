package com.tim23.webproject.repository;


import com.tim23.webproject.entity.Autor;
import com.tim23.webproject.entity.Knjiga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {


    @Modifying
    @Query("UPDATE Autor a SET a.spisakKnjiga = :spisakKnjiga WHERE a.id = :autorId")
    void updateSpisakKnjiga(@Param("autorId") Long autorId, @Param("spisakKnjiga") Set<Knjiga> spisakKnjiga);

}
