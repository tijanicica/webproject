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
import java.util.stream.Collectors;

@Service
public class PolicaService {

    @Autowired
    private PolicaRepository policaRepository;
    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private AutorRepository autorRepository;

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
 
  public void obrisiPolicu(Korisnik korisnik, Long policaId) throws Exception {
      Polica polica = policaRepository.findById(policaId).orElseThrow(() -> new EntityNotFoundException("Polica sa datim ID-om nije pronadjena."));
      if (polica.isPrimarna()) {
          throw new Exception("Nije moguće obrisati primarnu policu.");
      }

      List<Polica> listaKorisnikovihPolica = korisnik.getPolice();
      listaKorisnikovihPolica.remove(polica);

      korisnik.setPolice(listaKorisnikovihPolica);
      korisnikRepository.save(korisnik);
      for (StavkaPolice stavkaPolice : polica.getStavkaPolice()) {
          stavkaPoliceRepository.delete(stavkaPolice);
      }
      polica.getStavkaPolice().clear();

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

  public void obrisiKnjiguSaPolice(String nazivKnjige, Korisnik korisnik) {
      List<Polica> police = korisnik.getPolice();

      for (Polica polica : police) {
          List<StavkaPolice> stavkeZaBrisanje = new ArrayList<>();

          for (StavkaPolice stavkaPolice : polica.getStavkaPolice()) {
              if (stavkaPolice.getKnjiga().getNaslov().equals(nazivKnjige)) {
                  stavkeZaBrisanje.add(stavkaPolice);

                  if (polica.isPrimarna() && polica.getNaziv().equals("Read")) {
                      Recenzija recenzija = stavkaPolice.getRecenzija();
                      if (recenzija != null) {
                          recenzijaRepository.delete(recenzija);
                      }
                  }
              }
          }

          for (StavkaPolice stavkaPolice : stavkeZaBrisanje) {
              Knjiga knjiga = stavkaPolice.getKnjiga();
              Zanr zanr = knjiga.getZanr();

              // Provera da li je korisnik instanca Autora
              if (korisnik instanceof Autor) {
                  Autor autor = (Autor) korisnik;

                  // Uklanjanje knjige sa spiska knjiga autora
                  autor.getSpisakKnjiga().remove(knjiga);
                  autorRepository.save(autor); // Sačuvaj promene u bazi podataka
              }

              polica.getStavkaPolice().remove(stavkaPolice);
              knjiga.setZanr(null); // Ukloni referencu na zanr
              knjigaRepository.save(knjiga); // Sačuvaj promene u bazi podataka
              policaRepository.save(polica); // Sačuvaj promene u bazi podataka
              stavkaPoliceRepository.delete(stavkaPolice);
          }
      }
  }








}