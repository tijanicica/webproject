package com.tim23.webproject.controller;

import com.tim23.webproject.dto.AutorDto;
import com.tim23.webproject.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutorRestController {

    @Autowired
    private AutorService autorService;

}
