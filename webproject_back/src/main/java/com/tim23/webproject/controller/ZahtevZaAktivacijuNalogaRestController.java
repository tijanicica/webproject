package com.tim23.webproject.controller;

import com.tim23.webproject.dto.AutorDto;
import com.tim23.webproject.dto.KnjigaDto;
import com.tim23.webproject.dto.ZahtevZaAktivacijuNalogaAutoraDto;
import com.tim23.webproject.entity.Autor;
import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Uloga;
import com.tim23.webproject.entity.ZahtevZaAktivacijuNalogaAutora;
import com.tim23.webproject.repository.AutorRepository;
import com.tim23.webproject.repository.KorisnikRepository;
import com.tim23.webproject.repository.ZahtevZaAktivacijuNalogaAutoraRepository;
import com.tim23.webproject.service.EmailService;
import com.tim23.webproject.service.KorisnikService;
import com.tim23.webproject.service.ZahtevZaAktivacijuNalogaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ZahtevZaAktivacijuNalogaRestController {

    @Autowired
    private ZahtevZaAktivacijuNalogaService zahtevZaAktivacijuNalogaService;
    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private ZahtevZaAktivacijuNalogaAutoraRepository zahtevZaAktivacijuNalogaAutoraRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

   /* @PostMapping("api/zahtev-za-autora/{id}")
    public ResponseEntity<String> podnesiZahtevZaAktivacijuAutoraAutora(@PathVariable("id") Long autorId,@RequestBody ZahtevZaAktivacijuNalogaAutoraDto zahtevZaAktivacijuNalogaAutoraDto) {
        String mejlAdresa = zahtevZaAktivacijuNalogaAutoraDto.getEmail();
        if (!korisnikService.imaUloguAutora(mejlAdresa)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nemate pristup zahtevu za aktivaciju autora.");
        }
        try {
            zahtevZaAktivacijuNalogaService.podnesiZahtevZaAktivacijuAutora(autorId,zahtevZaAktivacijuNalogaAutoraDto);
            return ResponseEntity.ok("Uspesno ste podneli zahtev.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }*/

    @PostMapping("api/zahtev-za-autora")
    public ResponseEntity<String> podnesiZahtevZaAktivacijuAutoraAutora(@RequestBody ZahtevZaAktivacijuNalogaAutoraDto zahtevZaAktivacijuNalogaAutoraDto) {
        String mejlAdresa = zahtevZaAktivacijuNalogaAutoraDto.getEmail();
        Autor autor = autorRepository.findByMejlAdresa(mejlAdresa);
        if (!korisnikService.imaUloguAutora(mejlAdresa)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nemate pristup zahtevu za aktivaciju autora.");
        }
        try {
            zahtevZaAktivacijuNalogaService.podnesiZahtevZaAktivacijuAutora(autor.getId(), zahtevZaAktivacijuNalogaAutoraDto);
            return ResponseEntity.ok("Uspesno ste podneli zahtev.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("api/zahtevi")
    public ResponseEntity<List<ZahtevZaAktivacijuNalogaAutoraDto>> getAllZahtevi(HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            List<ZahtevZaAktivacijuNalogaAutoraDto> zahtevi = zahtevZaAktivacijuNalogaService.getAllZahtevi();
            return ResponseEntity.ok(zahtevi);
        } else {
            return null;
        }
    }

  /*  @PostMapping("api/odbij-zahtev/{id}")
    public ResponseEntity<String> odbijZahtev(@PathVariable("id") Long zahtevId, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            zahtevZaAktivacijuNalogaService.odbijZahtev(zahtevId);
            return ResponseEntity.ok("Zahtev je uspesno odbijen.");
        } else {
            return new ResponseEntity<>("Niste administrator!", HttpStatus.BAD_REQUEST);
        }
    }*/

    @PostMapping("api/odbij-zahtev")
    public ResponseEntity<String> odbijZahtev(@RequestParam String email, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            ZahtevZaAktivacijuNalogaAutora zahtev = zahtevZaAktivacijuNalogaAutoraRepository.findByEmail(email);
            zahtevZaAktivacijuNalogaService.odbijZahtev(zahtev.getId());
            return ResponseEntity.ok("Zahtev je uspesno odbijen.");
        } else {
            return new ResponseEntity<>("Niste administrator!", HttpStatus.BAD_REQUEST);
        }
    }

    /*@PostMapping("api/prihvati-zahtev/{id}")
    public ResponseEntity<String> prihvatiZahtev(@PathVariable("id") Long zahtevId, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            zahtevZaAktivacijuNalogaService.prihvatiZahtev(zahtevId);
            return ResponseEntity.ok("Zahtev je uspesno prihvacen.");
        } else {
            return new ResponseEntity<>("Niste administrator!", HttpStatus.BAD_REQUEST);
        }
    }*/

    @PostMapping("api/prihvati-zahtev")
    public ResponseEntity<String> prihvatiZahtev(@RequestParam String email, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            ZahtevZaAktivacijuNalogaAutora zahtev = zahtevZaAktivacijuNalogaAutoraRepository.findByEmail(email);
            zahtevZaAktivacijuNalogaService.prihvatiZahtev(zahtev.getId());
            return ResponseEntity.ok("Zahtev je uspesno prihvacen.");
        } else {
            return new ResponseEntity<>("Niste administrator!", HttpStatus.BAD_REQUEST);
        }
    }





}


