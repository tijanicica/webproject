package com.tim23.webproject.controller;

import com.tim23.webproject.dto.AutorDto;
import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Uloga;
import com.tim23.webproject.service.AutorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutorRestController {

    @Autowired
    private AutorService autorService;

    @PostMapping("api/kreiraj-autora")
    public ResponseEntity<AutorDto> kreirajAutora(@RequestBody AutorDto autorDto, @RequestParam String mejlAdresa, @RequestParam String lozinka, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            try {
                AutorDto kreiraniAutor = autorService.kreirajAutora(autorDto, mejlAdresa, lozinka);
                return ResponseEntity.ok(kreiraniAutor);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(null);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } else {
            return null;
        }
    }
}
