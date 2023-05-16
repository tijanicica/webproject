package com.tim23.webproject.controller;

import com.tim23.webproject.dto.RecenzijaDto;
import com.tim23.webproject.service.RecenzijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecenzijaRestController {

    @Autowired
    private RecenzijaService recenzijaService;

//    @GetMapping("/api/recenzije")
//    public ResponseEntity<List<RecenzijaDto>> getRecenzije() {
//        List<RecenzijaDto> recenzije = recenzijaService.getAllRecenzije();
//        return ResponseEntity.ok(recenzije);
//    }


}
