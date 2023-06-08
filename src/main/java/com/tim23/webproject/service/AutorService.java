package com.tim23.webproject.service;

import com.tim23.webproject.entity.*;
import com.tim23.webproject.repository.AutorRepository;
import com.tim23.webproject.repository.KnjigaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private KnjigaRepository knjigaRepository;

    public void dodajKnjiguAutora(Autor autor, Knjiga knjiga) {
        autor.getSpisakKnjiga().add(knjiga);
        autorRepository.save(autor);
    }

    public void azurirajKnjigu(String nazivKnjige, Knjiga novaKnjiga, Autor autor) {
        Knjiga staraKnjiga = knjigaRepository.findByNaslovKnjige(nazivKnjige);

        if (staraKnjiga == null) {
            throw new EntityNotFoundException("Knjiga sa nazivom " + nazivKnjige + " nije pronađena.");
        }

        // Ažuriranje polja noveKnjige
        staraKnjiga.setNaslov(novaKnjiga.getNaslov());
        staraKnjiga.setNaslovnaFotografija(novaKnjiga.getNaslovnaFotografija());
        staraKnjiga.setISBN(novaKnjiga.getISBN());
        staraKnjiga.setDatumObjavljivanja(novaKnjiga.getDatumObjavljivanja());
        staraKnjiga.setBrojStrana(novaKnjiga.getBrojStrana());
        staraKnjiga.setOpis(novaKnjiga.getOpis());
        staraKnjiga.setOcena(novaKnjiga.getOcena());
        staraKnjiga.setZanr(novaKnjiga.getZanr());

        knjigaRepository.save(staraKnjiga);
    }


    public void obrisiKnjiguIzSpiskaKnjiga(Long knjigaId, Autor autor) {
        Knjiga knjiga = knjigaRepository.findById(knjigaId).orElseThrow(() -> new EntityNotFoundException("Knjiga sa datim ID-om nije pronadjena."));
        Autor autorBaza = autorRepository.getById(autor.getId());
        Set<Knjiga> spisakKnjiga = autorBaza.getSpisakKnjiga();
        if (spisakKnjiga != null) {
            spisakKnjiga.remove(knjiga);
            Set<Knjiga> noviSpisakKnjiga = new HashSet<>();
            for (Knjiga knjigaNova : spisakKnjiga) {
                noviSpisakKnjiga.add(knjigaNova);
            }
            autorBaza.setSpisakKnjiga(noviSpisakKnjiga);
            autorRepository.save(autorBaza);
        }
    }

}
