
INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, korisnik_id, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Tijana', 'Petrovic', 'tictic', 'tijanap@gmail.com', 'tic123', '27.01.2003.', 'profilnaSlika', 'student', 0 );
INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, korisnik_id, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Antoan', 'Egziperi', 'antoane', 'antoane@gmail.com', 'maliprinc123', '01.01.1930.', 'profilnaSlika2', 'pisac', 1);

INSERT INTO ZANR (naziv) VALUES ('poezija');
INSERT INTO ZANR (naziv) VALUES ('decija');

INSERT INTO AUTOR (aktivan, autor_id) VALUES (true, 'antoane@gmail.com');

INSERT INTO KNJIGA (naslov, naslovna_fotografija, knjiga_ISBN,  datum_objavljivanja, broj_strana, opis, ocena, zanr_id, autor_id) VALUES ('Mali Princ', 'naslovnafoto2', 'isbn2', '01.01.1960.', 90, 'Svi odrasli su nekada bili deca', 10, 2, 'antoane@gmail.com');
INSERT INTO KNJIGA (naslov, naslovna_fotografija, knjiga_ISBN,  datum_objavljivanja, broj_strana, opis, ocena, zanr_id, autor_id) VALUES ('Gradinar', 'naslovnafoto3', 'isbn3', '01.01.1850.', 100, 'Ptica u kavezu', 10, 1, 'antoane@gmail.com');

INSERT INTO STAVKAPOLICE (knjiga_isbn) VALUES ('isbn2');

INSERT INTO RECENZIJA (ocena, tekst, datum_recenzije, korisnik_id, stavka_police_id) VALUES (8, 'Sve preporuke.','03.03.2023.', 'tijanap@gmail.com', 1);


