package com.tim23.webproject.controller;

import com.tim23.webproject.dto.AutorDto;
import com.tim23.webproject.dto.ZahtevZaAktivacijuNalogaAutoraDto;
import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Uloga;
import com.tim23.webproject.service.KorisnikService;
import com.tim23.webproject.service.ZahtevZaAktivacijuNalogaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZahtevZaAktivacijuNalogaRestController {

    @Autowired
    private ZahtevZaAktivacijuNalogaService zahtevZaAktivacijuNalogaService;
    @Autowired
    private KorisnikService korisnikService;

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); //sta je ovo?
        }
    }
}


