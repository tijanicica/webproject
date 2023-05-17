package com.tim23.webproject.service;

import com.tim23.webproject.dto.KnjigaDto;
import com.tim23.webproject.dto.RecenzijaDto;
import com.tim23.webproject.entity.Knjiga;
import com.tim23.webproject.entity.Recenzija;
import com.tim23.webproject.repository.RecenzijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecenzijaService {

    @Autowired
    private RecenzijaRepository recenzijaRepository;

    public List<RecenzijaDto> getAllRecenzije() {
        List<Recenzija> recenzije = recenzijaRepository.findAll();
        List<RecenzijaDto> dtos = new ArrayList<>();
        for (Recenzija recenzija : recenzije) {
            RecenzijaDto dto = new RecenzijaDto(recenzija);
            dtos.add(dto);
        }
        return dtos;

    }
}
