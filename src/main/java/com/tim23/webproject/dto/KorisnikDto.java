package com.tim23.webproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Polica;
import com.tim23.webproject.entity.Uloga;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



@Getter
@Setter
public class KorisnikDto {

    private String ime;
    private String prezime;
    private String korisnickoIme;

    private LocalDate datumRodjenja;
    private String profilnaSlika;
    private String opis;

   // public enum UlogaDto {CITALAC, AUTOR, ADMINISTRATOR;}
   @Enumerated(EnumType.STRING)
    private Uloga uloga;

    private List<PolicaDto> police = new ArrayList<>();

    public KorisnikDto(String ime, String prezime, String korisnickoIme, LocalDate datumRodjenja, String profilnaSlika, String opis, Uloga uloga, List<PolicaDto> police) {
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.datumRodjenja = datumRodjenja;
        this.profilnaSlika = profilnaSlika;
        this.opis = opis;
        this.uloga = uloga;
        this.police = police;
    }

    public KorisnikDto() {}

    public KorisnikDto(Korisnik korisnik) {
        this.ime = korisnik.getIme();
        this.prezime = korisnik.getPrezime();
        this.korisnickoIme = korisnik.getKorisnickoIme();
        this.datumRodjenja = korisnik.getDatumRodjenja();
        this.profilnaSlika = korisnik.getProfilnaSlika();
        this.opis = korisnik.getOpis();
        this.uloga = korisnik.getUloga();
        /*if (korisnik.getUloga() == Uloga.AUTOR) {
            this.uloga = UlogaDto.AUTOR;
        } else if (korisnik.getUloga() == Uloga.CITALAC) {
            this.uloga = UlogaDto.CITALAC;
        } else if (korisnik.getUloga() == Uloga.ADMINISTRATOR) {
            this.uloga = UlogaDto.ADMINISTRATOR;
        }*/

        this.police = new ArrayList<>();
        for (Polica polica : korisnik.getPolice()) {
            this.police.add(new PolicaDto(polica));
        }

    }
}
