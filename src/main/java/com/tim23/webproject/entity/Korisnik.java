package com.tim23.webproject.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "korisnikove_police",
    joinColumns = @JoinColumn(name = "korisnik_id"),
    inverseJoinColumns = @JoinColumn(name = "polica_id"))
    private Set<Polica> police = new HashSet<>();


}
