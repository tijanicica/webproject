INSERT INTO POLICA (naziv, primarna) VALUES ('Want to Read', true);
INSERT INTO POLICA (naziv, primarna) VALUES ('Tijanina polica', false);

INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, mejl_adresa, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Nikola', 'Stojicic', 'nikolas', 'nikolas@gmail.com', 'nikola123', '2002-03-27','profilnaSlika1', 'student', 'CITALAC');
INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, mejl_adresa, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Tijana', 'Petrovic', 'tictic', 'tijanap@gmail.com', 'tic123', '2003-01-27', 'profilnaSlika', 'student', 'CITALAC' );
INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, mejl_adresa, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Antoan', 'Egziperi', 'antoane', 'antoane@gmail.com', 'maliprinc123', '1930-01-01', 'profilnaSlika2', 'pisac', 'AUTOR');

INSERT INTO KORISNIKOVE_POLICE (korisnik_id, polica_id) VALUES (1, 1);
INSERT INTO KORISNIKOVE_POLICE (korisnik_id, polica_id) VALUES (2, 1);
INSERT INTO KORISNIKOVE_POLICE (korisnik_id, polica_id) VALUES (2, 2);

INSERT INTO ZANR (naziv) VALUES ('poezija');
INSERT INTO ZANR (naziv) VALUES ('decija');

INSERT INTO KNJIGA (naslov, naslovna_fotografija, knjiga_ISBN,  datum_objavljivanja, broj_strana, opis, ocena, zanr_id) VALUES ('Mali Princ', 'naslovnafoto2', 'isbn2', '1960-01-01', 90, 'Svi odrasli su nekada bili deca', 10, 2);
INSERT INTO KNJIGA (naslov, naslovna_fotografija, knjiga_ISBN,  datum_objavljivanja, broj_strana, opis, ocena, zanr_id) VALUES ('Gradinar', 'naslovnafoto3', 'isbn3', '1850-01-01', 100, 'Ptica u kavezu', 10, 1);

INSERT INTO AUTOR (aktivan, autor_id) VALUES (true, 3);

INSERT INTO AUTOR_SPISAK_KNJIGA(autor_autor_id, spisak_knjiga_knjiga_ISBN) VALUES (3, 'isbn2');
INSERT INTO AUTOR_SPISAK_KNJIGA(autor_autor_id, spisak_knjiga_knjiga_ISBN) VALUES (3, 'isbn3');

INSERT INTO RECENZIJA (ocena, tekst, datum_recenzije, korisnik_id) VALUES (8, 'Sve preporuke.','2023-03-03', 2);
INSERT INTO RECENZIJA (ocena, tekst, datum_recenzije, korisnik_id) VALUES (9, 'Odlicna knjiga.','2023-03-04', 1);

INSERT INTO STAVKAPOLICE (recenzija_id, knjiga_isbn) VALUES (1, 'isbn2');
INSERT INTO STAVKAPOLICE (recenzija_id, knjiga_isbn) VALUES (2, 'isbn3');

INSERT INTO POLICA_STAVKA_POLICE (polica_polica_id, stavka_police_stavka_police_id) VALUES (1, 1);
INSERT INTO POLICA_STAVKA_POLICE (polica_polica_id, stavka_police_stavka_police_id) VALUES (2, 2);

INSERT INTO ZAHTEV_ZA_AKTIVACIJU_NALOGA_AUTORA (email, telefon, poruka, datum, status, autor_id) VALUES ('antoane@gmail.com', '061111111', 'Nalog za aktivaciju', '2023-04-03', 'AKTIVAN', 3);




