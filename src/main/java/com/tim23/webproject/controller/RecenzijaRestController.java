package com.tim23.webproject.controller;

import com.tim23.webproject.dto.RecenzijaDto;
import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Uloga;
import com.tim23.webproject.service.RecenzijaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecenzijaRestController {

    @Autowired
    private RecenzijaService recenzijaService;

    @GetMapping("api/recenzije")
    public ResponseEntity<List<RecenzijaDto>> getRecenzije() {
        List<RecenzijaDto> recenzije = recenzijaService.getAllRecenzije();
        return ResponseEntity.ok(recenzije);
    }

    @PutMapping("api/azuriraj/{id}")
    public ResponseEntity<String> azurirajRecenziju(@PathVariable Long id, @RequestBody RecenzijaDto recenzijaDto, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && (prijavljeniKorisnik.getUloga().equals(Uloga.CITALAC) || prijavljeniKorisnik.getUloga().equals(Uloga.AUTOR))) {
            try {
                recenzijaService.azurirajRecenziju(id, recenzijaDto, prijavljeniKorisnik);
                return ResponseEntity.ok("Uspesno ste azurirali recenziju.");
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
