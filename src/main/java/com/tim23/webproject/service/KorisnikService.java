package com.tim23.webproject.service;

import com.tim23.webproject.dto.RegisterDto;
import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KorisnikService {

    @Autowired
    private KorisnikRepository korisnikRepository;

    public Korisnik login(String mejlAdresa, String lozinka) {
        Korisnik korisnik = korisnikRepository.getByMejlAdresa(mejlAdresa);
        if(korisnik == null || !korisnik.getLozinka().equals(lozinka))
            return null;
        return  korisnik;
    }

    //korisnikRepository.findByKorisnickoIme(registracijaDTO.getKorisnickoIme()) != null


    public void register(RegisterDto registerDto) throws Exception {
        // Provjeri da li korisničko ime već postoji
        if (korisnikRepository.existsByKorisnickoIme(registerDto.getKorisnickoIme())) {
            throw new Exception("Korisničko ime već postoji.");
        }

        // Provjeri da li email već postoji
        if (korisnikRepository.existsByMejlAdresa(registerDto.getMejlAdresa())) {
            throw new Exception("Email adresa već postoji.");
        }

        // Provjeri da li unesene lozinke se podudaraju
        if (!registerDto.getLozinka().equals(registerDto.getPonovljenaLozinka())) {
            throw new Exception("Lozinke se ne podudaraju.");
        }

        // Kreiraj novog korisnika
        Korisnik korisnik = new Korisnik();
        korisnik.setIme(registerDto.getIme());
        korisnik.setPrezime(registerDto.getPrezime());
        korisnik.setKorisnickoIme(registerDto.getKorisnickoIme());
        korisnik.setMejlAdresa(registerDto.getMejlAdresa());
        korisnik.setLozinka(registerDto.getLozinka());

        // Spremi korisnika u bazu podataka
        korisnikRepository.save(korisnik);
    }




}
