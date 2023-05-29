package com.tim23.webproject.controller;

import com.tim23.webproject.dto.*;
import com.tim23.webproject.entity.*;
import com.tim23.webproject.repository.KorisnikRepository;
import com.tim23.webproject.service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class KorisnikRestController {

    @Autowired
    private KorisnikService korisnikService;




    @PostMapping("api/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpSession session){
        // proverimo da li su podaci validni
        if(loginDto.getMejlAdresa().isEmpty() || loginDto.getLozinka().isEmpty())
            return new ResponseEntity("Neispravni podaci za prijavu", HttpStatus.BAD_REQUEST);

        Korisnik prijavljeniKorisnik = korisnikService.login(loginDto.getMejlAdresa(), loginDto.getLozinka());
        if (prijavljeniKorisnik == null)
            return new ResponseEntity<>("Korisnik ne postoji!", HttpStatus.NOT_FOUND);


        session.setAttribute("korisnik", prijavljeniKorisnik);
        return ResponseEntity.ok("Uspesna prijava!");
    }

    @PostMapping("api/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        try {
            korisnikService.register(registerDto);
            return ResponseEntity.ok("Registracija uspesna.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); //sta je ovo?
        }
    }

    @GetMapping("api/profili-korisnika")
    public ResponseEntity<List<KorisnikDto>> getProfiliKorisnika() {
        List<KorisnikDto> profiliKorisnika = korisnikService.getAllKorisnik();
        return ResponseEntity.ok(profiliKorisnika);
    }

    @PostMapping("api/kreiraj-autora")
    public ResponseEntity<String> kreirajAutora(@RequestBody AutorDto autorDto, @RequestParam String mejlAdresa, @RequestParam String lozinka, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
                AutorDto kreiraniAutor = korisnikService.kreirajAutora(autorDto, mejlAdresa, lozinka);
                return ResponseEntity.ok("Uspesno ste kreirali autora.");
        } else {
            return new ResponseEntity<>("Niste administrator!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("api/azuriraj-profil/{korisnikId}")
    public ResponseEntity<String> azurirajProfil(
            @PathVariable("korisnikId") Long korisnikId,
            @RequestParam(required = false) String ime,
            @RequestParam(required = false) String prezime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate datumRodjenja,
            @RequestParam(required = false) String profilnaSlika,
            @RequestParam(required = false) String opis,
            @RequestParam(required = false) String mejlAdresa,
            @RequestParam(required = false) String novaLozinka,
            @RequestParam(required = false) String trenutnaLozinka,
            HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Uloga.CITALAC)) {
            try {
                korisnikService.azurirajProfil(korisnikId, ime, prezime, datumRodjenja, profilnaSlika, opis, mejlAdresa, novaLozinka, trenutnaLozinka);
                return ResponseEntity.ok("Uspesno ste azurirali vas profil.");
            } catch (EntityNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        } else {
            return new ResponseEntity<>("Niste citalac!", HttpStatus.BAD_REQUEST);
        }
    }

}
