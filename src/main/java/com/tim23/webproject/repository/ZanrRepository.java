package com.tim23.webproject.repository;

import com.tim23.webproject.entity.Zanr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZanrRepository extends JpaRepository<Zanr, Long> {
}


/* INSERT INTO ZANR (naziv) VALUES ('drama')
INSERT INTO ZANR (naziv) VALUES ('decija')
INSERT INTO KNJIGA(naslov, naslovna_fotografija, kniga_ISBN,  datum_objavljivanja, broj_strana, opis, ocena, zanr_id) VALUES ('Mali Princ', 'naslovnafoto2', 'isbn2', '01.01.1960.', 90, 'Svi odrasli su nekada bili deca', 10, 2)*/