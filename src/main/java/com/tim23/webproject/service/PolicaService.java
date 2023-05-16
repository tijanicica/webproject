package com.tim23.webproject.service;

import com.tim23.webproject.repository.PolicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicaService {

    @Autowired
    private PolicaRepository policaRepository;
}
