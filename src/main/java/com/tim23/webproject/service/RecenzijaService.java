package com.tim23.webproject.service;

import com.tim23.webproject.dto.RecenzijaBezKorisnikaDto;
import com.tim23.webproject.dto.RecenzijaDto;
import com.tim23.webproject.entity.Knjiga;
import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Recenzija;
import com.tim23.webproject.entity.StavkaPolice;
import com.tim23.webproject.repository.KnjigaRepository;
import com.tim23.webproject.repository.KorisnikRepository;
import com.tim23.webproject.repository.RecenzijaRepository;
import com.tim23.webproject.repository.StavkaPoliceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RecenzijaService {

    @Autowired
    private RecenzijaRepository recenzijaRepository;
    @Autowired
    private StavkaPoliceRepository stavkaPoliceRepository;
    @Autowired
    private KnjigaRepository knjigaRepository;
    @Autowired
    private KorisnikRepository korisnikRepository;


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

        recenzija.setOcena(recenzijaDto.getOcena());
        recenzija.setTekst(recenzijaDto.getTekst());
        recenzija.setDatumRecenzije(recenzijaDto.getDatumRecenzije());

        recenzijaRepository.save(recenzija);


        StavkaPolice stavkaPolice = stavkaPoliceRepository.findByRecenzija(recenzija);
        if (stavkaPolice != null) {

            stavkaPolice.setRecenzija(recenzija);
            stavkaPoliceRepository.save(stavkaPolice);
        }
    }


    public void dodajNovuRecenziju(Long knjigaId, RecenzijaBezKorisnikaDto recenzijaBezKorisnikaDto, Korisnik korisnik) {
        Knjiga knjiga = knjigaRepository.findById(knjigaId)
                .orElseThrow(() -> new EntityNotFoundException("Knjiga sa datim ID-em nije pronađena."));

        Recenzija recenzija = new Recenzija();
        recenzija.setOcena(recenzijaBezKorisnikaDto.getOcena());
        recenzija.setTekst(recenzijaBezKorisnikaDto.getTekst());
        recenzija.setDatumRecenzije(new Date());

        recenzijaRepository.save(recenzija);

        StavkaPolice stavkaPolice = stavkaPoliceRepository.findByKnjigaId(knjigaId);
        if (stavkaPolice != null) {
            stavkaPolice.setRecenzija(recenzija);
            stavkaPoliceRepository.save(stavkaPolice);
        }
        korisnikRepository.save(korisnik);
    }

}
