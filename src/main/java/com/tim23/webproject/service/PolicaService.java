package com.tim23.webproject.service;

import com.tim23.webproject.dto.*;
import com.tim23.webproject.entity.*;
import com.tim23.webproject.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void dodajNovuPolicu(String imePolice) throws Exception {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Uloga.CITALAC)) {
            if (!imaPolicuSaImenom(prijavljeniKorisnik, imePolice)) {
                Polica novaPolica = new Polica(imePolice, false, new ArrayList<>());
                prijavljeniKorisnik.getPolice().add(novaPolica);
                korisnikRepository.save(prijavljeniKorisnik);
            } else {
                throw new Exception("Polica sa datim imenom već postoji.");
            }
        } else {
            throw new Exception("Niste prijavljeni kao čitalac.");
        }
    }
    // Pomoćna metoda za proveru da li korisnik već ima policu sa datim imenom

    public void obrisiPolicu(Korisnik korisnik, String nazivPolice) throws Exception {
        Polica polica = null;
        for (Polica p : korisnik.getPolice()) {
            if (p.getNaziv().equals(nazivPolice)) {
                polica = p;
                break;
            }
        }

        if (polica != null) {
            if (polica.isPrimarna()) {
                throw new Exception("Nije moguće obrisati primarnu policu.");
            }
            korisnik.getPolice().remove(polica);
            policaRepository.delete(polica);
            korisnikRepository.save(korisnik); // Sačuvaj promene u korisniku
        } else {
            throw new Exception("Polica sa datim nazivom ne postoji.");
        }
    }

    //DODAJ KNJIGE
  /*  public void dodajKnjiguNaPolicu(Korisnik korisnik, String nazivPrimarnePolice, String nazivKreiranePolice, Knjiga knjiga) throws Exception {
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

        // Dodavanje knjige na primarnu policu
        StavkaPolice novaStavka = new StavkaPolice(knjiga);
        primarnaPolica.getStavkaPolice().add(novaStavka);

        knjigaRepository.save(knjiga);
        stavkaPoliceRepository.save(novaStavka);
        policaRepository.save(primarnaPolica);

        // Provera i dodavanje knjige na kreiranu policu (ako je naziv prosleđen)
        if (nazivKreiranePolice != null && !nazivKreiranePolice.isEmpty()) {
            boolean dodatoNaKreiranuPolicu = false;
            for (Polica polica : korisnik.getPolice()) {
                if (polica.getNaziv().equals(nazivKreiranePolice) && !polica.isPrimarna()) {
                    StavkaPolice novaStavkaKreirane = new StavkaPolice(knjiga);
                    polica.getStavkaPolice().add(novaStavkaKreirane);
                    dodatoNaKreiranuPolicu = true;
                    knjigaRepository.save(knjiga);
                    stavkaPoliceRepository.save(novaStavka);
                    policaRepository.save(polica); // Sačuvajte kreiranu policu unutar petlje
                    break;
                }
            }
            if (!dodatoNaKreiranuPolicu) {
                throw new Exception("Nije pronađena kreirana polica sa datim nazivom.");
            }
        }
        korisnikRepository.save(korisnik);
    }
*/
    public void dodajKnjiguNaPolicu(Korisnik korisnik, String nazivPrimarnePolice, String nazivKreiranePolice, Knjiga knjiga) throws Exception {
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

        // Dodavanje knjige na primarnu policu
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
                    policaRepository.save(polica);
                    stavkaPoliceRepository.save(novaStavkaKreirane);
                    break;
                }
            }
            if (!dodatoNaKreiranuPolicu) {
                throw new Exception("Nije pronađena kreirana polica sa datim nazivom.");
            }
        }
    }



}