package com.tim23.webproject.controller;

import com.tim23.webproject.dto.*;
import com.tim23.webproject.entity.*;
import com.tim23.webproject.repository.KnjigaRepository;
import com.tim23.webproject.repository.PolicaRepository;
import com.tim23.webproject.service.KnjigaService;
import com.tim23.webproject.service.PolicaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class PolicaRestController {

    @Autowired
    private PolicaService policaService;
    @Autowired
    private PolicaRepository policaRepository;
    @Autowired
    private KnjigaRepository knjigaRepository;

    @GetMapping("api/police-prijavljenog-korisnika")
    public ResponseEntity<List<PolicaDto>> getPolicePrijavljenogKorisnika(HttpSession session) {

        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (prijavljeniKorisnik != null) {
            List<PolicaDto> policeDtoList = policaService.getPolicePrijavljenogKorisnika(prijavljeniKorisnik.getId());
            return ResponseEntity.ok(policeDtoList);
        } else {
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

    @DeleteMapping("/api/obrisi-policu-naziv")
    public ResponseEntity<String> obrisiPolicu(@RequestParam String nazivPolice, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && (prijavljeniKorisnik.getUloga().equals(Uloga.CITALAC) || prijavljeniKorisnik.getUloga().equals(Uloga.AUTOR))) {
            try {
                Polica policaPoNazivu = policaRepository.findByNaziv(nazivPolice);
                policaService.obrisiPolicu(prijavljeniKorisnik, policaPoNazivu.getId());
                return ResponseEntity.ok("Polica uspešno obrisana.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        } else {
            return new ResponseEntity<>("Korisnik nije prijavljen.", HttpStatus.UNAUTHORIZED);
        }
    }



    //
    /*@PostMapping("/api/dodaj-knjigu-na-policu")
    public ResponseEntity<String> dodajKnjiguNaPolicu(@RequestParam String nazivPrimarnePolice,
                                                      @RequestParam(required = false) String nazivKreiranePolice,
                                                      @RequestBody KnjigaDto knjigaDto,
                                                      HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && (prijavljeniKorisnik.getUloga().equals(Uloga.CITALAC) || prijavljeniKorisnik.getUloga().equals(Uloga.AUTOR))) {
            try {
                    policaService.dodajKnjiguNaPolicuBezRecenzije(prijavljeniKorisnik, nazivPrimarnePolice, nazivKreiranePolice, knjigaDto);
                    return ResponseEntity.ok("Knjiga uspešno dodata na policu (bez recenzije).");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        } else {
            return new ResponseEntity<>("Korisnik nije prijavljen.", HttpStatus.UNAUTHORIZED);
        }
    }*/

    @PostMapping("/api/dodaj-knjigu-na-policu")
    public ResponseEntity<String> dodajKnjiguNaPolicu(@RequestParam String nazivPrimarnePolice,
                                                      @RequestParam(required = false) String nazivKreiranePolice,
                                                      @RequestParam String nazivKnjige,
                                                      HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && (prijavljeniKorisnik.getUloga().equals(Uloga.CITALAC) || prijavljeniKorisnik.getUloga().equals(Uloga.AUTOR))) {
            try {
                policaService.dodajKnjiguNaPolicuBezRecenzije(prijavljeniKorisnik, nazivPrimarnePolice, nazivKreiranePolice, nazivKnjige);
                return ResponseEntity.ok("Knjiga uspešno dodata na policu (bez recenzije).");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        } else {
            return new ResponseEntity<>("Korisnik nije prijavljen.", HttpStatus.UNAUTHORIZED);
        }
    }
    /*
    @DeleteMapping("api/obrisi-knjigu-sa-police/{knjigaId}/{policaId}")
    public ResponseEntity<String> ukloniKnjiguSaPolice(@PathVariable(name = "knjigaId") Long knjigaId, @PathVariable(name = "policaId") Long policaId,  HttpSession session) throws Exception {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && (prijavljeniKorisnik.getUloga().equals(Uloga.CITALAC) || prijavljeniKorisnik.getUloga().equals(Uloga.AUTOR))) {
            policaService.obrisiKnjiguSaPolice(knjigaId, policaId, prijavljeniKorisnik);
            return ResponseEntity.ok("Knjiga je uspesno uklonjena sa police.");
        } else {
            return new ResponseEntity<>("Niste citalac ili autor!", HttpStatus.BAD_REQUEST);
        }
    }
    */
   /* @DeleteMapping("api/obrisi-knjigu-sa-police")
    public ResponseEntity<String> ukloniKnjiguSaPolice(@RequestParam String nazivKnjige, @RequestParam String nazivPolice, HttpSession session) throws Exception {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && (prijavljeniKorisnik.getUloga().equals(Uloga.CITALAC) || prijavljeniKorisnik.getUloga().equals(Uloga.AUTOR))) {
            List<Knjiga> knjigeBaza = knjigaRepository.findByNaslov(nazivKnjige);
            if (knjigeBaza.isEmpty()) {
                // Handle the case when no book with the given title is found
                return new ResponseEntity<>("Nema knjige sa datim naslovom.", HttpStatus.NOT_FOUND);
            } else if (knjigeBaza.size() > 1) {
                // Handle the case when multiple books with the given title are found
                return new ResponseEntity<>("Pronađeno je više knjiga sa istim naslovom.", HttpStatus.CONFLICT);
            }

            Knjiga knjigaBaza = knjigeBaza.get(0);
            Polica policaBaza = policaRepository.findByNaziv(nazivPolice);
            if (policaBaza != null) {
                policaService.obrisiKnjiguSaPolice(knjigaBaza.getId(), policaBaza.getId(), prijavljeniKorisnik);
                return ResponseEntity.ok("Knjiga je uspešno uklonjena sa police.");
            } else {
                return new ResponseEntity<>("Policu sa datim nazivom nije moguće pronaći.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Niste čitalac ili autor!", HttpStatus.BAD_REQUEST);
        }
    }*/



    @DeleteMapping("api/obrisi-knjigu-sa-police")
    public ResponseEntity<String> ukloniKnjiguSaPolice(@RequestParam String nazivKnjige, @RequestParam String nazivPolice, HttpSession session) throws Exception {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && (prijavljeniKorisnik.getUloga().equals(Uloga.CITALAC) || prijavljeniKorisnik.getUloga().equals(Uloga.AUTOR))) {
            Knjiga knjigaBaza = knjigaRepository.findKnjigaByNaslov(nazivKnjige);
            if (knjigaBaza == null) {
                // Handle the case when no book with the given title is found
                return new ResponseEntity<>("Nema knjige sa datim naslovom.", HttpStatus.NOT_FOUND);
            }

            Polica policaBaza = policaRepository.findByNaziv(nazivPolice);
            if (policaBaza != null) {
                policaService.obrisiKnjiguSaPolice(knjigaBaza.getId(), policaBaza.getId(), prijavljeniKorisnik);
                return ResponseEntity.ok("Knjiga je uspešno uklonjena sa police.");
            } else {
                return new ResponseEntity<>("Policu sa datim nazivom nije moguće pronaći.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Niste čitalac ili autor!", HttpStatus.BAD_REQUEST);
        }
    }




}



 /*   @PostMapping("/api/dodaj-knjigu-na-policu")
    public ResponseEntity<String> dodajKnjiguNaKreiranuPolicu(@RequestParam(required = false) String nazivKreiranePolice,
                                                              @RequestParam String nazivKnjige,
                                                      HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && (prijavljeniKorisnik.getUloga().equals(Uloga.CITALAC) || prijavljeniKorisnik.getUloga().equals(Uloga.AUTOR))) {
            try {
                policaService.dodajKnjiguNaPolicuBezRecenzije(prijavljeniKorisnik, nazivPrimarnePolice, nazivKreiranePolice, nazivKnjige);
                return ResponseEntity.ok("Knjiga uspešno dodata na policu (bez recenzije).");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        } else {
            return new ResponseEntity<>("Korisnik nije prijavljen.", HttpStatus.UNAUTHORIZED);
        }
    }*/


