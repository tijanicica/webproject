INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, korisnik_id, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Nikola', 'Stojicic', 'nikolas', 'nikolas@gmail.com', 'nikola123', '27.03.2002','profilnaSlika1', 'student', 'CITALAC');
INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, korisnik_id, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Tijana', 'Petrovic', 'tictic', 'tijanap@gmail.com', 'tic123', '27.01.2003.', 'profilnaSlika', 'student', 'CITALAC' );
INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, korisnik_id, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Antoan', 'Egziperi', 'antoane', 'antoane@gmail.com', 'maliprinc123', '01.01.1930.', 'profilnaSlika2', 'pisac', 'AUTOR');

INSERT INTO ZANR (naziv) VALUES ('poezija');
INSERT INTO ZANR (naziv) VALUES ('decija');

INSERT INTO KNJIGA (naslov, naslovna_fotografija, knjiga_ISBN,  datum_objavljivanja, broj_strana, opis, ocena, zanr_id) VALUES ('Mali Princ', 'naslovnafoto2', 'isbn2', '01.01.1960.', 90, 'Svi odrasli su nekada bili deca', 10, 2);
INSERT INTO KNJIGA (naslov, naslovna_fotografija, knjiga_ISBN,  datum_objavljivanja, broj_strana, opis, ocena, zanr_id) VALUES ('Gradinar', 'naslovnafoto3', 'isbn3', '01.01.1850.', 100, 'Ptica u kavezu', 10, 1);

INSERT INTO AUTOR (aktivan, autor_id) VALUES (true, 'antoane@gmail.com');

INSERT INTO AUTOR_SPISAK_KNJIGA(autor_autor_id, spisak_knjiga_knjiga_ISBN) VALUES ('antoane@gmail.com', 'isbn2');
INSERT INTO AUTOR_SPISAK_KNJIGA(autor_autor_id, spisak_knjiga_knjiga_ISBN) VALUES ('antoane@gmail.com', 'isbn3');

INSERT INTO RECENZIJA (ocena, tekst, datum_recenzije, korisnik_id) VALUES (8, 'Sve preporuke.','03.03.2023.', 'tijanap@gmail.com');
INSERT INTO RECENZIJA (ocena, tekst, datum_recenzije, korisnik_id) VALUES (9, 'Odlicna knjiga.','03.04.2022.', 'nikolas@gmail.com');

INSERT INTO STAVKAPOLICE (knjiga_isbn) VALUES ('isbn2');
INSERT INTO STAVKAPOLICE (knjiga_isbn) VALUES ('isbn3');

INSERT INTO STAVKAPOLICE_RECENZIJA(stavka_police_stavka_police_id, recenzija_recenzija_id) VALUES (1, 1);
INSERT INTO STAVKAPOLICE_RECENZIJA(stavka_police_stavka_police_id, recenzija_recenzija_id) VALUES (1, 2);

INSERT INTO POLICA (naziv, primarna) VALUES ('Want to Read', true);
INSERT INTO POLICA (naziv, primarna) VALUES ('Tijanina polica', false);

INSERT INTO POLICA_STAVKA_POLICE (polica_polica_id, stavka_police_stavka_police_id) VALUES (1, 1);
INSERT INTO POLICA_STAVKA_POLICE (polica_polica_id, stavka_police_stavka_police_id) VALUES (2, 2);

INSERT INTO ZAHTEV_ZA_AKTIVACIJU_NALOGA_AUTORA (email, telefon, poruka, datum, status) VALUES ('antoane@gmail.com', '061111111', 'Nalog za aktivaciju', '16.04.2023.', 'AKTIVAN');




