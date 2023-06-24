package com.tim23.webproject.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tim23.webproject.entity.Korisnik;
import com.tim23.webproject.entity.Recenzija;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RecenzijaDto {

    private int ocena;
    private String tekst;
    private Date datumRecenzije;

    private KorisnikDto korisnik;

    public RecenzijaDto(int ocena, String tekst, Date datumRecenzije, KorisnikDto korisnik) {
        this.ocena = ocena;
        this.tekst = tekst;
        this.datumRecenzije = datumRecenzije;
        this.korisnik = korisnik;
    }

    public RecenzijaDto() {}

    public RecenzijaDto(Recenzija recenzija){
        this.ocena = recenzija.getOcena();
        this.tekst = recenzija.getTekst();
        this.datumRecenzije = recenzija.getDatumRecenzije();

       Korisnik korisnikKnjige = recenzija.getKorisnik();
        if (korisnikKnjige != null) {
            this.korisnik = new KorisnikDto(korisnikKnjige);
        }
    }
}
