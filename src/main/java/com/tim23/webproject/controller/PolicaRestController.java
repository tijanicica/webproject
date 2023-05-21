package com.tim23.webproject.controller;

import com.tim23.webproject.dto.*;
import com.tim23.webproject.entity.*;
import com.tim23.webproject.service.PolicaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PolicaRestController {

    @Autowired
    private PolicaService policaService;

    @PostMapping("/api/dodaj-novu-policu")
    public ResponseEntity<String> dodajNovuPolicu(@RequestParam String imePolice,  HttpSession session) throws Exception {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Uloga.CITALAC)) {
            policaService.dodajNovuPolicu(imePolice);
            return ResponseEntity.ok("Uspesno ste dodali novu policu.");
        } else {
            return new ResponseEntity<>("Neuspeno dodavanje nove police!", HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/api/obrisi-policu/{nazivPolice}")
    public ResponseEntity<String> obrisiPolicu(@PathVariable String nazivPolice, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null) {
            try {
                policaService.obrisiPolicu(prijavljeniKorisnik, nazivPolice);
                return ResponseEntity.ok("Polica uspešno obrisana.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        } else {
            return new ResponseEntity<>("Korisnik nije prijavljen.", HttpStatus.UNAUTHORIZED);
        }
    }
}
