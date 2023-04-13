package com.tim23.webproject.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
@Entity
public class Recenzija implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recenzijaId;
    private int ocena;
    private String tekst;
    private Date datumRecenzije;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Korisnik korisnik;

    public Long getRecenzijaId() {
        return recenzijaId;
    }

    public void setRecenzijaId(Long recenzijaId) {
        this.recenzijaId = recenzijaId;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public Date getDatumRecenzije() {
        return datumRecenzije;
    }

    public void setDatumRecenzije(Date datumRecenzije) {
        this.datumRecenzije = datumRecenzije;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
}
