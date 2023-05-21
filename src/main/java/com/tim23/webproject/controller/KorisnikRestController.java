package com.tim23.webproject.controller;

import com.tim23.webproject.dto.*;
import com.tim23.webproject.entity.*;
import com.tim23.webproject.repository.KorisnikRepository;
import com.tim23.webproject.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class KorisnikRestController {

    @Autowired
    private KorisnikService korisnikService;


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

    @GetMapping("api/profili-korisnika")
    public ResponseEntity<List<KorisnikDto>> getProfiliKorisnika() {
        List<KorisnikDto> profiliKorisnika = korisnikService.getAllKorisnik();
        return ResponseEntity.ok(profiliKorisnika);
    }

    @GetMapping("api/police-prijavljenog-korisnika")
    public ResponseEntity<List<PolicaDto>> getPolicePrijavljenogKorisnika(HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null) {
            List<Polica> policePrijavljenogKorisnika = korisnikService.getPolicePrijavljenogKorisnika(prijavljeniKorisnik.getId());
            //konvertovanje
            List<PolicaDto> policeDtoList = new ArrayList<>();
            for(Polica polica : policePrijavljenogKorisnika){
                List<StavkaPoliceDto> stavkePoliceDTOList = new ArrayList<>();

                for (StavkaPolice stavkaPolice : polica.getStavkaPolice()) {
                    Knjiga knjiga = stavkaPolice.getKnjiga();
                    Recenzija recenzija = stavkaPolice.getRecenzija();

                    RecenzijaBezKorisnikaDto recenzijaDTO = null;
                    if (recenzija != null) {
                        recenzijaDTO = new RecenzijaBezKorisnikaDto(recenzija.getOcena(), recenzija.getTekst(), recenzija.getDatumRecenzije());
                    }
                    KnjigaDto knjigaDto = null;
                    if(knjiga != null){
                        Zanr zanr = knjiga.getZanr();
                        String nazivZanra = zanr.getNaziv();
                        ZanrDto zanrDTO = new ZanrDto(nazivZanra);
                        knjigaDto = new KnjigaDto(knjiga.getNaslov(), knjiga.getNaslovnaFotografija(), knjiga.getDatumObjavljivanja(), knjiga.getBrojStrana(), knjiga.getOpis(), knjiga.getOcena(), zanrDTO);
                    }
                    stavkePoliceDTOList.add(new StavkaPoliceDto(recenzijaDTO, knjigaDto));
                }

                PolicaDto policaDTO = new PolicaDto(polica.getNaziv(), polica.isPrimarna(), stavkePoliceDTOList);
                policeDtoList.add(policaDTO);
            }
            return ResponseEntity.ok(policeDtoList);
        } else {
            return null;
        }
    }
    @PostMapping("api/kreiraj-autora")
    public ResponseEntity<String> kreirajAutora(@RequestBody AutorDto autorDto, @RequestParam String mejlAdresa, @RequestParam String lozinka, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
                AutorDto kreiraniAutor = korisnikService.kreirajAutora(autorDto, mejlAdresa, lozinka);
                return ResponseEntity.ok("Uspesno ste kreirali autora.");
        } else {
            return new ResponseEntity<>("Niste administrator!", HttpStatus.BAD_REQUEST);
        }
    }
}
