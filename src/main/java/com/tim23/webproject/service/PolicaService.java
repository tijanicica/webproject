package com.tim23.webproject.service;

import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Polica;
import com.tim23.webproject.entity.Uloga;
import com.tim23.webproject.repository.KorisnikRepository;
import com.tim23.webproject.repository.PolicaRepository;
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
    private boolean imaPolicuSaImenom(Korisnik korisnik, String imePolice) {
        for (Polica polica : korisnik.getPolice()) {
            if (polica.getNaziv().equals(imePolice)) {
                return true;
            }
        }
        return false;
    }

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
}
