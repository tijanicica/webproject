package com.tim23.webproject.dto;

import com.tim23.webproject.entity.Knjiga;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KnjigaDto {

    private String naslov;
    private String naslovnaFotografija;
    private int brojStrana;
    private String opis;
    private int ocena;

    public KnjigaDto(String naslov, String naslovnaFotografija, int brojStrana, String opis, int ocena) {
        this.naslov = naslov;
        this.naslovnaFotografija = naslovnaFotografija;
        this.brojStrana = brojStrana;
        this.opis = opis;
        this.ocena = ocena;
    }

    public KnjigaDto() {}

    public KnjigaDto(Knjiga knjiga){
        this.naslov = knjiga.getNaslov();
        this.naslovnaFotografija = knjiga.getNaslovnaFotografija();
        this.brojStrana = knjiga.getBrojStrana();
        this.opis = knjiga.getOpis();
        this.ocena = knjiga.getOcena();

    }
}
