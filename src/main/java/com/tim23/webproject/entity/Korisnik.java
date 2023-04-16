package com.tim23.webproject.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
public class Korisnik  implements Serializable {

    private String ime;
    private String prezime;
    @Column(name = "korisnicko_ime", unique = true)
    private String korisnickoIme;
    @Id
    @Column(name = "korisnik_id", unique = true)
    private String mejlAdresa;
    private String lozinka;
    @Column(name = "datum_rodjenja")
    private String datumRodjenja;
    @Column(name = "profilna_slika")
    private String profilnaSlika;
    private String opis;
    private Uloga uloga;



}
