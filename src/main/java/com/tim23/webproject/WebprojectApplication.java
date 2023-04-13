package com.tim23.webproject;

import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Uloga;
import com.tim23.webproject.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;
import static com.tim23.webproject.entity.Uloga.AUTOR;

@SpringBootApplication
public class WebprojectApplication implements CommandLineRunner {

	@Autowired
	private KorisnikRepository korisnikRepository;

	@Override
	public void run(String... args) {

		// kreiramo novi objekat klase Korisnik
		Korisnik korisnik = new Korisnik();

		korisnik.setIme("Nikola");
		korisnik.setPrezime("Stojicic");
		korisnik.setKorisnickoIme("nikolastojicic");
		korisnik.setMejlAdresa("nikolas@gmail.com");
		korisnik.setLozinka("nikola123");
		korisnik.setDatumRodjenja("27.03.2002");
		korisnik.setProfilnaSlika("profilnaSlika1");
		korisnik.setOpis("student");
		korisnik.setUloga(Uloga.AUTOR);




		// čuvamo objekat u bazi
		this.korisnikRepository.save(korisnik);

		List<Korisnik> korisnici = this.korisnikRepository.findAll();
//		List<Employee> employees = this.employeeRepository.findAllByPositionOrderByFirstName("radnik");
//		List<Employee> employees = this.employeeRepository.findByFirstNameOrLastName("Aleksandar", "Milić");
//		List<Employee> employees = this.employeeRepository.findByFirstNameIgnoreCase("jovanka");
//		List<Employee> employees = this.employeeRepository.findByDepartmentName("Menadžment");

		for (Korisnik k : korisnici){
			System.out.println(k);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(WebprojectApplication.class, args);
	}

}
