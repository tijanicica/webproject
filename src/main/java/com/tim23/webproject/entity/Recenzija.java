package com.tim23.webproject.entity;
import com.tim23.webproject.dto.RecenzijaBezKorisnikaDto;
import com.tim23.webproject.dto.RecenzijaDto;
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
public class Recenzija implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recenzija_id")
    private Long id;
    private int ocena;
    private String tekst;
    @Column(name = "datum_recenzije")
    private Date datumRecenzije;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "korisnik_id")
    private Korisnik korisnik;

    public Recenzija(Long id, int ocena, String tekst, Date datumRecenzije, Korisnik korisnik) {
        this.id = id;
        this.ocena = ocena;
        this.tekst = tekst;
        this.datumRecenzije = datumRecenzije;
        this.korisnik = korisnik;
    }

    public Recenzija() {}

    public Recenzija(Recenzija recenzija) {
        this.ocena = recenzija.getOcena();
        this.tekst = recenzija.getTekst();
        this.datumRecenzije = recenzija.getDatumRecenzije();
        this.korisnik = recenzija.getKorisnik();
    }

    public Recenzija(RecenzijaBezKorisnikaDto recenzijaBezKorisnikaDto) {
        this.ocena = recenzijaBezKorisnikaDto.getOcena();
        this.tekst = recenzijaBezKorisnikaDto.getTekst();
        this.datumRecenzije = recenzijaBezKorisnikaDto.getDatumRecenzije();
    }
}
