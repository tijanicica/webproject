package com.tim23.webproject.repository;

import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Polica;
import com.tim23.webproject.entity.Uloga;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {

    Korisnik getByMejlAdresa(String mejlAdresa);

    Korisnik findByKorisnickoIme(String korisnickoIme);
    boolean existsByKorisnickoIme(String korisnickoIme);
    boolean existsByMejlAdresa(String MejlAdresa);

    @Query("SELECT p FROM Korisnik k JOIN k.police p WHERE k.id = :korisnikId")
    List<Polica> findPoliceByKorisnikId(@Param("korisnikId") Long korisnikId);
    //NIKOLA
    Korisnik findByMejlAdresa(String mejlAdresa);
   /* @Modifying
    @Query("DELETE FROM KORISNIK_POLICE WHERE KORISNIK_KORISNIK_ID = :korisnikId AND POLICE_POLICA_ID = :policaId")
    void deletePolica(Long korinikId, Long policaId);
*/

    List<Korisnik> findByIme(String ime);
    List<Korisnik> findByPrezime(String prezime);


    List<Korisnik> findByUloga(Uloga uloga);

}
