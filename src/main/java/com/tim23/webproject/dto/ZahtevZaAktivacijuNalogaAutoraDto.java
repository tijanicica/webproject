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
    private Date datum;
    public enum StatusDto {NA_CEKANJU, ODOBREN, ODBIJEN;}
    @Enumerated(EnumType.STRING)
    private StatusDto status;
    private AutorDto autor;

    public ZahtevZaAktivacijuNalogaAutoraDto(String email, String telefon, String poruka, Date datum, StatusDto status, AutorDto autor) {
        this.email = email;
        this.telefon = telefon;
        this.poruka = poruka;
        this.datum = datum;
        this.status = status;
        this.autor = autor;
    }

    public ZahtevZaAktivacijuNalogaAutoraDto() {}

    public ZahtevZaAktivacijuNalogaAutoraDto(ZahtevZaAktivacijuNalogaAutora zahtevZaAktivacijuNalogaAutora){
        this.email = zahtevZaAktivacijuNalogaAutora.getEmail();
        this.telefon = zahtevZaAktivacijuNalogaAutora.getTelefon();
        this.poruka = zahtevZaAktivacijuNalogaAutora.getPoruka();
        this.datum = zahtevZaAktivacijuNalogaAutora.getDatum();
        if (zahtevZaAktivacijuNalogaAutora.getStatus() == Status.NA_CEKANJU) {
            this.status = StatusDto.NA_CEKANJU;
        } else if (zahtevZaAktivacijuNalogaAutora.getStatus() == Status.ODOBREN) {
            this.status = StatusDto.ODOBREN;
        } else if (zahtevZaAktivacijuNalogaAutora.getStatus() == Status.ODBIJEN) {
            this.status = StatusDto.ODBIJEN;
        }

        /* Knjiga knjigaStavke = stavkaPolice.getKnjiga();
        if (knjigaStavke != null) {
            this.knjiga = new KnjigaDto(knjigaStavke);
        }*/

        Autor autorZahtevaZaAktivaciju = zahtevZaAktivacijuNalogaAutora.getAutor();
        if (autorZahtevaZaAktivaciju != null) {
            this.autor = new AutorDto(autorZahtevaZaAktivaciju);
        }
    }
}
