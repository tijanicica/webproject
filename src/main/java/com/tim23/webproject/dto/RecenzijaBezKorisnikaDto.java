package com.tim23.webproject.dto;

import com.tim23.webproject.entity.Recenzija;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RecenzijaBezKorisnikaDto {

    private int ocena;
    private String tekst;
    private Date datumRecenzije;

    public RecenzijaBezKorisnikaDto(int ocena, String tekst, Date datumRecenzije) {
        this.ocena = ocena;
        this.tekst = tekst;
        this.datumRecenzije = datumRecenzije;
    }

    public RecenzijaBezKorisnikaDto(Recenzija recenzija) {
        this.ocena = recenzija.getOcena();
        this.tekst = recenzija.getTekst();
        this.datumRecenzije = recenzija.getDatumRecenzije();
    }


}
