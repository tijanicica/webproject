package com.tim23.webproject.service;

import com.tim23.webproject.dto.KnjigaDto;
import com.tim23.webproject.dto.RecenzijaDto;
import com.tim23.webproject.dto.ZanrDto;
import com.tim23.webproject.entity.Knjiga;
import com.tim23.webproject.entity.Recenzija;
import com.tim23.webproject.entity.Zanr;
import com.tim23.webproject.repository.ZanrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ZanrService {

    @Autowired
    private ZanrRepository zanrRepository;

    public List<ZanrDto> getAllZanrovi() {
        List<Zanr> zanrovi = zanrRepository.findAll();
        List<ZanrDto> dtos = new ArrayList<>();
        for (Zanr zanr : zanrovi) {
            ZanrDto dto = new ZanrDto(zanr);
            dtos.add(dto);
        }
        return dtos;
    }

    public ZanrDto searchByNaziv(String naziv) {
        Zanr zanr = zanrRepository.findByNaziv(naziv);

        ZanrDto zanrDto = new ZanrDto(zanr);

        return zanrDto;
    }
    public void dodajZanr(ZanrDto zanrDto) {
        Zanr zanr = new Zanr(zanrDto.getNaziv());
        zanrRepository.save(zanr);
    }
}
