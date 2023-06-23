package com.tim23.webproject.entity;

import com.tim23.webproject.dto.KnjigaDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
public class Knjiga implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "knjiga_id")
    private Long id;

    private String naslov;
    @Column(name = "naslovna_fotografija")
    private String naslovnaFotografija;
    @Column(name = "knjiga_isbn")
    private String ISBN;
    @Column(name = "datum_objavljivanja")
    private Date datumObjavljivanja;
    @Column(name = "broj_strana")
    private int brojStrana;
    private String opis;
    private int ocena;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Zanr zanr;

    public Knjiga(String naslov, String naslovnaFotografija, Date datumObjavljivanja, int brojStrana, String opis, int ocena, Zanr zanr) {
        this.naslov = naslov;
        this.naslovnaFotografija = naslovnaFotografija;
        this.datumObjavljivanja = datumObjavljivanja;
        this.brojStrana = brojStrana;
        this.opis = opis;
        this.ocena = ocena;
        this.zanr = zanr;
    }


    public Knjiga(){}

    public Knjiga(KnjigaDto knjigaDto) {
        this.naslov = knjigaDto.getNaslov();
        this.naslovnaFotografija = knjigaDto.getNaslovnaFotografija();
        this.datumObjavljivanja = knjigaDto.getDatumObjavljivanja();
        this.brojStrana = knjigaDto.getBrojStrana();
        this.opis = knjigaDto.getOpis();
        this.ocena = knjigaDto.getOcena();
    }
}
