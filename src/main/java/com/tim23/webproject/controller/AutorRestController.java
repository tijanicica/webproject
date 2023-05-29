package com.tim23.webproject.controller;

import com.tim23.webproject.dto.AutorDto;
import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Uloga;
import com.tim23.webproject.service.AutorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutorRestController {

    @Autowired
    private AutorService autorService;
//dodaj knjigu i njen zanr, spoji autora i njegov spisak knjiga preko id
    //azuriraj knjiug
}
