package com.tim23.webproject.service;

import com.tim23.webproject.dto.*;
import com.tim23.webproject.entity.*;
import com.tim23.webproject.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

@Service
public class PolicaService {

    @Autowired
    private PolicaRepository policaRepository;
    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private KnjigaRepository knjigaRepository;

    @Autowired
    private ZanrRepository zanrRepository;
    @Autowired
    private StavkaPoliceRepository stavkaPoliceRepository;
    @Autowired
    private RecenzijaRepository recenzijaRepository;

    public List<PolicaDto> getPolicePrijavljenogKorisnika(Long korisnikId) {
        // Preuzimanje korisnika iz baze podataka
        Optional<Korisnik> optionalKorisnik = korisnikRepository.findById(korisnikId);
        if (optionalKorisnik.isPresent()) {
            Korisnik korisnik = optionalKorisnik.get();

            // Preuzimanje polica prijavljenog korisnika i konverzija u PolicaDto objekte
            List<Polica> policePrijavljenogKorisnika = korisnik.getPolice();
            List<PolicaDto> policeDtoList = new ArrayList<>();

            // Konverzija polica u PolicaDto objekte
            for (Polica polica : policePrijavljenogKorisnika) {
                // Konverzija stavki police u StavkaPoliceDto objekte
                List<StavkaPoliceDto> stavkePoliceDTOList = new ArrayList<>();
                for (StavkaPolice stavkaPolice : polica.getStavkaPolice()) {
                    // Konverzija knjige i recenzije u odgovarajuće Dto objekte
                    Knjiga knjiga = stavkaPolice.getKnjiga();
                    Recenzija recenzija = stavkaPolice.getRecenzija();

                    // Kreiranje StavkaPoliceDto objekta
                    StavkaPoliceDto stavkaPoliceDto = new StavkaPoliceDto();
                    stavkaPoliceDto.setRecenzijaDto(recenzija != null ? new RecenzijaBezKorisnikaDto(recenzija.getOcena(), recenzija.getTekst(), recenzija.getDatumRecenzije()) : null);

                    KnjigaDto knjigaDto = null;
                    if (knjiga != null) {
                        Zanr zanr = knjiga.getZanr();
                        if (zanr != null) {
                            String nazivZanra = zanr.getNaziv();
                            ZanrDto zanrDto = new ZanrDto(nazivZanra);
                            knjigaDto = new KnjigaDto(knjiga.getNaslov(), knjiga.getNaslovnaFotografija(), knjiga.getDatumObjavljivanja(), knjiga.getBrojStrana(), knjiga.getOpis(), knjiga.getOcena(), zanrDto);
                        } else {
                            knjigaDto = new KnjigaDto(knjiga.getNaslov(), knjiga.getNaslovnaFotografija(), knjiga.getDatumObjavljivanja(), knjiga.getBrojStrana(), knjiga.getOpis(), knjiga.getOcena(), null);
                        }
                    }
                    stavkaPoliceDto.setKnjigaDto(knjigaDto);

                    stavkePoliceDTOList.add(stavkaPoliceDto);
                }

                // Kreiranje PolicaDto objekta
                PolicaDto policaDto = new PolicaDto(polica.getNaziv(), polica.isPrimarna(), stavkePoliceDTOList);
                policeDtoList.add(policaDto);
            }

            return policeDtoList;
        } else {
            throw new IllegalArgumentException("Korisnik sa datim ID-em ne postoji.");
        }
    }


    private boolean imaPolicuSaImenom(Korisnik korisnik, String imePolice) {
        for (Polica polica : korisnik.getPolice()) {
            if (polica.getNaziv().equals(imePolice)) {
                return true;
            }
        }
        return false;
    }

    public void dodajNovuPolicu(String imePolice, Korisnik korisnik) throws Exception {
            if (!imaPolicuSaImenom(korisnik, imePolice)) {
                Polica novaPolica = new Polica(imePolice, false, new ArrayList<>());
                policaRepository.save(novaPolica);
                korisnik.getPolice().add(novaPolica);
                korisnikRepository.save(korisnik);
            } else {
                throw new Exception("Polica sa datim imenom već postoji.");
            }
    }
    // Pomoćna metoda za proveru da li korisnik već ima policu sa datim imenom

    public void obrisiPolicu(Korisnik korisnik, Long policaId) throws Exception {
        Polica polica = policaRepository.findById(policaId).orElseThrow(() -> new EntityNotFoundException("Polica sa datim ID-om nije pronadjena."));
            if (polica.isPrimarna()) {
                throw new Exception("Nije moguće obrisati primarnu policu.");
            }
        korisnik.getPolice().remove(polica);
        korisnikRepository.save(korisnik); // Sačuvaj promene u korisniku

        policaRepository.delete(polica);
    }

    public void dodajKnjiguNaPolicuSaRecenzijom(Korisnik korisnik, String nazivPrimarnePolice, String nazivKreiranePolice, Knjiga knjiga, Recenzija recenzija) throws Exception {
        // Pronalaženje primarne police
        Polica primarnaPolica = null;
        for (Polica polica : korisnik.getPolice()) {
            if (polica.getNaziv().equals(nazivPrimarnePolice) && polica.isPrimarna()) {
                primarnaPolica = polica;
                break;
            }
        }

        if (primarnaPolica == null) {
            throw new Exception("Nije pronađena primarna polica sa datim nazivom.");
        }

        // Čuvanje knjige u bazi podataka
        knjigaRepository.save(knjiga);

        StavkaPolice novaStavka = new StavkaPolice(knjiga);
        // Provera i dodavanje recenzije samo ako je polica primarna
        if (primarnaPolica.getNaziv().equals("Read")) {
            novaStavka.setRecenzija(recenzija);
            recenzijaRepository.save(recenzija);
        }
        primarnaPolica.getStavkaPolice().add(novaStavka);

        // Čuvanje promena u bazi podataka
        korisnikRepository.save(korisnik);
        policaRepository.save(primarnaPolica);
        stavkaPoliceRepository.save(novaStavka);





        // Provera i dodavanje knjige na kreiranu policu (ako je naziv prosleđen)
        if (nazivKreiranePolice != null && !nazivKreiranePolice.isEmpty()) {
            boolean dodatoNaKreiranuPolicu = false;
            for (Polica polica : korisnik.getPolice()) {
                if (polica.getNaziv().equals(nazivKreiranePolice) && !polica.isPrimarna()) {
                    StavkaPolice novaStavkaKreirane = new StavkaPolice(knjiga);
                    polica.getStavkaPolice().add(novaStavkaKreirane);
                    dodatoNaKreiranuPolicu = true;

                    // Čuvanje promena u bazi podataka za kreiranu policu
                    korisnikRepository.save(korisnik);
                    policaRepository.save(polica);
                    stavkaPoliceRepository.save(novaStavkaKreirane);
                    knjigaRepository.save(knjiga);
                    break;
                }
            }
            if (!dodatoNaKreiranuPolicu) {
                throw new Exception("Nije pronađena kreirana polica sa datim nazivom.");
            }
        }
    }

    public void dodajKnjiguNaPolicuBezRecenzije(Korisnik korisnik, String nazivPrimarnePolice, String nazivKreiranePolice, Knjiga knjiga) throws Exception {
        // Pronalaženje primarne police
        Polica primarnaPolica = null;
        for (Polica polica : korisnik.getPolice()) {
            if (polica.getNaziv().equals(nazivPrimarnePolice) && polica.isPrimarna()) {
                primarnaPolica = polica;
                break;
            }
        }

        if (primarnaPolica == null) {
            throw new Exception("Nije pronađena primarna polica sa datim nazivom.");
        }
        knjigaRepository.save(knjiga);
        StavkaPolice novaStavka = new StavkaPolice(knjiga);
        primarnaPolica.getStavkaPolice().add(novaStavka);

        // Čuvanje promena u bazi podataka
        korisnikRepository.save(korisnik);
        policaRepository.save(primarnaPolica);
        stavkaPoliceRepository.save(novaStavka);




        // Provera i dodavanje knjige na kreiranu policu (ako je naziv prosleđen)
        if (nazivKreiranePolice != null && !nazivKreiranePolice.isEmpty()) {
            boolean dodatoNaKreiranuPolicu = false;
            for (Polica polica : korisnik.getPolice()) {
                if (polica.getNaziv().equals(nazivKreiranePolice) && !polica.isPrimarna()) {
                    StavkaPolice novaStavkaKreirane = new StavkaPolice(knjiga);
                    polica.getStavkaPolice().add(novaStavkaKreirane);
                    dodatoNaKreiranuPolicu = true;

                    // Čuvanje promena u bazi podataka za kreiranu policu
                    korisnikRepository.save(korisnik);
                    policaRepository.save(polica);
                    stavkaPoliceRepository.save(novaStavkaKreirane);
                    knjigaRepository.save(knjiga);
                    break;
                }
            }
            if (!dodatoNaKreiranuPolicu) {
                throw new Exception("Nije pronađena kreirana polica sa datim nazivom.");
            }
        }
    }
    public void obrisiKnjigu(String nazivKnjige, Korisnik korisnik) throws Exception {
        boolean knjigaNaPrimarnoj = false;
        boolean knjigaNaDodatnim = false;

        // Provera i uklanjanje knjige sa primarne police
        for (Polica polica : korisnik.getPolice()) {
            if (polica.isPrimarna()) {
                List<StavkaPolice> stavke = polica.getStavkaPolice();
                for (Iterator<StavkaPolice> iterator = stavke.iterator(); iterator.hasNext();) {
                    StavkaPolice stavka = iterator.next();
                    if (stavka.getKnjiga().getNaslov().equals(nazivKnjige)) {
                        iterator.remove();
                        knjigaNaPrimarnoj = true;
                        break;
                    }
                }
            }
        }

        // Provera i uklanjanje knjige sa dodatnih polica
        for (Polica polica : korisnik.getPolice()) {
            if (!polica.isPrimarna()) {
                List<StavkaPolice> stavke = polica.getStavkaPolice();
                for (Iterator<StavkaPolice> iterator = stavke.iterator(); iterator.hasNext();) {
                    StavkaPolice stavka = iterator.next();
                    if (stavka.getKnjiga().getNaslov().equals(nazivKnjige)) {
                        iterator.remove();
                        knjigaNaDodatnim = true;
                        break;
                    }
                }
            }
        }

        if (!knjigaNaPrimarnoj && !knjigaNaDodatnim) {
            throw new Exception("Knjiga sa nazivom '" + nazivKnjige + "' se ne nalazi na policama.");
        }
        Knjiga knjiga = knjigaRepository.findByNaslovKnjige(nazivKnjige); // Pronalaženje knjige po nazivu
       /* StavkaPolice stavkaPolice = stavkaPoliceRepository.findByKnjiga(knjiga);
        Polica policaBrisanje = policaRepository.findByStavkaPolice(stavkaPolice);*/
        if (knjiga != null) {/*
            policaRepository.delete(policaBrisanje);
            stavkaPoliceRepository.delete(stavkaPolice);*/
            knjigaRepository.delete(knjiga); // Brisanje knjige iz baze
        }
        korisnikRepository.save(korisnik); // Sačuvaj promene u korisniku
    }

}