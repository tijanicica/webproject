package com.tim23.webproject.controller;

import com.tim23.webproject.dto.KnjigaDto;
import com.tim23.webproject.service.KnjigaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KnjigaRestController {

    @Autowired
    private KnjigaService knjigaService;

    @GetMapping("api/knjige")
    public ResponseEntity<List<KnjigaDto>> getKnjige() {
        List<KnjigaDto> knjige = knjigaService.getAllKnjige();
        return ResponseEntity.ok(knjige);
    }

    @GetMapping("/pretraga/naslov/{naslov}")
    public ResponseEntity<List<KnjigaDto>> searchByKnjiga(@PathVariable String naslov) {

        List<KnjigaDto> knjige = knjigaService.searchByNaslov(naslov);

        return ResponseEntity.ok(knjige);
    }
}
