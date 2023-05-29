package com.tim23.webproject.service;

import com.tim23.webproject.dto.KnjigaDto;
import com.tim23.webproject.dto.RecenzijaBezKorisnikaDto;
import com.tim23.webproject.dto.RecenzijaDto;
import com.tim23.webproject.entity.Knjiga;
import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Recenzija;
import com.tim23.webproject.entity.StavkaPolice;
import com.tim23.webproject.repository.RecenzijaRepository;
import com.tim23.webproject.repository.StavkaPoliceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecenzijaService {

    @Autowired
    private RecenzijaRepository recenzijaRepository;
    @Autowired
    private StavkaPoliceRepository stavkaPoliceRepository;

    /*public List<RecenzijaDto> getAllRecenzije() {
        List<Recenzija> recenzije = recenzijaRepository.findAll();
        List<RecenzijaDto> dtos = new ArrayList<>();
        for (Recenzija recenzija : recenzije) {
            RecenzijaDto dto = new RecenzijaDto(recenzija);
            dtos.add(dto);
        }
        return dtos;

    }*/

    public List<RecenzijaBezKorisnikaDto> getAllRecenzije() {
        List<Recenzija> recenzije = recenzijaRepository.findAll();
        List<RecenzijaBezKorisnikaDto> dtos = new ArrayList<>();
        for (Recenzija recenzija : recenzije) {
            RecenzijaBezKorisnikaDto dto = new RecenzijaBezKorisnikaDto(recenzija);
            dtos.add(dto);
        }
        return dtos;

    }

    public void azurirajRecenziju(Long id, RecenzijaDto recenzijaDto, Korisnik korisnik) {
        Recenzija recenzija = recenzijaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recenzija sa datim ID-om nije pronađena."));

        // Ažuriranje recenzije sa novim vrednostima
        recenzija.setOcena(recenzijaDto.getOcena());
        recenzija.setTekst(recenzijaDto.getTekst());
        recenzija.setDatumRecenzije(recenzijaDto.getDatumRecenzije());

        // Čuvanje ažurirane recenzije
        recenzijaRepository.save(recenzija);

        // Pronalaženje odgovarajuće stavke police koja sadrži recenziju
        StavkaPolice stavkaPolice = stavkaPoliceRepository.findByRecenzija(recenzija);
        if (stavkaPolice != null) {
            // Ažuriranje recenzije u okviru stavke police
            stavkaPolice.setRecenzija(recenzija);
            stavkaPoliceRepository.save(stavkaPolice);
        }
    }
}
