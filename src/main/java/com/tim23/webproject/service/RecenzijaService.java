package com.tim23.webproject.service;

import com.tim23.webproject.repository.RecenzijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecenzijaService {

    @Autowired
    private RecenzijaRepository recenzijaRepository;
}
