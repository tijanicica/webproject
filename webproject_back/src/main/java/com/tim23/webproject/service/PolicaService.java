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

        Optional<Korisnik> optionalKorisnik = korisnikRepository.findById(korisnikId);
        if (optionalKorisnik.isPresent()) {
            Korisnik korisnik = optionalKorisnik.get();

            List<Polica> policePrijavljenogKorisnika = korisnik.getPolice();
            List<PolicaDto> policeDtoList = new ArrayList<>();

            for (Polica polica : policePrijavljenogKorisnika) {

                List<StavkaPoliceDto> stavkePoliceDTOList = new ArrayList<>();
                for (StavkaPolice stavkaPolice : polica.getStavkaPolice()) {

                    Knjiga knjiga = stavkaPolice.getKnjiga();
                    Recenzija recenzija = stavkaPolice.getRecenzija();

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
      Korisnik korisnikBaza = korisnikRepository.getById(korisnik.getId());
      korisnikBaza.getPolice().remove(polica);
      korisnikRepository.save(korisnikBaza);
      policaRepository.delete(polica);
  }



/*
    public void dodajKnjiguNaPolicuBezRecenzije(Korisnik korisnik, String nazivPrimarnePolice, String nazivKreiranePolice, KnjigaDto knjigaDto) throws Exception {
        Knjiga knjiga = new Knjiga(knjigaDto);
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

        korisnikRepository.save(korisnik);
        policaRepository.save(primarnaPolica);
        stavkaPoliceRepository.save(novaStavka);

        if (nazivKreiranePolice != null && !nazivKreiranePolice.isEmpty()) {
            boolean dodatoNaKreiranuPolicu = false;
            for (Polica polica : korisnik.getPolice()) {
                if (polica.getNaziv().equals(nazivKreiranePolice) && !polica.isPrimarna()) {
                    StavkaPolice novaStavkaKreirane = new StavkaPolice(knjiga);
                    polica.getStavkaPolice().add(novaStavkaKreirane);
                    dodatoNaKreiranuPolicu = true;

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
*/

    public void dodajKnjiguNaPolicuBezRecenzije(Korisnik korisnik, String nazivPrimarnePolice, String nazivKreiranePolice, String nazivKnjige) throws Exception {
        Knjiga knjiga = knjigaRepository.findByNaslovKnjige(nazivKnjige);
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

        korisnikRepository.save(korisnik);
        policaRepository.save(primarnaPolica);
       // stavkaPoliceRepository.save(novaStavka);

        if (nazivKreiranePolice != null && !nazivKreiranePolice.isEmpty()) {
            boolean dodatoNaKreiranuPolicu = false;
            for (Polica polica : korisnik.getPolice()) {
                if (polica.getNaziv().equals(nazivKreiranePolice) && !polica.isPrimarna()) {
                    StavkaPolice novaStavkaKreirane = new StavkaPolice(knjiga);
                    polica.getStavkaPolice().add(novaStavkaKreirane);
                    dodatoNaKreiranuPolicu = true;

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

    /*public void dodajKnjiguNaPolicuBezRecenzije(Korisnik korisnik, String nazivPrimarnePolice, String nazivKreiranePolice, Knjiga knjiga) throws Exception {
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

        korisnikRepository.save(korisnik);
        policaRepository.save(primarnaPolica);
        stavkaPoliceRepository.save(novaStavka);

        if (nazivKreiranePolice != null && !nazivKreiranePolice.isEmpty()) {
            boolean dodatoNaKreiranuPolicu = false;
            for (Polica polica : korisnik.getPolice()) {
                if (polica.getNaziv().equals(nazivKreiranePolice) && !polica.isPrimarna()) {
                    StavkaPolice novaStavkaKreirane = new StavkaPolice(knjiga);
                    polica.getStavkaPolice().add(novaStavkaKreirane);
                    dodatoNaKreiranuPolicu = true;

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
    }*/

  public void obrisiKnjiguSaPolice(Long knjigaId, Long policaId, Korisnik korisnik) {
      Knjiga knjiga = knjigaRepository.findById(knjigaId).orElseThrow(() -> new EntityNotFoundException("Knjiga sa datim ID-om nije pronadjena."));
      Polica polica = policaRepository.findById(policaId).orElseThrow(() -> new EntityNotFoundException("Polica sa datim ID-om nije pronadjena."));

          Zanr zanr = knjiga.getZanr();
          List<StavkaPolice> stavkePolice = polica.getStavkaPolice();

          StavkaPolice stavkaPolice = stavkePolice.stream()
                  .filter(stavka -> stavka.getKnjiga().equals(knjiga))
                  .findFirst()
                  .orElse(null);

          if (stavkaPolice != null) {
              if (polica.isPrimarna()) {
                  System.out.println("brisanje sa primarne police");
                  List<Polica> korisnikovePolice = korisnik.getPolice();
                  for (Polica korisnikovaPolica : korisnikovePolice) {
                      if (!korisnikovaPolica.isPrimarna()) {
                          List<StavkaPolice> korisnikoveStavke = korisnikovaPolica.getStavkaPolice();
                          List<StavkaPolice> stavkeZaBrisanje = new ArrayList<>();
                          for (StavkaPolice korisnikovaStavka : korisnikoveStavke) {
                              if (korisnikovaStavka.getKnjiga().getNaslov().equals(knjiga.getNaslov())) {
                                  System.out.println("nadjena");
                                  //korisnikoveStavke.remove(korisnikovaStavka);
                                  //stavkaPoliceRepository.delete(korisnikovaStavka);
                                  stavkeZaBrisanje.add(korisnikovaStavka);
                                  break;
                              }
                          }
                          for (StavkaPolice stavkaZaBrisanje : stavkeZaBrisanje) {
                              System.out.println("obrisana");
                              korisnikovaPolica.removeStavka(stavkaZaBrisanje);
                              stavkaPoliceRepository.delete(stavkaZaBrisanje);
                              korisnikRepository.save(korisnik);
                          }
                      }
                  }
              }


              stavkePolice.remove(stavkaPolice);
              stavkaPoliceRepository.delete(stavkaPolice);
          }
          korisnikRepository.save(korisnik);
  }


}
