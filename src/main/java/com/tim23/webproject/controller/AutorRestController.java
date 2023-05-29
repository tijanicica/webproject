package com.tim23.webproject.controller;

import com.tim23.webproject.dto.AutorDto;
import com.tim23.webproject.dto.RecenzijaDto;
import com.tim23.webproject.entity.*;
import com.tim23.webproject.service.AutorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import org.springframework.web.bind.annotation.*;

@RestController
public class AutorRestController {

    @Autowired
    private AutorService autorService;
//dodaj knjigu i njen zanr, spoji autora i njegov spisak knjiga preko id
    //azuriraj knjigu

    @PostMapping("api/dodaj-knjigu-u-spisak-knjiga")
    public ResponseEntity<String> dodajKnjiguNaPolicu(@RequestBody Knjiga knjiga, HttpSession session) {
        Autor prijavljeniAutor = (Autor) session.getAttribute("korisnik");
        if (prijavljeniAutor != null &&  prijavljeniAutor.getUloga().equals(Uloga.AUTOR)) {
            try {
                    autorService.dodajKnjiguAutora(prijavljeniAutor, knjiga);
                    return ResponseEntity.ok("Knjiga uspešno dodata u spisak knjiga.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        } else {
            return new ResponseEntity<>("Korisnik nije prijavljen.", HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("api/azuriraj-knjigu")
    public ResponseEntity<String> azurirajRecenziju(@RequestParam String nazivKnjige, @RequestBody Knjiga knjiga, HttpSession session) {
        Autor prijavljeniAutor = (Autor) session.getAttribute("korisnik");
        if (prijavljeniAutor != null && prijavljeniAutor.getUloga().equals(Uloga.AUTOR)) {
            try {
                autorService.azurirajKnjigu(nazivKnjige, knjiga, prijavljeniAutor);
                return ResponseEntity.ok("Uspesno ste azurirali knjigu.");
            } catch (EntityNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        } else {
            return new ResponseEntity<>("Niste autor!", HttpStatus.BAD_REQUEST);
        }
    }

    /*@PutMapping("/api/azuriraj-nalog-autora/{id}")
    public ResponseEntity<String> azurirajNalogAutora(@PathVariable Long id, @RequestBody AutorDto autorDto, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            try {
                autorService.azurirajNalogAutora(id, autorDto);
                return ResponseEntity.ok("Nalog autora je uspešno azuriran.");
            } catch (EntityNotFoundException e) {
                return new ResponseEntity<>("Nalog autora sa datim ID-om nije pronadjen.", HttpStatus.NOT_FOUND);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>("Nalog autora je aktivan.Ne moze se azurirati.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Niste administrator!", HttpStatus.FORBIDDEN);
        }
    }*/
}
