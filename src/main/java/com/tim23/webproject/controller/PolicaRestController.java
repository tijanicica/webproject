package com.tim23.webproject.controller;

import com.tim23.webproject.dto.*;
import com.tim23.webproject.entity.*;
import com.tim23.webproject.service.PolicaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class PolicaRestController {

    @Autowired
    private PolicaService policaService;

    @GetMapping("api/police-prijavljenog-korisnika")
    public ResponseEntity<List<PolicaDto>> getPolicePrijavljenogKorisnika(HttpSession session) {
        // Preuzimanje prijavljenog korisnika iz sesije
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (prijavljeniKorisnik != null) {
            // Poziv metode u PoliceService sloju
            List<PolicaDto> policeDtoList = policaService.getPolicePrijavljenogKorisnika(prijavljeniKorisnik.getId());

            return ResponseEntity.ok(policeDtoList);
        } else {
            // Vraćanje odgovarajućeg odgovora ako korisnik nije prijavljen
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PostMapping("/api/dodaj-novu-policu")
    public ResponseEntity<String> dodajNovuPolicu(@RequestParam String imePolice,  HttpSession session) throws Exception {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && (prijavljeniKorisnik.getUloga().equals(Uloga.CITALAC) || prijavljeniKorisnik.getUloga().equals(Uloga.AUTOR))) {
            policaService.dodajNovuPolicu(imePolice, prijavljeniKorisnik);
            return ResponseEntity.ok("Uspesno ste dodali novu policu.");
        } else {
            return new ResponseEntity<>("Neuspeno dodavanje nove police!", HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/api/obrisi-policu/{id}")
    public ResponseEntity<String> obrisiPolicu(@PathVariable(name = "id") Long policaId, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && (prijavljeniKorisnik.getUloga().equals(Uloga.CITALAC) || prijavljeniKorisnik.getUloga().equals(Uloga.AUTOR))) {
            try {
                policaService.obrisiPolicu(prijavljeniKorisnik, policaId);
                return ResponseEntity.ok("Polica uspešno obrisana.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        } else {
            return new ResponseEntity<>("Korisnik nije prijavljen.", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("api/dodaj-knjigu-na-policu")
    public ResponseEntity<String> dodajKnjiguNaPolicu(@RequestParam String nazivPrimarnePolice,
                                                      @RequestParam(required = false) String nazivKreiranePolice,
                                                      @RequestBody Knjiga knjiga,
                                                      HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && (prijavljeniKorisnik.getUloga().equals(Uloga.CITALAC) || prijavljeniKorisnik.getUloga().equals(Uloga.AUTOR))) {
            try {
                    policaService.dodajKnjiguNaPolicuBezRecenzije(prijavljeniKorisnik, nazivPrimarnePolice, nazivKreiranePolice, knjiga);
                    return ResponseEntity.ok("Knjiga uspešno dodata na policu (bez recenzije).");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        } else {
            return new ResponseEntity<>("Korisnik nije prijavljen.", HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("api/obrisi-knjigu-sa-police/{nazivKnjige}")
    public ResponseEntity<String> ukloniKnjiguSaPolice(@PathVariable String nazivKnjige, HttpSession session) throws Exception {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && (prijavljeniKorisnik.getUloga().equals(Uloga.CITALAC) || prijavljeniKorisnik.getUloga().equals(Uloga.AUTOR))) {
            policaService.obrisiKnjiguSaPolice(nazivKnjige, prijavljeniKorisnik);
            return ResponseEntity.ok("Knjiga je uspesno uklonjena sa police.");
        } else {
            return new ResponseEntity<>("Niste citalac ili autor!", HttpStatus.BAD_REQUEST);
        }
    }
}
