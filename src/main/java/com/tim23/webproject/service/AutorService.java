package com.tim23.webproject.service;

import com.tim23.webproject.dto.*;
import com.tim23.webproject.entity.*;
import com.tim23.webproject.repository.AutorRepository;
import com.tim23.webproject.repository.KnjigaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;

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
}
