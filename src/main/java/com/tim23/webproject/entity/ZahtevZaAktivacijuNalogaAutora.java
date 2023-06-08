package com.tim23.webproject.entity;

import com.tim23.webproject.dto.ZahtevZaAktivacijuNalogaAutoraDto;
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
public class ZahtevZaAktivacijuNalogaAutora implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zahtev_za_aktivaciju_naloga_autora_id")
    private Long id;

    private String email;
    private String telefon;
    private String poruka;
    private Date datum;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public ZahtevZaAktivacijuNalogaAutora(ZahtevZaAktivacijuNalogaAutoraDto zahtevZaAktivacijuNalogaAutoraDto) {
        this.email = zahtevZaAktivacijuNalogaAutoraDto.getEmail();
        this.telefon = zahtevZaAktivacijuNalogaAutoraDto.getTelefon();
        this.poruka = zahtevZaAktivacijuNalogaAutoraDto.getPoruka();

    }

    public ZahtevZaAktivacijuNalogaAutora() {}


}
