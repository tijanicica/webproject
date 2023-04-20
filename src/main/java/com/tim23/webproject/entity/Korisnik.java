package com.tim23.webproject.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

enum Uloga {CITALAC, AUTOR, ADMINISTRATOR;}

@Entity
@Getter
@Setter
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
public class Korisnik  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "korisnik_id")
    private Long id;
    private String ime;
    private String prezime;
    @Column(name = "korisnicko_ime", unique = true)
    private String korisnickoIme;
    @Column(name = "mejl_adresa", unique = true)
    private String mejlAdresa;
    private String lozinka;
    @Column(name = "datum_rodjenja")
    private LocalDate datumRodjenja;
    @Column(name = "profilna_slika")
    private String profilnaSlika;
    private String opis;
    @Enumerated(EnumType.STRING)
    private Uloga uloga;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "police")
    private List<Polica> police = new ArrayList<>();


}
