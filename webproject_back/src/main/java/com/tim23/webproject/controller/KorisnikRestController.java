package com.tim23.webproject.controller;

import com.tim23.webproject.dto.*;
import com.tim23.webproject.entity.*;
import com.tim23.webproject.service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class KorisnikRestController {

    @Autowired
    private KorisnikService korisnikService;

    @GetMapping("/api/")
    public String welcome(){
        return "Dobrodosli u bukovacku biblioteku. Bilbiotekarka Ljilja vas pozdravlja :)!";
    }

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

    @PostMapping("api/logout")
    public ResponseEntity Logout(HttpSession session){
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (prijavljeniKorisnik == null)
            return new ResponseEntity("Forbidden", HttpStatus.FORBIDDEN);

        session.invalidate();
        return new ResponseEntity("Uspesna odjava", HttpStatus.OK);
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

    @GetMapping("api/pretraga/korisnik/ime/{ime}") //radi kada je razmak pise se %20
    public ResponseEntity<List<KorisnikDto>> searchByIme(@PathVariable(name = "ime") String ime) {

        List<KorisnikDto> korisnici = korisnikService.searchByIme(ime);

        return ResponseEntity.ok(korisnici);
    }

    @GetMapping("api/pretraga/korisnik/prezime/{prezime}") //radi kada je razmak pise se %20
    public ResponseEntity<List<KorisnikDto>> searchByPrezime(@PathVariable(name = "prezime") String prezime) {

        List<KorisnikDto> korisnici = korisnikService.searchByPrezime(prezime);

        return ResponseEntity.ok(korisnici);
    }

    @GetMapping("api/pretraga/korisnik/korisnickoIme/{korisnickoIme}") //radi kada je razmak pise se %20
    public ResponseEntity<KorisnikDto> searchByKorisnickoIme(@PathVariable(name = "korisnickoIme") String korisnickoIme) {

        KorisnikDto korisnikDto = korisnikService.searchByKorisnickoIme(korisnickoIme);

        return ResponseEntity.ok(korisnikDto);
    }

    @GetMapping("api/pretraga/korisnik/uloga/{uloga}") //radi kada je razmak pise se %20
    public ResponseEntity<List<KorisnikDto>> searchByUloga(@PathVariable(name = "uloga") String uloga) {

        List<KorisnikDto> korisnici = korisnikService.searchByUloga(uloga);

        return ResponseEntity.ok(korisnici);
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
        if (prijavljeniKorisnik != null && (prijavljeniKorisnik.getUloga().equals(Uloga.CITALAC) || prijavljeniKorisnik.getUloga().equals(Uloga.AUTOR))) {
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


     @PutMapping("/api/azuriraj-nalog-autora/{id}")
    public ResponseEntity<String> azurirajNalogAutora(@PathVariable("id") Long autorId, @RequestBody AutorDto autorDto, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            try {
                korisnikService.azurirajProfilAutora(autorId, autorDto);
                return ResponseEntity.ok("Nalog autora je uspešno azuriran.");
            } catch (EntityNotFoundException e) {
                return new ResponseEntity<>("Nalog autora sa datim ID-om nije pronadjen.", HttpStatus.NOT_FOUND);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>("Nalog autora je aktivan.Ne moze se azurirati.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Niste administrator!", HttpStatus.FORBIDDEN);
        }
    }


}
