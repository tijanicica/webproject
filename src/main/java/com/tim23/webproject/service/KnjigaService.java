package com.tim23.webproject.service;

import com.tim23.webproject.dto.KnjigaDto;
import com.tim23.webproject.entity.Knjiga;
import com.tim23.webproject.repository.KnjigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KnjigaService {

    @Autowired
    private KnjigaRepository knjigaRepository;

    public List<KnjigaDto> getAllKnjige() {
        List<Knjiga> knjige = knjigaRepository.findAll();

        List<KnjigaDto> dtos = new ArrayList<>();
            for (Knjiga knjiga : knjige) {
                KnjigaDto dto = new KnjigaDto(knjiga);
                dtos.add(dto);
            }
            return dtos;
    }

    public List<KnjigaDto> searchByNaslov(String naslov) {
        List<Knjiga> knjige = knjigaRepository.findByNaslov(naslov);

        List<KnjigaDto> dtos = new ArrayList<>();
        for (Knjiga knjiga : knjige) {
            KnjigaDto dto = new KnjigaDto(knjiga);
            dtos.add(dto);
        }
        return dtos;
    }
}

