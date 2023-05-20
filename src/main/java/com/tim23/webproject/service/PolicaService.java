package com.tim23.webproject.service;

import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Polica;
import com.tim23.webproject.repository.KorisnikRepository;
import com.tim23.webproject.repository.PolicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicaService {

    @Autowired
    private PolicaRepository policaRepository;
    @Autowired
    private KorisnikRepository korisnikRepository;


}
