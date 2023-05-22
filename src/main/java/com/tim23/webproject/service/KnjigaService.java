package com.tim23.webproject.service;

import com.tim23.webproject.dto.KnjigaDto;
import com.tim23.webproject.entity.Knjiga;
import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Polica;
import com.tim23.webproject.entity.StavkaPolice;
import com.tim23.webproject.repository.KnjigaRepository;
import com.tim23.webproject.repository.KorisnikRepository;
import com.tim23.webproject.repository.PolicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KnjigaService {

    @Autowired
    private KnjigaRepository knjigaRepository;

    @Autowired
    private PolicaRepository policaRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

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

    public void ukloniKnjiguSaPolice(String nazivKnjige, String nazivPolice, Korisnik korisnik) throws Exception {
        // Pronalaženje knjige po nazivu
        Knjiga knjiga = knjigaRepository.findByNaslovKnjige(nazivKnjige);
        if (knjiga == null) {
            throw new Exception("Knjiga sa nazivom '" + nazivKnjige + "' nije pronađena.");
        }

        // Pronalaženje police po nazivu
        Polica polica = korisnik.getPolice().stream()
                .filter(p -> p.getNaziv().equals(nazivPolice))
                .findFirst()
                .orElseThrow(() -> new Exception("Policu sa nazivom '" + nazivPolice + "' nije pronađena."));

        // Provera da li je polica primarna
        if (polica.isPrimarna()) {
            // Uklanjanje knjige sa primarne police
            polica.getStavkaPolice().removeIf(stavka -> stavka.getKnjiga().equals(knjiga));

            // Uklanjanje knjige sa svih dodatnih polica korisnika
            korisnik.getPolice().stream()
                    .filter(p -> !p.isPrimarna())
                    .forEach(p -> p.getStavkaPolice().removeIf(stavka -> stavka.getKnjiga().equals(knjiga)));
        } else {
            // Uklanjanje knjige sa dodatne police
            polica.getStavkaPolice().removeIf(stavka -> stavka.getKnjiga().equals(knjiga));
        }

        // Čuvanje promena u repozitorijumima
        knjigaRepository.save(knjiga);
        policaRepository.save(polica);
        korisnikRepository.save(korisnik);
    }
/*
    public void ukloniKnjiguSaPolice(String nazivKnjige, String nazivPolice, Korisnik korisnik) throws Exception {
        // Pronalaženje knjige po nazivu
        Knjiga knjiga = knjigaRepository.findByNaslovKnjige(nazivKnjige);
        if (knjiga == null) {
            throw new Exception("Knjiga sa nazivom '" + nazivKnjige + "' nije pronađena.");
        }

        // Pronalaženje police po nazivu
        Polica polica = korisnik.getPolice().stream()
                .filter(p -> p.getNaziv().equals(nazivPolice))
                .findFirst()
                .orElseThrow(() -> new Exception("Policu sa nazivom '" + nazivPolice + "' nije pronađena."));

        // Provera da li je polica primarna
        if (polica.isPrimarna()) {
            // Uklanjanje knjige sa primarne police
            polica.getStavkaPolice().removeIf(stavka -> stavka.getKnjiga().equals(knjiga));

            // Uklanjanje knjige sa svih dodatnih polica korisnika
            korisnik.getPolice().stream()
                    .filter(p -> !p.isPrimarna())
                    .forEach(p -> {
                        p.getStavkaPolice().removeIf(stavka -> stavka.getKnjiga().equals(knjiga));
                        policaRepository.save(p); // Čuvanje promena na dodatnim policama
                    });
        } else {
            // Uklanjanje knjige sa dodatne police
            polica.getStavkaPolice().removeIf(stavka -> stavka.getKnjiga().equals(knjiga));
        }

        // Čuvanje promena u repozitorijumima
        knjigaRepository.save(knjiga);
        policaRepository.save(polica);
    }*/

}

