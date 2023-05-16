package com.tim23.webproject.controller;

import com.tim23.webproject.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutorRestController {

    @Autowired
    private AutorService autorService;

}
