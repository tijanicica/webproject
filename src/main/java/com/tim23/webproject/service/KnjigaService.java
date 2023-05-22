package com.tim23.webproject.service;

import com.tim23.webproject.dto.KnjigaDto;
import com.tim23.webproject.dto.ZanrDto;
import com.tim23.webproject.entity.Knjiga;
import com.tim23.webproject.entity.StavkaPolice;
import com.tim23.webproject.entity.Zanr;
import com.tim23.webproject.repository.KnjigaRepository;
import com.tim23.webproject.repository.StavkaPoliceRepository;
import com.tim23.webproject.repository.ZanrRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KnjigaService {

    @Autowired
    private KnjigaRepository knjigaRepository;
    @Autowired
    private ZanrRepository zanrRepository;
    @Autowired
    private StavkaPoliceRepository stavkaPoliceRepository;

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

    public void dodajKnjigu(KnjigaDto knjigaDto) {
        //convert zanr
        ZanrDto zanrdto = knjigaDto.getZanr();
        Zanr zanr = new Zanr();
        zanr.setNaziv(zanrdto.getNaziv());
        zanrRepository.save(zanr);
        Knjiga knjiga = new Knjiga(knjigaDto.getNaslov(), knjigaDto.getNaslovnaFotografija(), knjigaDto.getDatumObjavljivanja(), knjigaDto.getBrojStrana(), knjigaDto.getOpis(), knjigaDto.getOcena(), zanr);
        knjigaRepository.save(knjiga);
        StavkaPolice stavka = new StavkaPolice();
        stavka.setKnjiga(knjiga);
        stavkaPoliceRepository.save(stavka);

    }

    public void azurirajKnjigu(Long knjigaId, KnjigaDto knjigaDto) {
        Knjiga knjiga = knjigaRepository.findById(knjigaId).orElseThrow(() -> new EntityNotFoundException("Knjiga sa datim ID-om nije pronadjena."));

        knjiga.setNaslov(knjigaDto.getNaslov());
        knjiga.setNaslovnaFotografija(knjigaDto.getNaslovnaFotografija());
        knjiga.setDatumObjavljivanja(knjigaDto.getDatumObjavljivanja());
        knjiga.setBrojStrana(knjigaDto.getBrojStrana());
        knjiga.setOpis(knjigaDto.getOpis());
        knjiga.setOcena(knjigaDto.getOcena());

        ZanrDto zanrDto = knjigaDto.getZanr();
        if (zanrDto != null) {
            Zanr zanr = new Zanr(zanrDto.getNaziv());
            knjiga.setZanr(zanr);
            zanrRepository.save(zanr);
        } else {
            knjiga.setZanr(null);
        }

        knjigaRepository.save(knjiga);
    }

    public void obrisiKnjigu(Long knjigaId) {
        Knjiga knjiga = knjigaRepository.findById(knjigaId).orElseThrow(() -> new EntityNotFoundException("Knjiga sa datim ID-om nije pronadjena."));

        StavkaPolice stavkePolice = stavkaPoliceRepository.findByKnjiga(knjiga);

        if (stavkePolice != null && stavkePolice.getRecenzija() == null && stavkePolice.getKnjiga() != null) {
            stavkaPoliceRepository.delete(stavkePolice);
            knjigaRepository.delete(knjiga);
        } else {
            throw new RuntimeException("Knjiga ima recenziju i ne može biti obrisana.");
        }
    }


}


