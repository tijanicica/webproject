package com.tim23.webproject.controller;

import com.tim23.webproject.dto.RecenzijaBezKorisnikaDto;
import com.tim23.webproject.dto.RecenzijaDto;
import com.tim23.webproject.entity.Knjiga;
import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Recenzija;
import com.tim23.webproject.entity.Uloga;
import com.tim23.webproject.repository.KnjigaRepository;
import com.tim23.webproject.repository.RecenzijaRepository;
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

    @Autowired
    private KnjigaRepository knjigaRepository;

    @Autowired
    private RecenzijaRepository recenzijaRepository;

    @GetMapping("api/recenzije")
    public ResponseEntity<List<RecenzijaBezKorisnikaDto>> getRecenzije() {
        List<RecenzijaBezKorisnikaDto> recenzije = recenzijaService.getAllRecenzije();
        return ResponseEntity.ok(recenzije);
    }
    //dodaj recenziju id knjige kojoj dodaje

   /* @PutMapping("api/azuriraj-recenziju/{id}")
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
    }*/

    @PutMapping("api/azuriraj-recenziju")
    public ResponseEntity<String> azurirajRecenziju(@RequestParam String tekst, @RequestBody RecenzijaBezKorisnikaDto recenzijaDto, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (prijavljeniKorisnik != null && (prijavljeniKorisnik.getUloga().equals(Uloga.CITALAC) || prijavljeniKorisnik.getUloga().equals(Uloga.AUTOR))) {
            try {
                Recenzija recenzijaBaza = recenzijaRepository.findByTekst(tekst);
                recenzijaService.azurirajRecenziju(recenzijaBaza.getId(), recenzijaDto, prijavljeniKorisnik);
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



 /*   @PostMapping("/api/dodaj-recenziju/{knjigaId}")
    public ResponseEntity<String> dodajRecenziju(@PathVariable(name = "knjigaId") Long knjigaId,  @RequestBody RecenzijaBezKorisnikaDto recenzijaBezKorisnikaDto, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && (prijavljeniKorisnik.getUloga().equals(Uloga.CITALAC) || prijavljeniKorisnik.getUloga().equals(Uloga.AUTOR))){
            recenzijaService.dodajNovuRecenziju(knjigaId, recenzijaBezKorisnikaDto, prijavljeniKorisnik);
            return ResponseEntity.ok("Uspesno dodata recenzija.");
        } else {
            return new ResponseEntity<>("Niste citalac ili autor!", HttpStatus.FORBIDDEN);
        }
    }*/

 /*   @PostMapping("/api/dodaj-recenziju")
    public ResponseEntity<String> dodajRecenziju(@RequestParam String nazivKnjige, @RequestBody RecenzijaBezKorisnikaDto recenzijaBezKorisnikaDto, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && (prijavljeniKorisnik.getUloga().equals(Uloga.CITALAC) || prijavljeniKorisnik.getUloga().equals(Uloga.AUTOR))) {
            List<Knjiga> knjigeBaza = knjigaRepository.findByNaslov(nazivKnjige);

            if (knjigeBaza.isEmpty()) {
                return new ResponseEntity<>("Knjiga nije pronađena!", HttpStatus.NOT_FOUND);
            }
            Knjiga knjigaBaza = knjigeBaza.get(0);
            recenzijaService.dodajNovuRecenziju(knjigaBaza.getId(), recenzijaBezKorisnikaDto, prijavljeniKorisnik);
            return ResponseEntity.ok("Uspesno dodata recenzija.");
        } else {
            return new ResponseEntity<>("Niste citalac ili autor!", HttpStatus.FORBIDDEN);
        }
    }*/

    @PostMapping("/api/dodaj-recenziju")
    public ResponseEntity<String> dodajRecenziju(@RequestParam String nazivKnjige, @RequestBody RecenzijaBezKorisnikaDto recenzijaBezKorisnikaDto, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && (prijavljeniKorisnik.getUloga().equals(Uloga.CITALAC) || prijavljeniKorisnik.getUloga().equals(Uloga.AUTOR))) {
            Knjiga knjigaBaza = knjigaRepository.findKnjigaByNaslov(nazivKnjige);
            if (knjigaBaza == null) {
                return new ResponseEntity<>("Knjiga nije pronađena!", HttpStatus.NOT_FOUND);
            }
            recenzijaService.dodajNovuRecenziju(knjigaBaza.getId(), recenzijaBezKorisnikaDto, prijavljeniKorisnik);
            return ResponseEntity.ok("Uspesno dodata recenzija.");
        } else {
            return new ResponseEntity<>("Niste citalac ili autor!", HttpStatus.FORBIDDEN);
        }
    }




}
