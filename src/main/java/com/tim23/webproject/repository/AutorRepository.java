package com.tim23.webproject.repository;


import com.tim23.webproject.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
/*
    @Modifying
    @Query("DELETE FROM Autor a WHERE a.id = :autorId AND EXISTS (SELECT k FROM a.spisakKnjiga k WHERE k.id = :knjigaId)")
    void deleteFromAutorSpisakKnjiga(@Param("autorId") Long autorId, @Param("knjigaId") Long knjigaId);*/
}
