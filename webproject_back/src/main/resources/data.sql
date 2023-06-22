INSERT INTO POLICA (naziv, primarna) VALUES ('Want to Read', true);
INSERT INTO POLICA (naziv, primarna) VALUES ('Tijanina polica', false);
INSERT INTO POLICA (naziv, primarna) VALUES ('Read', true);
INSERT INTO POLICA (naziv, primarna) VALUES ('Currently Reading', true);

INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, mejl_adresa, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Nikola', 'Stojicic', 'nikolas', 'nikolas@gmail.com', 'nikola123', '2002-03-27','profilnaSlika2', 'student', 'CITALAC');
INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, mejl_adresa, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Tijana', 'Petrovic', 'tictic', 'tijanap@gmail.com', 'tic123', '2003-01-27', 'profilnaSlika', 'student', 'CITALAC' );
INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, mejl_adresa, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Antoan', 'Egziperi', 'antoane', 'danilo.cvijetic10@gmail.com', 'maliprinc123', '1930-01-01', 'profilnaSlika2', 'pisac', 'AUTOR');
INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, mejl_adresa, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Iva', 'Jovanovic', 'ivaj', 'ivaj@gmail.com', 'ivica123', '2002-12-28', 'profilnaSlika3', 'student', 'ADMINISTRATOR');

INSERT INTO KORISNIK_POLICE (korisnik_korisnik_id, police_polica_id) VALUES (1, 1);
INSERT INTO KORISNIK_POLICE (korisnik_korisnik_id, police_polica_id) VALUES (1, 3);
INSERT INTO KORISNIK_POLICE (korisnik_korisnik_id, police_polica_id) VALUES (1, 4);
INSERT INTO KORISNIK_POLICE (korisnik_korisnik_id, police_polica_id) VALUES (2, 2);

INSERT INTO ZANR (naziv) VALUES ('poezija');
INSERT INTO ZANR (naziv) VALUES ('decija');

INSERT INTO KNJIGA (naslov, naslovna_fotografija, knjiga_isbn,  datum_objavljivanja, broj_strana, opis, ocena, zanr_id) VALUES ('Mali Princ', 'mali-princ.jpg', 'isbn2', '1960-01-01', 90, 'Svi odrasli su nekada bili deca', 10, 2);
INSERT INTO KNJIGA (naslov, naslovna_fotografija, knjiga_isbn,  datum_objavljivanja, broj_strana, opis, ocena, zanr_id) VALUES ('Gradinar', 'naslovnafoto3', 'isbn3', '1850-01-01', 100, 'Ptica u kavezu', 10, 1);
INSERT INTO KNJIGA (naslov, naslovna_fotografija, knjiga_isbn,  datum_objavljivanja, broj_strana, opis, ocena, zanr_id) VALUES ('Nova knjiga', 'novaknjigafoto', 'isbn4', '1950-01-01', 100, 'Nova knjiga', 10, 1);

INSERT INTO AUTOR (aktivan, autor_id) VALUES (false, 3);

INSERT INTO AUTOR_SPISAK_KNJIGA(autor_autor_id, spisak_knjiga_knjiga_id) VALUES (3, 1);
INSERT INTO AUTOR_SPISAK_KNJIGA(autor_autor_id, spisak_knjiga_knjiga_id) VALUES (3, 2);

INSERT INTO RECENZIJA (ocena, tekst, datum_recenzije, korisnik_id) VALUES (8, 'Sve preporuke.','2023-03-03', 2);
INSERT INTO RECENZIJA (ocena, tekst, datum_recenzije, korisnik_id) VALUES (9, 'Odlicna knjiga.','2023-03-04', 1);

INSERT INTO STAVKAPOLICE (knjiga_id, recenzija_id ) VALUES (1, 1);
INSERT INTO STAVKAPOLICE (knjiga_id, recenzija_id) VALUES (2, 2);

INSERT INTO POLICA_STAVKA_POLICE (polica_polica_id, stavka_police_stavka_police_id) VALUES (1, 1);
INSERT INTO POLICA_STAVKA_POLICE (polica_polica_id, stavka_police_stavka_police_id) VALUES (2, 2);

INSERT INTO ZAHTEV_ZA_AKTIVACIJU_NALOGA_AUTORA (email, telefon, poruka, datum, status, autor_id) VALUES ('danilo.cvijetic10@gmail.com', '061111111', 'Nalog za aktivaciju', '2023-04-03', 'NA_CEKANJU', 3);




