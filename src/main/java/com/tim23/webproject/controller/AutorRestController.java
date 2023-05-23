package com.tim23.webproject.controller;

import com.tim23.webproject.dto.AutorDto;
import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Uloga;
import com.tim23.webproject.service.AutorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AutorRestController {

    @Autowired
    private AutorService autorService;

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
