package com.tim23.webproject.controller;

import com.tim23.webproject.dto.KnjigaDto;
import com.tim23.webproject.dto.LoginDto;
import com.tim23.webproject.dto.RegisterDto;
import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.repository.KorisnikRepository;
import com.tim23.webproject.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KorisnikRestController {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private KnjigaService knjigaService;

    @Autowired
    private RecenzijaService recenzijaService;

    @Autowired
    private ZanrService zanrService;
    @Autowired
    private PolicaService policaService;

    @PostMapping("api/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpSession session){
        // proverimo da li su podaci validni
        if(loginDto.getMejlAdresa().isEmpty() || loginDto.getLozinka().isEmpty())
            return new ResponseEntity("Neispravni podaci za prijavu", HttpStatus.BAD_REQUEST);

        Korisnik prijavljeniKorisnik = korisnikService.login(loginDto.getMejlAdresa(), loginDto.getLozinka());
        if (prijavljeniKorisnik == null)
            return new ResponseEntity<>("Korisnik ne postoji!", HttpStatus.NOT_FOUND);

        session.setAttribute("korisnik", prijavljeniKorisnik);
        return ResponseEntity.ok("Uspesna prijava!");
    }

    @PostMapping("api/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        try {
            korisnikService.register(registerDto);
            return ResponseEntity.ok("Registracija uspesna.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); //sta je ovo?
        }
    }




    /*

    @Autowired
    private ProfilKorisnikaService profilKorisnikaService;

    @GetMapping("api/knjige")
    public ResponseEntity<List<KnjigaDTO>> getKnjige() {
        List<KnjigaDTO> knjige = knjigaService.getAllKnjige();
        return ResponseEntity.ok(knjige);
    }

    @GetMapping("/recenzije")
    public ResponseEntity<List<RecenzijaDTO>> getRecenzije() {
        List<RecenzijaDTO> recenzije = recenzijaService.getAllRecenzije();
        return ResponseEntity.ok(recenzije);
    }

    @GetMapping("/zanrovi")
    public ResponseEntity<List<ZanrDTO>> getZanrovi() {
        List<ZanrDTO> zanrovi = zanrService.getAllZanrovi();
        return ResponseEntity.ok(zanrovi);
    }

    @GetMapping("/profili-korisnika")
    public ResponseEntity<List<ProfilKorisnikaDTO>> getProfiliKorisnika() {
        List<ProfilKorisnikaDTO> profiliKorisnika = profilKorisnikaService.getAllProfiliKorisnika();
        return ResponseEntity.ok(profiliKorisnika);
    }

    @GetMapping("/police")
    public ResponseEntity<List<PolicaDTO>> getPolice() {
        List<PolicaDTO> police = policaService.getAllPolice();
        return ResponseEntity.ok(police);
    }

    @GetMapping("/knjige/{id}/recenzije")
    public ResponseEntity<List<RecenzijaDTO>> getRecenzijeKnjige(@PathVariable Long id) {
        List<RecenzijaDTO> recenzije = recenzijaService.getRecenzijeByKnjigaId(id);
        return ResponseEntity.ok(recenzije);
    }*/



}
