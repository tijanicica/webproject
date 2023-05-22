package com.tim23.webproject.dto;
import com.tim23.webproject.entity.Autor;
import com.tim23.webproject.entity.Status;
import com.tim23.webproject.entity.ZahtevZaAktivacijuNalogaAutora;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class ZahtevZaAktivacijuNalogaAutoraDto {

    private String email;
    private String telefon;
    private String poruka;

    public ZahtevZaAktivacijuNalogaAutoraDto(String email, String telefon, String poruka) {
        this.email = email;
        this.telefon = telefon;
        this.poruka = poruka;
    }

    public ZahtevZaAktivacijuNalogaAutoraDto() {}

    public ZahtevZaAktivacijuNalogaAutoraDto(ZahtevZaAktivacijuNalogaAutora zahtevZaAktivacijuNalogaAutora){
        this.email = zahtevZaAktivacijuNalogaAutora.getEmail();
        this.telefon = zahtevZaAktivacijuNalogaAutora.getTelefon();
        this.poruka = zahtevZaAktivacijuNalogaAutora.getPoruka();

    }
}
