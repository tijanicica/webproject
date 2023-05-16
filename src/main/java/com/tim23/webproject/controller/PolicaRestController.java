package com.tim23.webproject.controller;

import com.tim23.webproject.service.PolicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PolicaRestController {

    @Autowired
    private PolicaService policaService;
}
