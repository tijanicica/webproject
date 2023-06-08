package com.tim23.webproject.controller;

import com.tim23.webproject.service.StavkaPoliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StavkaPoliceRestController {


    @Autowired
    private StavkaPoliceService stavkaPoliceService;
}
