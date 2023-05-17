package com.tim23.webproject.service;

import com.tim23.webproject.dto.KorisnikDto;
import com.tim23.webproject.dto.RecenzijaDto;
import com.tim23.webproject.dto.RegisterDto;
import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Recenzija;
import com.tim23.webproject.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public void register(RegisterDto registerDto) throws Exception {
        if (korisnikRepository.existsByKorisnickoIme(registerDto.getKorisnickoIme())) {
            throw new Exception("Korisničko ime već postoji.");
        }

        if (korisnikRepository.existsByMejlAdresa(registerDto.getMejlAdresa())) {
            throw new Exception("Email adresa već postoji.");
        }

        if (!registerDto.getLozinka().equals(registerDto.getPonovljenaLozinka())) {
            throw new Exception("Lozinke se ne podudaraju.");
        }

        Korisnik korisnik = new Korisnik();
        korisnik.setIme(registerDto.getIme());
        korisnik.setPrezime(registerDto.getPrezime());
        korisnik.setKorisnickoIme(registerDto.getKorisnickoIme());
        korisnik.setMejlAdresa(registerDto.getMejlAdresa());
        korisnik.setLozinka(registerDto.getLozinka());

        korisnikRepository.save(korisnik);
    }

    public List<KorisnikDto> getAllKorisnik() {
        List<Korisnik> korisnici = korisnikRepository.findAll();
        List<KorisnikDto> dtos = new ArrayList<>();
        for (Korisnik korisnik : korisnici) {
            KorisnikDto dto = new KorisnikDto(korisnik);
            dtos.add(dto);
        }
        return dtos;

    }



}
