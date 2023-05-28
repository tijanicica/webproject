package com.tim23.webproject.controller;

import com.tim23.webproject.dto.AutorDto;
import com.tim23.webproject.dto.KnjigaDto;
import com.tim23.webproject.entity.Knjiga;
import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Uloga;
import com.tim23.webproject.service.KnjigaService;
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

    @GetMapping("api/pretraga/naslov/{naslov}") //radi kada je razmak pise se %20
    public ResponseEntity<List<KnjigaDto>> searchByKnjiga(@PathVariable(name = "naslov") String naslov) {

        List<KnjigaDto> knjige = knjigaService.searchByNaslov(naslov);

        return ResponseEntity.ok(knjige);
    }


}
