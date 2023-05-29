package com.tim23.webproject.controller;

import com.tim23.webproject.dto.AutorDto;
import com.tim23.webproject.dto.KnjigaDto;
import com.tim23.webproject.dto.ZanrDto;
import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Uloga;
import com.tim23.webproject.entity.Zanr;
import com.tim23.webproject.entity.Knjiga;
import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Uloga;
import com.tim23.webproject.service.KnjigaService;
import jakarta.servlet.http.HttpSession;
import com.tim23.webproject.service.PolicaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KnjigaRestController {

    @Autowired
    private KnjigaService knjigaService;

    @Autowired
    private PolicaService policaService;

    @GetMapping("api/knjige") //radi
    public ResponseEntity<List<KnjigaDto>> getKnjige() {
        List<KnjigaDto> knjige = knjigaService.getAllKnjige();
        return ResponseEntity.ok(knjige);
    }

    @GetMapping("api/pretraga/knjiga/naslov/{naslov}") //radi kada je razmak pise se %20
    public ResponseEntity<List<KnjigaDto>> searchByKnjiga(@PathVariable(name = "naslov") String naslov) {

        List<KnjigaDto> knjige = knjigaService.searchByNaslov(naslov);

        return ResponseEntity.ok(knjige);
    }


    @GetMapping("api/pretraga/knjiga/zanr/{naziv}")
    public ResponseEntity<List<KnjigaDto>> searchByZanr(@PathVariable(name = "naziv") String naziv) {

        List<KnjigaDto> knjige = knjigaService.searchByZanr(naziv);

        return ResponseEntity.ok(knjige);
    }

    @GetMapping("api/pretraga/knjiga/ocena/{ocena}")
    public ResponseEntity<List<KnjigaDto>> searchByZanr(@PathVariable(name = "ocena") int ocena) {

        List<KnjigaDto> knjige = knjigaService.searchByOcena(ocena);

        return ResponseEntity.ok(knjige);
    }



    @PostMapping("/api/dodaj-knjigu")
    public ResponseEntity<String> dodajKnjigu(@RequestBody KnjigaDto knjigaDto, HttpSession session) {

        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)){
            knjigaService.dodajKnjigu(knjigaDto);
            return ResponseEntity.ok("Uspesno dodata knjiga.");
        } else {
            return new ResponseEntity<>("Niste administrator!", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/api/azuriraj-knjigu/{id}")
    public ResponseEntity<String> azurirajKnjigu(@PathVariable("id") Long knjigaId, @RequestBody KnjigaDto knjigaDto, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)){
            knjigaService.azurirajKnjigu(knjigaId, knjigaDto);
            return ResponseEntity.ok("Uspesno azurirana knjiga.");
        } else {
            return new ResponseEntity<>("Niste administrator!", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/api/obrisi-knjigu/{id}")
    public ResponseEntity<String> obrisiKnjigu(@PathVariable("id") Long knjigaId, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            knjigaService.obrisiKnjigu(knjigaId);
            return ResponseEntity.ok("Knjiga je uspesno obrisana.");
        } else {
            return new ResponseEntity<>("Niste administrator!", HttpStatus.FORBIDDEN);
        }

    }
}
