package com.tim23.webproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.Date;

enum Status {NA_CEKANJU, ODOBREN, ODBIJEN;}
@Entity
@Getter
@Setter
@ToString
public class ZahtevZaAktivacijuNalogaAutora implements Serializable {
    @Id
    private String email;
    private String telefon;
    private String poruka;
    private Date datum;
    @Enumerated(EnumType.STRING)
    private Status status;

}
