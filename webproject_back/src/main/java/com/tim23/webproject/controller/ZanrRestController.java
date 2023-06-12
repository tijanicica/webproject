package com.tim23.webproject.controller;

import com.tim23.webproject.dto.ZanrDto;
import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Uloga;
import com.tim23.webproject.service.ZanrService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ZanrRestController {

    @Autowired
    private ZanrService zanrService;

    @GetMapping("/api/zanrovi")
    public ResponseEntity<List<ZanrDto>> getZanrovi() {
        List<ZanrDto> zanrovi = zanrService.getAllZanrovi();
        return ResponseEntity.ok(zanrovi);
    }

    @GetMapping("api/pretraga/naziv/{naziv}")
    public ResponseEntity<ZanrDto> searchByNaziv(@PathVariable(name = "naziv") String naziv) {

        ZanrDto zanrDto = zanrService.searchByNaziv(naziv);

        return ResponseEntity.ok(zanrDto);
    }

    @PostMapping("/api/dodaj-zanr")
    public ResponseEntity<String> dodajZanr(@RequestBody ZanrDto zanrDto, HttpSession session) {

        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)){
            zanrService.dodajZanr(zanrDto);
            return ResponseEntity.ok("Uspesno dodat zanr.");
        } else {
            return new ResponseEntity<>("Niste administrator!", HttpStatus.FORBIDDEN);
        }
    }




}


