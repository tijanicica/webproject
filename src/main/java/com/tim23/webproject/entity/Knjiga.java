package com.tim23.webproject.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
@Entity
public class Knjiga implements Serializable {

    private String naslov;
    private String naslovnaFotografija;
    @Id
    @Column(name = "knjiga_ISBN")
    private String ISBN;
    private Date datumObjavljivanja;
    private int brojStrana;
    private String opis;
    //da li jedna knjiga moze biti i drama i romantika? ili OneToOne ili OneToMany
    private int ocena;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Zanr zanr;

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getNaslovnaFotografija() {
        return naslovnaFotografija;
    }

    public void setNaslovnaFotografija(String naslovnaFotografija) {
        this.naslovnaFotografija = naslovnaFotografija;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Date getDatumObjavljivanja() {
        return datumObjavljivanja;
    }

    public void setDatumObjavljivanja(Date datumObjavljivanja) {
        this.datumObjavljivanja = datumObjavljivanja;
    }

    public int getBrojStrana() {
        return brojStrana;
    }

    public void setBrojStrana(int brojStrana) {
        this.brojStrana = brojStrana;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Zanr getZanr() {
        return zanr;
    }

    public void setZanr(Zanr zanr) {
        this.zanr = zanr;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    @Override
    public String toString() {
        return "Knjiga{" +
                "naslov='" + naslov + '\'' +
                ", naslovnaFotografija='" + naslovnaFotografija + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", datumObjavljivanja=" + datumObjavljivanja +
                ", brojStrana=" + brojStrana +
                ", opis='" + opis + '\'' +
                ", ocena=" + ocena +
                ", zanr=" + zanr +
                '}';
    }
}
