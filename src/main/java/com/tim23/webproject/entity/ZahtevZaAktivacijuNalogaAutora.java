package com.tim23.webproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;


enum Status {NA_CEKANJU, ODOBREN, ODBIJEN};
@Entity
@Getter
@Setter
@ToString
public class ZahtevZaAktivacijuNalogaAutora implements Serializable {
    @Id
    private String email;
    private String telefon;
    private String poruka;
    private String datum;
    private Status status;

}
