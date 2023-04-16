package com.tim23.webproject;

import com.tim23.webproject.entity.*;
import com.tim23.webproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.tim23.webproject.entity.Uloga.AUTOR;

@SpringBootApplication
public class WebprojectApplication implements CommandLineRunner {

	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	private KnjigaRepository knjigaRepository;
	@Autowired
	private ZanrRepository zanrRepository;
	@Autowired
	private AutorRepository autorRepository;

	@Autowired
	private StavkaPoliceRepository stavkaPoliceRepository;
	@Autowired
	private RecenzijaRepository recenzijaRepository;


	@Override
	public void run(String... args) {

		// kreiramo novi objekat klase Korisnik
		/*Korisnik korisnik = new Korisnik();

		korisnik.setIme("Nikola");
		korisnik.setPrezime("Stojicic");
		korisnik.setKorisnickoIme("nikolastojicic");
		korisnik.setMejlAdresa("nikolas@gmail.com");
		korisnik.setLozinka("nikola123");
		korisnik.setDatumRodjenja("27.03.2002");
		korisnik.setProfilnaSlika("profilnaSlika1");
		korisnik.setOpis("student");
		korisnik.setUloga(Uloga.CITALAC);

		// čuvamo objekat u bazi
		this.korisnikRepository.save(korisnik);*/

		List<Korisnik> korisnici = this.korisnikRepository.findAll();
		for (Korisnik k : korisnici){
			System.out.println(k);
		}

		/*Zanr zanr = new Zanr();
		zanr.setNaziv("drama");
		this.zanrRepository.save(zanr);*/

		List<Zanr> zanrovi = this.zanrRepository.findAll();
		for (Zanr z : zanrovi) {
			System.out.println(z);
		}

		/*Autor autor = new Autor();
		autor.setIme("Mihail");
		autor.setPrezime("Bulgakov");
		autor.setKorisnickoIme("mihbul");
		autor.setMejlAdresa("mihbul@gmail.com");
		autor.setLozinka("mih123");
		autor.setDatumRodjenja("01.01.1910.");
		autor.setProfilnaSlika("profilnaslika4");
		autor.setOpis("pisac");
		autor.setUloga(AUTOR);
		autor.setAktivan(true);
		//autor.setSpisakKnjiga(spisakKnjiga);

		this.autorRepository.save(autor);*/

		List<Autor> autori = this.autorRepository.findAll();
		for (Autor a : autori) {
			System.out.println(a);
		}


		/*Knjiga knjiga  = new Knjiga();

		knjiga.setNaslov("Majstor i Margarita");
		knjiga.setNaslovnaFotografija("naslovnafoto1");
		knjiga.setISBN("isbn1");
		knjiga.setDatumObjavljivanja("01.01.1940");
		knjiga.setBrojStrana(550);
		knjiga.setOpis("Macka u Rusiji");
		knjiga.setZanr(zanr);
		knjiga.setOcena(10);
		knjiga.setAutor(autor);

		this.knjigaRepository.save(knjiga);*/

		List<Knjiga> knjige = this.knjigaRepository.findAll();
		for (Knjiga k : knjige) {
			System.out.println(k);
		}

		/*Set<Knjiga> spisakKnjiga = new HashSet<>();
		spisakKnjiga.add(knjiga);*/


		/*Autor autor = new Autor();
		autor.setIme("Mihail");
		autor.setPrezime("Bulgakov");
		autor.setKorisnickoIme("mihbul");
		autor.setMejlAdresa("mihbul@gmail.com");
		autor.setLozinka("mih123");
		autor.setDatumRodjenja("01.01.1910.");
		autor.setProfilnaSlika("profilnaslika4");
		autor.setOpis("pisac");
		autor.setUloga(AUTOR);
		autor.setAktivan(true);
		autor.setSpisakKnjiga(spisakKnjiga);

		this.autorRepository.save(autor);

		List<Autor> autori = this.autorRepository.findAll();
		for (Autor a : autori) {
			System.out.println(a);
		}*/

		/*Recenzija recenzija = new Recenzija();
		recenzija.setOcena(6);
		recenzija.setTekst("Solidno.");
		recenzija.setDatumRecenzije("03.04.2022.");
		recenzija.setKorisnik(korisnik);

		this.recenzijaRepository.save(recenzija);*/

		List<StavkaPolice> stavkePolice = this.stavkaPoliceRepository.findAll();
		for (StavkaPolice s: stavkePolice) {
			System.out.println(s);
		}

		List<Recenzija> recenzije = this.recenzijaRepository.findAll();
		for (Recenzija r : recenzije) {
			System.out.println(r);
		}



	}

	public static void main(String[] args) {
		SpringApplication.run(WebprojectApplication.class, args);
	}

}
