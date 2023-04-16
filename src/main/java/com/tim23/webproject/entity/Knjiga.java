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
public class Knjiga implements Serializable {

    private String naslov;
    @Column(name = "naslovna_fotografija")
    private String naslovnaFotografija;
    @Id
    @Column(name = "knjiga_ISBN")
    private String ISBN;
    @Column(name = "datum_objavljivanja")
    private String datumObjavljivanja;
    @Column(name = "broj_strana")
    private int brojStrana;
    private String opis;
    //da li jedna knjiga moze biti i drama i romantika? ili OneToOne ili OneToMany
    private int ocena;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Zanr zanr;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "autor_id")
    private Autor autor;


}
