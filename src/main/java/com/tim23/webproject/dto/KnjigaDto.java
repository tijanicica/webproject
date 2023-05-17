package com.tim23.webproject.dto;
import com.tim23.webproject.entity.Knjiga;
import com.tim23.webproject.entity.Zanr;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class KnjigaDto {

    private String naslov;
    private String naslovnaFotografija;
    private Date datumObjavljivanja;
    private int brojStrana;
    private String opis;
    private int ocena;
    private ZanrDto zanr;

    public KnjigaDto(String naslov, String naslovnaFotografija, Date datumObjavljivanja, int brojStrana, String opis, int ocena, ZanrDto zanr) {
        this.naslov = naslov;
        this.naslovnaFotografija = naslovnaFotografija;
        this.datumObjavljivanja = datumObjavljivanja;
        this.brojStrana = brojStrana;
        this.opis = opis;
        this.ocena = ocena;
        this.zanr = zanr;
    }

    public KnjigaDto() {}

    public KnjigaDto(Knjiga knjiga){
        this.naslov = knjiga.getNaslov();
        this.naslovnaFotografija = knjiga.getNaslovnaFotografija();
        this.datumObjavljivanja = knjiga.getDatumObjavljivanja();
        this.brojStrana = knjiga.getBrojStrana();
        this.opis = knjiga.getOpis();
        this.ocena = knjiga.getOcena();
        Zanr zanrKnjige = knjiga.getZanr();
        if (zanrKnjige != null) {
            this.zanr = new ZanrDto(zanrKnjige);
        }

    }
}
