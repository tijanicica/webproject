package com.tim23.webproject.controller;

import com.tim23.webproject.dto.ZanrDto;
import com.tim23.webproject.repository.ZanrRepository;
import com.tim23.webproject.service.ZanrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}


