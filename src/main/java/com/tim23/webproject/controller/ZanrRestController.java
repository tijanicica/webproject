package com.tim23.webproject.controller;

import com.tim23.webproject.repository.ZanrRepository;
import com.tim23.webproject.service.ZanrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZanrRestController {

    @Autowired
    private ZanrService zanrService;

}


