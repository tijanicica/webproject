package com.tim23.webproject.entity;
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
    private String datumRecenzije;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "korisnik_id")
    private Korisnik korisnik;



    
}
