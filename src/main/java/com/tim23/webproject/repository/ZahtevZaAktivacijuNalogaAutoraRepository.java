package com.tim23.webproject.repository;


import com.tim23.webproject.entity.ZahtevZaAktivacijuNalogaAutora;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZahtevZaAktivacijuNalogaAutoraRepository extends JpaRepository<ZahtevZaAktivacijuNalogaAutora, String> {
}

/*
    INSERT INTO ZANR (naziv) VALUES ('decija')
        INSERT INTO ZANR (naziv) VALUES ('drama')
        INSERT INTO KNJIGA (naslov, naslovnaFotografija, ISBN, datumObjavljivanja, brojStrana, opis, ocena, zanr_id) VALUES ('Bela Griva', 'foto', '1234567knjiga', '01.01.1950.', 90, 'decija knjiga', 10, 1);
        INSERT INTO KNJIGA (naslov, naslovnaFotografija, ISBN, datumObjavljivanja, brojStrana, opis, ocena, zanr_id) VALUES ('Mjastor i  Margarita', 'foto2', '12345678knjiga', '01.01.1940.', 90, 'lektira', 9, 2);
        INSERT INTO AUTOR (aktivan, autor_id, knjiga_ISBN, knjiga_ISBN) VALUES (true, 'nikolas@gmail.com', '1234567knjiga', '12345678knjiga');*/
