package com.tim23.webproject.controller;

import com.tim23.webproject.dto.AutorDto;
import com.tim23.webproject.dto.KnjigaDto;
import com.tim23.webproject.dto.ZahtevZaAktivacijuNalogaAutoraDto;
import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Uloga;
import com.tim23.webproject.entity.ZahtevZaAktivacijuNalogaAutora;
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
    private EmailService emailService;

    @PostMapping("api/zahtev-za-autora")
    public ResponseEntity<String> podnesiZahtevZaAktivacijuAutoraAutora(@RequestBody ZahtevZaAktivacijuNalogaAutoraDto zahtevZaAktivacijuNalogaAutoraDto) {
        String mejlAdresa = zahtevZaAktivacijuNalogaAutoraDto.getEmail();
        if (!korisnikService.imaUloguAutora(mejlAdresa)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nemate pristup zahtevu za aktivaciju autora.");
        }
        try {
            zahtevZaAktivacijuNalogaService.podnesiZahtevZaAktivacijuAutora(zahtevZaAktivacijuNalogaAutoraDto);
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

    @PostMapping("api/odbij-zahtev/{id}")
    public ResponseEntity<String> odbijZahtev(@PathVariable("id") Long zahtevId, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            zahtevZaAktivacijuNalogaService.odbijZahtev(zahtevId);
            return ResponseEntity.ok("Zahtev je uspesno odbijen.");
        } else {
            return new ResponseEntity<>("Niste administrator!", HttpStatus.BAD_REQUEST);
        }
    }

  /*  @PostMapping("api/prihvati-zahtev/{id}")
    public ResponseEntity<String> prihvatiZahtev(@PathVariable("id") Long zahtevId, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            zahtevZaAktivacijuNalogaService.prihvatiZahtev(zahtevId);
            return ResponseEntity.ok("Zahtev je uspesno prihvacen.");
        } else {
            return new ResponseEntity<>("Niste administrator!", HttpStatus.BAD_REQUEST);
        }
    }
*/




}


